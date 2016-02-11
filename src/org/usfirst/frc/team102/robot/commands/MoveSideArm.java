package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;
import org.usfirst.frc.team102.robot.Arm.State;
import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class MoveSideArm extends Command {
	private int armNumber = -1;
	private boolean offTheSwitch;
	private boolean longSide = true;

	public MoveSideArm(boolean isLongSide) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.wallyArmSystem);
		longSide = isLongSide;
		if (RobotMap.isTestBed)
			this.setTimeout(10.0);
		else
			this.setTimeout(3.0);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
		offTheSwitch = false;
		if (longSide) {
			armNumber = Robot.wallyTurntable.getLongSideArm();
			if (armNumber == -1) {
				this.cancel();
				Scheduler.getInstance().add(new ReadyToRumble());
			} else {
				System.out.println(String.format(
						"Moving arm %d from state %s on the LONG side.",
						armNumber,
						Robot.wallyArmSystem.armlist.get(armNumber).state));
				Robot.wallyArmSystem.BeginMoveArm(armNumber);
			}
		} else {
			armNumber = Robot.wallyTurntable.getShortSideArm();
			if (armNumber == -1) {
				this.cancel();
				Scheduler.getInstance().add(new ReadyToRumble());
			} else {
				System.out.println(String.format(
						"Moving arm %d from state %s on the SHORT side.",
						armNumber,
						Robot.wallyArmSystem.armlist.get(armNumber).state));
				Robot.wallyArmSystem.BeginMoveArm(armNumber);
			}
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (RobotMap.isTestBed && (armNumber != 0))
			if (!Robot.wallyArmSystem.isArmAtSwitch(armNumber))
				offTheSwitch = true;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (armNumber < 0)
			return true;
		boolean finished = false;
		String switchState = "unknown";
		if (RobotMap.isTestBed) {
			if (armNumber == 0) {
				if (Robot.wallyArmSystem.armlist.get(armNumber).state == State.MovingUp)
					finished = (Robot.wallyArmSystem
							.isArmAtUpperSwitch(armNumber) || isTimedOut());
				else
					finished = (Robot.wallyArmSystem
							.isArmAtLowerSwitch(armNumber) || isTimedOut());
				switchState = Robot.wallyArmSystem.canArmSwitchState(armNumber);
			} else {
				finished = (offTheSwitch
						&& Robot.wallyArmSystem.isArmAtSwitch(armNumber) || isTimedOut());
				if (Robot.wallyArmSystem.isArmAtSwitch(armNumber))
					switchState = "true";
				else
					switchState = "false";
			}
		} else {
			if (Robot.wallyArmSystem.armlist.get(armNumber).state == State.MovingUp)
				finished = (Robot.wallyArmSystem.isArmAtUpperSwitch(armNumber) || isTimedOut());
			else
				finished = (Robot.wallyArmSystem.isArmAtLowerSwitch(armNumber) || isTimedOut());
			switchState = Robot.wallyArmSystem.canArmSwitchState(armNumber);
		}
		System.out
				.println(String
						.format("ArmNumber: %d, isTimedOut: %b, offTheSwitch: %b, switchState %s, ArmState: %s, armMotor: %f",
								armNumber,
								isTimedOut(),
								offTheSwitch,
								switchState,
								Robot.wallyArmSystem.armlist.get(armNumber).state,
								(RobotMap.isTestBed && armNumber != 0) ? Robot.wallyArmSystem.armlist
										.get(armNumber).armMotor.get()
										: Robot.wallyArmSystem.armlist
												.get(armNumber).canArmMotor
												.get()));
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
		if (armNumber < 0)
			return;
		Robot.wallyArmSystem.StopMoveArm(armNumber);
		if (RobotMap.isTestBed) {
			if (armNumber == 0)
				if (Robot.wallyArmSystem.isArmAtUpperSwitch(armNumber))
					Robot.wallyArmSystem.armlist.get(armNumber).state = State.Up;
				else if (Robot.wallyArmSystem.isArmAtLowerSwitch(armNumber))
					Robot.wallyArmSystem.armlist.get(armNumber).state = State.Down;
				else {
					// In the case of time out, just leave the state as is.
				}
			else {
				if ((!isTimedOut() && Robot.wallyArmSystem.armlist
						.get(armNumber).state == State.MovingUp))
					Robot.wallyArmSystem.armlist.get(armNumber).state = State.Up;
				else if (!isTimedOut()
						&& (Robot.wallyArmSystem.armlist.get(armNumber).state == State.MovingDown))
					Robot.wallyArmSystem.armlist.get(armNumber).state = State.Down;
			}
		} else {
			if (Robot.wallyArmSystem.isArmAtUpperSwitch(armNumber))
				Robot.wallyArmSystem.armlist.get(armNumber).state = State.Up;
			else if (Robot.wallyArmSystem.isArmAtLowerSwitch(armNumber))
				Robot.wallyArmSystem.armlist.get(armNumber).state = State.Down;
			else {
				// In the case of time out, just leave the state as is.
			}
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
