package org.usfirst.frc.team102.robot.subsystems;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.usfirst.frc.team102.robot.RobotMap;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class PDP extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	BufferedWriter pdpWriter = null;
	PowerDistributionPanel pdp;

	public PDP() {
		if (RobotMap.logPDPStats) {
			try {
				pdp = new PowerDistributionPanel();
				// create a temporary file
				String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
				File logFile = new File("/home/lvuser/PDP-" + timeLog + ".log");

				System.out.println(logFile.getCanonicalPath());

				pdpWriter = new BufferedWriter(new FileWriter(logFile));

				String chans = "";
				for (int i = 0; i < 16; i++) {
					chans += String.format("%d\t", i);
				}

				pdpWriter.write("Time\tTotCurr\tTotEng\tTotPow\tTemptr\tVoltg" + chans);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void initDefaultCommand() {
	}

	public void LogStats() {
		if (RobotMap.logPDPStats) {
			try {
				String channelCurr = "";
				for (int i = 0; i < 16; i++) {
					channelCurr += String.format("\t%.3f", pdp.getCurrent(i));
				}
				pdpWriter.write(String.format("%f\t%.3f\t%.3f\t%.3f\t%.3f\t%.3f%s\n", Timer.getFPGATimestamp(),
						pdp.getTotalCurrent(), pdp.getTotalEnergy(), pdp.getTotalPower(), pdp.getTemperature(), pdp.getVoltage(),
						channelCurr));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void CloseStats() {
		if (pdpWriter == null)
			return;
		if (RobotMap.logPDPStats) {
			try {
				// Close the log file.
				pdpWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
