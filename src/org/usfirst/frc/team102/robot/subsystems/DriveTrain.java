package org.usfirst.frc.team102.robot.subsystems;

import org.usfirst.frc.team102.robot.RobotMap;
import org.usfirst.frc.team102.robot.commands.DriveWithXBox;

import Team102Lib.MessageLogger;
import Team102Lib.MovingStat;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GyroBase;
//import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 *
 */

public class DriveTrain extends PIDSubsystem {
	//
	public enum AutoDriveDirection {
		forward, reverse, strafeLeft, strafeRight
	};

	CANTalon canFrontRight;
	CANTalon canFrontLeft;
	CANTalon canFrontStrafe;
	CANTalon canRearRight;
	CANTalon canRearLeft;
	CANTalon canRearStrafe;
	Talon frontRight;
	Talon frontLeft;
	public Talon frontStrafe;
	Talon rearRight;
	Talon rearLeft;
	Talon rearStrafe;
	AnalogInput frontDistanceSensor;
	AnalogInput rearDistanceSensor;
	private double leftJoyX;
	private double leftJoyY;
	private double rightJoyX;
	private double rightJoyY;
	private double speedScale;
	private boolean isToggle;
	public MovingStat frontMovingStats;
	public MovingStat rearMovingStats;
//	public Gyro theGyro;
	public String SimDistanceFile = "container data.txt";
	BufferedWriter distanceLogWriter = null;
	BufferedWriter gyroPIDLogWriter = null;

	Scanner simDistanceFile = null;

