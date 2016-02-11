//package org.usfirst.frc.team102.robot.commands;
//
//import org.usfirst.frc.team102.robot.Robot;
//import org.usfirst.frc.team102.robot.subsystems.DriveTrain.AutoDriveDirection;
//
//import Team102Lib.MessageLogger;
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// *
// */
//public class DriveAlongStepToSpace extends Command {
//
//	public AutoDriveDirection direction;
//	boolean spaceInitiallyDetected = true;
//
//	public DriveAlongStepToSpace(AutoDriveDirection direction,
//			double timeoutLength) {
//		// Use requires() here to declare subsystem dependencies
//		// eg. requires(chassis);
//		requires(Robot.wallyDriveTrain);
//		this.direction = direction;
//		this.setTimeout(timeoutLength);
//	}
//
//	// Called just before this Command runs the first time
//	protected void initialize() {
//		MessageLogger.LogMessage(this.getClass().getName());
//		Robot.wallyDriveTrain.resetCounter();
//		
//		// Check if the space is initial detected.  If so we have to find undetected first.
//		if(Robot.wallyDriveTrain.isSpaceAroundContainerDetected())
//		{
//			MessageLogger.LogMessage("Space initially detected.");
//			spaceInitiallyDetected = true;
//		}
//		else
//		{
//			MessageLogger.LogMessage("Space initially undetected.");
//			spaceInitiallyDetected = false;
//		}
//	}
//
//	// Called repeatedly when this Command is scheduled to run
//	protected void execute() {
//		Robot.wallyDriveTrain.DriveAlongStep(direction, 1.0);
//
//		// initially the space will be undetected.
//		if (spaceInitiallyDetected && !Robot.wallyDriveTrain.isSpaceAroundContainerDetected()) {
//			MessageLogger.LogMessage("Space undetected, resetting counter.");
//			Robot.wallyDriveTrain.resetCounter();
//		}
//	}
//
//	// Make this return true when this Command no longer needs to run execute()
//	protected boolean isFinished() {
//		if (Robot.wallyDriveTrain.isSpaceAroundContainerDetected()) {
//			MessageLogger.LogMessage("Space detected.");
//		} else if (isTimedOut())
//			MessageLogger.LogMessage("Timed out looking for space.");
//
//		return (Robot.wallyDriveTrain.isSpaceAroundContainerDetected() || isTimedOut());
//	}
//
//	// Called once after isFinished returns true
//	protected void end() {
//		Robot.wallyDriveTrain.TurnOffMotors();
//		String msg = this.getClass().getName();
//		if (this.isTimedOut())
//			msg += " timed out.";
//		else if (this.isCanceled())
//			msg += " cancelled.";
//		else
//			msg += " finished.";
//
//		MessageLogger.LogMessage(msg);
//	}
//
//	// Called when another command which requires one or more of the same
//	// subsystems is scheduled to run
//	protected void interrupted() {
//		end();
//	}
//}
