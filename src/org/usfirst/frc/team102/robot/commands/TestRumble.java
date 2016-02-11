package org.usfirst.frc.team102.robot.commands;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestRumble extends Command {
	Joystick xBoxTester;

	public TestRumble(Joystick xBoxTester) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.xBoxTester = xBoxTester;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
		xBoxTester.setRumble(RumbleType.kRightRumble, 1);
		xBoxTester.setRumble(RumbleType.kLeftRumble, 1);
		System.out.println("Rumble is on");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		xBoxTester.setRumble(RumbleType.kRightRumble, 0);
		xBoxTester.setRumble(RumbleType.kLeftRumble, 0);
		System.out.println("Rumble is off");
	}
}
