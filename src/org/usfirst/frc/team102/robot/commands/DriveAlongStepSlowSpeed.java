package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.subsystems.DriveTrain.AutoDriveDirection;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveAlongStepSlowSpeed extends Command {
	public AutoDriveDirection direction;
    public DriveAlongStepSlowSpeed(AutoDriveDirection direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wallyDriveTrain);
    	this.setTimeout(0.5);
    	this.direction = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
    	Robot.wallyDriveTrain.DriveAlongStep(direction, .5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
     	Robot.wallyDriveTrain.DriveAlongStep(direction, 0.0);
		String msg = this.getClass().getName();
		if (this.isTimedOut())
			msg += " timed out.";
		else if (this.isCanceled())
			msg += " cancelled.";
		else
			msg += " finished.";

		MessageLogger.LogMessage(msg);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
