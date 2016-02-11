package Team102Lib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Administrator
 */
public class MessageLogger {
	static BufferedWriter pdpWriter = null;
	public static boolean disableLog = false;
	public static boolean disableErrLog = false;

	public static void LogError(String errMsg) {
		if (disableErrLog)
			return;
		LogErrDirect(errMsg);
	}

	public static void LogMessage(String msg) {
		try {
			if (disableLog)
				return;
			if (DriverStation.getInstance().isAutonomous()) {
				if (pdpWriter == null) {
					String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
					File logFile = new File("/home/lvuser/Autonomous-" + timeLog + ".log");
					System.out.println(logFile.getCanonicalPath());
					pdpWriter = new BufferedWriter(new FileWriter(logFile));
				}
				pdpWriter.write(msg + "\n");
			} else {
				if (pdpWriter != null)
					pdpWriter.close();
			}
			LogDirect(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void LogDirect(String msg) {
		System.out.print(Timer.getFPGATimestamp() + "\t");
		System.out.println(msg);
	}

	public static void LogErrDirect(String msg) {
		System.err.print(Timer.getFPGATimestamp() + "\t");
		System.err.println(msg);
	}
}