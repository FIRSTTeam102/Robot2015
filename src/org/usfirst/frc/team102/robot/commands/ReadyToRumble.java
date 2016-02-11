package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReadyToRumble extends Command {

	public ReadyToRumble() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
		Robot.driverJoystick.setRumble(RumbleType.kRightRumble, 1);
		Robot.driverJoystick.setRumble(RumbleType.kLeftRumble, 1);
		System.out.println("Rumble is on");
		this.setTimeout(0.5);
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
		Robot.driverJoystick.setRumble(RumbleType.kRightRumble, 0);
		Robot.driverJoystick.setRumble(RumbleType.kLeftRumble, 0);
		System.out.println("Rumble is off");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
