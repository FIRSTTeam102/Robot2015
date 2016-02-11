package org.usfirst.frc.team102.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team102.robot.commands.Autonomous;
import org.usfirst.frc.team102.robot.commands.AutonomousSimple;
import org.usfirst.frc.team102.robot.commands.DriveAndFindContainer;
import org.usfirst.frc.team102.robot.commands.DriveAndSlam;
import org.usfirst.frc.team102.robot.commands.DriveToStep;
import org.usfirst.frc.team102.robot.commands.ResetArm;
import org.usfirst.frc.team102.robot.commands.ResetArmToHomePosition;
import org.usfirst.frc.team102.robot.commands.ResetArms;
import org.usfirst.frc.team102.robot.subsystems.ArmLift;
import org.usfirst.frc.team102.robot.subsystems.DriveTrain;
import org.usfirst.frc.team102.robot.subsystems.PDP;
import org.usfirst.frc.team102.robot.subsystems.Turntable;

import Team102Lib.MessageLogger;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static Turntable wallyTurntable;
	public static DriveTrain wallyDriveTrain;
	public static ArmLift wallyArmSystem;
	public static PDP wallyPDP;
	public static Joystick driverJoystick;
	public boolean areRobotObjectsCreated = false;
	Command autonomousCommand;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		try {
			DriverStation.reportError("WaaaAAA-AAAAaaaHHLlllyyyyy \n", false);
			DriverStation.reportError("EEEEvvEEEE", false);

			// Creating drivetrain here so that gyro can initialize before
			// autonomous starts.
			wallyPDP = new PDP();
			wallyDriveTrain = new DriveTrain(0.04, 0.0, 0.0); // .005?
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	public void createRobotObjects() {
		try {
			if (!areRobotObjectsCreated) {
				RobotMap.isTestBed = SmartDashboard.getBoolean("DB/Button 0", false);
				System.out.println("isTestBed is: " + RobotMap.isTestBed);
				System.out.println("isSimpleAutonomous is: " + RobotMap.isSimpleAutonomous);
				wallyTurntable = new Turntable();
				wallyDriveTrain.CreateDriveAfterInit();
				wallyArmSystem = new ArmLift();
				driverJoystick = new Joystick(0);
				oi = new OI();
				areRobotObjectsCreated = true;
				Robot.wallyTurntable.dashboardMessage();
				SmartDashboard.putString("DB/String 3", "             ");
			}
			else
			{
				System.out.println("areRobotObjectsCreated is: " + areRobotObjectsCreated);
			}
			RobotMap.isSimpleAutonomous = SmartDashboard.getBoolean("DB/Button 1", false);
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {
		try {
			MessageLogger.LogMessage("disabledInit");
			if (Robot.driverJoystick != null) {
				Robot.driverJoystick.setRumble(RumbleType.kLeftRumble, 0);
				Robot.driverJoystick.setRumble(RumbleType.kRightRumble, 0);
			}
			if (!this.isAutonomous())
				Robot.wallyPDP.CloseStats();
			MessageLogger.LogMessage(String.format("isAutonomous: %b", this.isAutonomous()));
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	public void disabledPeriodic() {
		try {
			Scheduler.getInstance().run();
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		try {
			MessageLogger.LogMessage("autonomousInit");
			createRobotObjects();
			// autonomousCommand = null;
			//autonomousCommand = new DriveToStep();
			autonomousCommand = new Autonomous();
			if (autonomousCommand != null)
				autonomousCommand.start();
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		try {
			UpdateDriversStation();
			Scheduler.getInstance().run();
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		try {
			MessageLogger.LogMessage("teleopInit");
			createRobotObjects();
			if (autonomousCommand != null)
				autonomousCommand.cancel();
			Robot.wallyTurntable.dashboardMessage();
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	void UpdateDriversStation() {
		Robot.wallyArmSystem.UpdateDriverStation();
		Robot.wallyTurntable.dashboardMessage();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		try {
			Scheduler.getInstance().run();
			UpdateDriversStation();
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	public void testInit() {
		try {
			MessageLogger.LogMessage("testInit");
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		try {
			LiveWindow.run();
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}
}
