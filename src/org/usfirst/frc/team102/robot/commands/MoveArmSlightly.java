package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Arm;
import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.subsystems.ArmLift.armDirection;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveArmSlightly extends Command {
	armDirection direction;
	Arm armToMove = Robot.wallyArmSystem.arm0;

	public MoveArmSlightly(armDirection direction) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.wallyArmSystem);
		this.direction = direction;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
		if (direction == armDirection.Up)
			armToMove.canArmMotor.set(-0.75);
		else if (direction == armDirection.Down)
			armToMove.canArmMotor.set(0.25);
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
		armToMove.canArmMotor.set(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
