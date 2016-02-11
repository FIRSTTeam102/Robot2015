package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnCounterwise extends Command {
	public TurnCounterwise() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.wallyTurntable);
		this.setTimeout(2.5);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
		Robot.wallyTurntable.beginCounterWise();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// System.out.println("Turn Counterwise is running");
		Robot.wallyTurntable.dashboardMessage();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (isTimedOut()) {
			System.out.println("Timed Out");
			Robot.wallyTurntable.checkTimeOut(true);
			Robot.wallyTurntable.dashboardMessage();
		} else if (Robot.wallyTurntable.isTTAtTarget())
			Robot.wallyTurntable.checkTimeOut(false);

		return Robot.wallyTurntable.isTTAtTarget() || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.wallyTurntable.turntableMotor.set(0);
//		Robot.wallyTurntable.tellPosition(currentPosition);
		if (!isTimedOut()) {
			Robot.wallyTurntable.stopMessage();
		}
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