	public DriveTrain(double p, double i, double d) {
		super(p, i, d);
		try {
			speedScale = 1.0;

			MessageLogger.LogMessage("creating gyro and distance sensors");
//			theGyro = new GyroBase(RobotMap.gyroChannel);
//			theGyro.initGyro();

			frontDistanceSensor = new AnalogInput(RobotMap.FrontDistanceSensor);
			rearDistanceSensor = new AnalogInput(RobotMap.RearDistanceSensor);

			MessageLogger.LogMessage("Gyro and distance sensors created");
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	// This part of initialization gets called after RobotInit, so we can make
	// use of DriverStation Digital Inputs.
	public void CreateDriveAfterInit() {
		try {
			MessageLogger.LogMessage("creating DriveTrain Motors");
			if (RobotMap.isTestBed) {
				frontLeft = new Talon(RobotMap.frontLeft);
				frontStrafe = new Talon(RobotMap.frontStrafe);
				frontRight = new Talon(RobotMap.frontRight);
				// rearLeft = new Talon(RobotMap.rearLeft);
				rearStrafe = new Talon(RobotMap.rearStrafe);
				// rearRight = new Talon(RobotMap.rearRight);
			} else {
				canFrontLeft = new CANTalon(RobotMap.canFrontLeft);
				canFrontStrafe = new CANTalon(RobotMap.canFrontStrafe);
				canFrontRight = new CANTalon(RobotMap.canFrontRight);
				canRearLeft = new CANTalon(RobotMap.canRearLeft);
				canRearStrafe = new CANTalon(RobotMap.canRearStrafe);
				canRearRight = new CANTalon(RobotMap.canRearRight);
			}
			MessageLogger.LogMessage("done with DriveTrain Motors");
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithXBox());
		System.out.println("creating Default Command");
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void driveWithXBox(Joystick xBox, boolean isToggle) {
		try {
			leftJoyX = xBox.getRawAxis(RobotMap.xBoxLeftXAxis);
			leftJoyY = -xBox.getRawAxis(RobotMap.xBoxLeftYAxis);
			rightJoyX = xBox.getRawAxis(RobotMap.xBoxRightXAxis);
			rightJoyY = -xBox.getRawAxis(RobotMap.xBoxRightYAxis);
			this.isToggle = isToggle;

			// Scale the speed.
			leftJoyX *= speedScale;
			leftJoyY *= speedScale;
			rightJoyX *= speedScale;
			rightJoyY *= speedScale;
			// Simple deadband
			if (Math.abs(leftJoyX) < 0.1) {
				leftJoyX = 0.0;
			}
			if (Math.abs(leftJoyY) < 0.1) {
				leftJoyY = 0.0;
			}
			if (Math.abs(rightJoyX) < 0.1) {
				rightJoyX = 0.0;
			}
			if (Math.abs(rightJoyY) < 0.1) {
				rightJoyY = 0.0;
			}
			// for(int i = 0; i <= 5; i++ ){
			// System.out.printf("%d: %.3f\t", i, xBox.getRawAxis(i));
			// }
			// System.out.printf("\n");
			// System.out.printf("LX: %.3f\tLY: %.3f\tRX: %.3f\tRY: %.3f\t\n",
			// leftJoyX, leftJoyY, rightJoyX, rightJoyY);
			if (RobotMap.isTestBed) {
				if (isToggle == false) {
					frontRight.set(-rightJoyY);
					// rearRight.set(-rightJoyY);
					frontStrafe.set(leftJoyX);
					// rearLeft.set(leftJoyY);
					frontLeft.set(leftJoyY);
					rearStrafe.set(-rightJoyX);
				} else {
					// frontRight.set(-rightJoyY);
					// rearRight.set(-rightJoyY);
					frontStrafe.set(leftJoyX);
					// rearLeft.set(leftJoyY);
					frontLeft.set(leftJoyY);
					rearStrafe.set(-leftJoyX);
				}
			} else {
				if (isToggle == false) {
					canFrontRight.set(-rightJoyY);
					canRearRight.set(-rightJoyY);
					canFrontStrafe.set(leftJoyX);
					canRearLeft.set(leftJoyY);
					canFrontLeft.set(leftJoyY);
					canRearStrafe.set(-rightJoyX);
				} else {
					canFrontRight.set(-rightJoyY);
					canRearRight.set(-rightJoyY);
					canFrontStrafe.set(leftJoyX);
					canRearLeft.set(leftJoyY);
					canFrontLeft.set(leftJoyY);
					canRearStrafe.set(-leftJoyX);
				}
				// MessageLogger.LogMessage(String.format("%d\t%d",
				// frontDistanceSensor.getValue(),
				// rearDistanceSensor.getValue()));
			}
		} catch (Exception ex1) {
			DriverStation.reportError(ex1.getMessage(), true);
		}
		// System.out.printf("FL: %.3f\tFS: %.3f\tFR: %.3f\tRL: %.3f\tRS: %.3f\tRR: %.3f\t\n",
		// frontLeft.get(), frontStrafe.get(), frontRight.get(), rearLeft.get(),
		// rearStrafe.get(), rearRight.get());
	}

	public void dashboardMessage() {
		SmartDashboard.putString("DB/String 4", "Strafe toggle is " + isToggle);
//		SmartDashboard.putString("DB/String 8", "Gyro: " + String.format("%.3f", theGyro.getAngle()));
		if (!RobotMap.isTestBed) {
			SmartDashboard.putString("DB/String 6", String.format("FDist: %d", frontDistanceSensor.getValue()));
			SmartDashboard.putString("DB/String 7", String.format("RDist: %d", rearDistanceSensor.getValue()));
		}
	}

	public void DriveAlongStep(AutoDriveDirection direction, double magnitude) {
		double strafeSpeed = 0.25;
		double speed = magnitude;
		System.out.println("Driving Along Step at: " + speed);
		if (direction == AutoDriveDirection.reverse)
			speed = -speed;
		if (RobotMap.isTestBed) {
			frontRight.set(-speed);
			frontLeft.set(speed);
			frontStrafe.set(-strafeSpeed);
			rearStrafe.set(strafeSpeed);
		} else {
			canFrontRight.set(-speed);
			canFrontLeft.set(speed);
			canRearRight.set(-speed);
			canRearLeft.set(speed);
			canFrontStrafe.set(strafeSpeed);
			canRearStrafe.set(-strafeSpeed);
		}
	}

	public void DriveStraightInAuto(double speed) {
		// might change from a value with the sensor.
		if (RobotMap.isTestBed == false) {
			canFrontRight.set(-speed);
			canFrontLeft.set(speed);
			canRearRight.set(-speed);
			canRearLeft.set(speed);
		} else {
			frontRight.set(-speed);
			frontLeft.set(speed);
		}
	}

	public void TurnOffMotors() {
		if (RobotMap.isTestBed == false) {
			canFrontRight.set(0);
			canFrontLeft.set(0);
			canRearRight.set(0);
			canRearLeft.set(0);
			canRearStrafe.set(0);
			canFrontStrafe.set(0);
		} else {
			frontLeft.set(0);
			frontRight.set(0);
			rearStrafe.set(0);
			frontStrafe.set(0);
		}

	}

	public void TestDrive(int direction) {
		System.out.println(String.format("TestDrive %d", direction));
		if (RobotMap.isTestBed) {
			if (direction == 0) {
				frontStrafe.set(-1.0);
				rearStrafe.set(1.0);
			} else if (direction == 1) {
				frontStrafe.set(1.0);
				rearStrafe.set(-1.0);
			} else if (direction == 2) {
				frontRight.set(-0.5);
				frontLeft.set(0.5);
				// rearRight.set(-0.5);
				// rearLeft.set(0.5);
			} else if (direction == 3) {
				frontRight.set(0.5);
				frontLeft.set(-0.5);
				// rearRight.set(0.5);
				// rearLeft.set(-0.5);
			}
		} else {
			if (direction == 0) {
				canFrontStrafe.set(-1.0);
				canRearStrafe.set(1.0);
			} else if (direction == 1) {
				canFrontStrafe.set(1.0);
				canRearStrafe.set(-1.0);
			} else if (direction == 2) {
				canFrontRight.set(-0.5);
				canFrontLeft.set(0.5);
				canRearRight.set(-0.5);
				canRearLeft.set(0.5);
			} else if (direction == 3) {
				canFrontRight.set(0.5);
				canFrontLeft.set(-0.5);
				canRearRight.set(0.5);
				canRearLeft.set(-0.5);
			}
		}
	}

	// This group of methods uses the PIDSubSystem and the Gyro to strafe
	// straight to the wall in autonomous.
	public void driveStrafeInAuto(boolean direction, double strafeSpeed) {

		// Output output file for logging PID feedback.
		try {
			// create a temporary file
			String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			File logFile = new File("/home/lvuser/GyroPID-" + timeLog + ".log");

			gyroPIDLogWriter = new BufferedWriter(new FileWriter(logFile));
		} catch (Exception e) {
			e.printStackTrace();
		}

//		theGyro.reset();
		this.enable();

		if (RobotMap.isTestBed) {
			if (direction == true) {
				frontStrafe.set(strafeSpeed);
				rearStrafe.set(-strafeSpeed);
			} else {
				frontStrafe.set(-strafeSpeed);
				rearStrafe.set(strafeSpeed);
			}
		} else {
			if (direction == true) {
				canFrontStrafe.set(-strafeSpeed);
				canRearStrafe.set(strafeSpeed);
				// canRearStrafe.set(0.97);
			} else {
				canFrontStrafe.set(strafeSpeed);
				canRearStrafe.set(-strafeSpeed);
				// canRearStrafe.set(-0.97);
			}
		}
	}

	public void StopDriveStrafeInAuto() {
		this.disable();
		if (RobotMap.isTestBed) {
			frontStrafe.set(0.0);
			rearStrafe.set(0.0);
		} else {
			canFrontStrafe.set(0.0);
			canRearStrafe.set(0.0);
		}
		try {
			// Close the log file.
			gyroPIDLogWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected double returnPIDInput() {
//		return theGyro.getAngle();
		return -1d;
	}

	@Override
	protected void usePIDOutput(double output) {
		if (RobotMap.isTestBed) {
			frontRight.set(-output);
			frontLeft.set(output);
		} else {
			canFrontRight.set(output);
			canFrontLeft.set(output);
			canRearRight.set(output);
			canRearLeft.set(output);
		}
		try {
//			gyroPIDLogWriter.write(String.format("%f\t%.3f\t%.3f\n", Timer.getFPGATimestamp(), theGyro.getAngle(), output));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SetUpDistanceSensor() {
		if (RobotMap.isTestBed) {
			try {
				String simFile = "/home/lvuser/SimDist/" + SimDistanceFile;
				MessageLogger.LogMessage("Opening simulation file: " + simFile);
				simDistanceFile = new Scanner(new File(simFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Open a file for logging the distance sensor output.
		try {
			// create a temporary file
			String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			File logFile = new File("/home/lvuser/SimDist/Distance-" + timeLog + ".log");

			MessageLogger.LogMessage("Logging distance to: " + logFile.getCanonicalPath());

			distanceLogWriter = new BufferedWriter(new FileWriter(logFile));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Create the moving stats objects for both sensors.
		frontMovingStats = new MovingStat(10);
		rearMovingStats = new MovingStat(10);
	}

	public void CloseDistanceSensor() {
		if (RobotMap.isTestBed) {
			if (simDistanceFile != null)
				simDistanceFile.close();
		}

		try {
			// Close the log file.
			distanceLogWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// NOTE: Front sensor returned first, Rear second.
	public void getDistanceSensors(double[] readings) {
		readings[0] = -1;
		readings[1] = -1;
		if (RobotMap.isTestBed) {
			if (simDistanceFile != null) {
				if (simDistanceFile.hasNextInt())
					readings[0] = simDistanceFile.nextInt();
				if (simDistanceFile.hasNextInt())
					readings[1] = simDistanceFile.nextInt();
			}
		} else {
			readings[0] = frontDistanceSensor.getValue();
			readings[1] = rearDistanceSensor.getValue();
		}

		if ((readings[0] != -1) && (readings[1] != -1)) {
			// Put the readings into the moving stats.
			frontMovingStats.update(readings[0]);
			rearMovingStats.update(readings[1]);

			// Write readings to the log file.
			try {
				distanceLogWriter.write(String.format("%f\t%.0f\t%.0f\t%.3f\t%.3f\t%.3f\t%.3f\t%.3f\t%.3f\n", Timer.getFPGATimestamp(),
						readings[0], readings[1], frontMovingStats.average, frontMovingStats.variance, frontMovingStats.stdDev,
						rearMovingStats.average, rearMovingStats.variance, rearMovingStats.stdDev));
//				 MessageLogger.LogMessage(String.format("%f\t%.0f\t%.0f", Timer.getFPGATimestamp(), readings[0], readings[1]));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// Indicate end of readings in the log file.
		}
	}
}
