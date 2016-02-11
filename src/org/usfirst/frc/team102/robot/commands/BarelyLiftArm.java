package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BarelyLiftArm extends Command {
	private boolean longSide = true;
	private int armNumber = 0;

	public BarelyLiftArm(boolean isLongSide) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.wallyArmSystem);
		longSide = isLongSide;
		this.setTimeout(0.125);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
		if (longSide) {
			armNumber = Robot.wallyTurntable.getLongSideArm();
			MessageLogger.LogMessage(String.format("Barely lift Arm to lift is: %d on LONG side.", armNumber));
		} else {
			armNumber = Robot.wallyTurntable.getShortSideArm();
			MessageLogger.LogMessage(String.format("Barely lift Arm to lift is: %d on SHORT side.", armNumber));
		}
		if (RobotMap.isTestBed && (armNumber != 0))
			Robot.wallyArmSystem.armlist.get(armNumber).armMotor.set(-1.0);
		else
			Robot.wallyArmSystem.armlist.get(armNumber).canArmMotor.set(-1.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if ((RobotMap.isTestBed) && (armNumber != 0)) {
			return (this.isTimedOut() || Robot.wallyArmSystem.isArmAtSwitch(armNumber));
		} else {
			return (this.isTimedOut() || Robot.wallyArmSystem.isArmAtUpperSwitch(armNumber));
		}
	}
	// Called once after isFinished returns true
	protected void end() {
		if (RobotMap.isTestBed && (armNumber != 0))
			Robot.wallyArmSystem.armlist.get(armNumber).armMotor.set(0.0);
		else
			Robot.wallyArmSystem.armlist.get(armNumber).canArmMotor.set(0.0);
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
