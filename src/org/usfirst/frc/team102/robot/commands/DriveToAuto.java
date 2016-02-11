package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToAuto extends Command {

    public DriveToAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.setTimeout(3.0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
    	Robot.wallyDriveTrain.driveStrafeInAuto(false, 1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//drive in curvy path back to autozone
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		if (isTimedOut()) {
			return true;
		} else {
			return false;
		}
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
