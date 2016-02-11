package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;
import org.usfirst.frc.team102.robot.subsystems.DriveTrain.AutoDriveDirection;

import Team102Lib.MessageLogger;
import Team102Lib.MovingStat;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class SlamIntoTotes extends Command {

	public AutoDriveDirection direction;

	public SlamIntoTotes(AutoDriveDirection direction, double timeoutLength) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.wallyDriveTrain);
		this.direction = direction;
		this.setTimeout(timeoutLength);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// MessageLogger.LogMessage(this.getClass().getName());
		MessageLogger.LogMessage("Currently Slamming into totes");
		Robot.wallyDriveTrain.DriveAlongStep(direction, 0.5);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// Get the measurements from the distance sensors.
		// This
		// also
		// updates
		// the
		// stats.

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		boolean finished = false;
		// The following line should be commented out in the real robot.
		// MessageLogger.LogMessage(String.format("%d: leading: %5.2f, %5.3f : trailing: %5.2f, %5.3f"
		// , point
		// , leadingStats.average
		// , leadingStats.stdDev
		// , trailingStats.average
		// , trailingStats.stdDev
		// ));
		//
		// // This should also be commented out on the real robot.
		// if(leadingContainerWithinLimit || leadingContainerWithinStdDev ||
		// trailingContainerWithinLimit || trailingContainerWithinStdDev)
		// {
		// MessageLogger.LogMessage(String.format("leading: %b, %b : trailing: %b, %b"
		// , leadingContainerWithinLimit
		// , leadingContainerWithinStdDev
		// , trailingContainerWithinLimit
		// , trailingContainerWithinStdDev
		// ));
		// }
		// if(leadingContainerWithinLimit && leadingContainerWithinStdDev &&
		// trailingContainerWithinLimit && trailingContainerWithinStdDev)
		if (isTimedOut()) {
			MessageLogger.LogMessage("Finished slamming into totes");
			finished = true;
		}
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.wallyDriveTrain.TurnOffMotors();
		String msg = this.getClass().getName();
		if (this.isTimedOut()) {
			msg += " timed out.";
			// Scheduler.getInstance().removeAll();
		} else if (this.isCanceled())
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
