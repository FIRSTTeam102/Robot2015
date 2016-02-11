package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class DriveToStep extends Command {

	public DriveToStep() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.wallyDriveTrain);
		this.setTimeout(1.2);  // MJP 15 Feb 2015: Tested this timing.
//		this.setTimeout(20);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
//		Robot.wallyDriveTrain.theGyro.reset();
		Robot.wallyDriveTrain.driveStrafeInAuto(false, 1.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.wallyDriveTrain.StopDriveStrafeInAuto();
		String msg = this.getClass().getName();
		if (this.isTimedOut())
			msg += " timed out.";
		else if (this.isCanceled())
			msg += " cancelled.";
		else
			msg += " finished.";
		MessageLogger.LogMessage(msg);
/*		if(Robot.wallyDriveTrain.theGyro.getAngle() > RobotMap.GyroClockwiseLimit 
				|| Robot.wallyDriveTrain.theGyro.getAngle() < RobotMap.GyroCounterClockwiseLimit){
			Scheduler.getInstance().removeAll();
			Scheduler.getInstance().add(new StopAllMotors());
			MessageLogger.LogMessage("Cancelling autonomous because robot is rotated too much.");
		}*/
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
