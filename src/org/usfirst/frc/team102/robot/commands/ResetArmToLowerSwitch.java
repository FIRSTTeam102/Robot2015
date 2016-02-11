package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetArmToLowerSwitch extends Command {

	int armNumber;
    public ResetArmToLowerSwitch(int armNumber) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wallyArmSystem);
    	this.armNumber = armNumber;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.setTimeout(RobotMap.armResetLowerSearchTimeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.wallyArmSystem.StartMoveArm(armNumber, RobotMap.downArmSpeed);    		
		MessageLogger.LogMessage(String.format("arm %d searching for lower switch.", armNumber));

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.wallyArmSystem.isArmAtLowerSwitch(armNumber))
    		MessageLogger.LogMessage(String.format("arm %d found lower switch.", armNumber));
    	else if(this.isTimedOut())
    		MessageLogger.LogMessage(String.format("arm %d timed out looking for lower switch.", armNumber));
    	return ((Robot.wallyArmSystem.isArmAtLowerSwitch(armNumber)) || (this.isTimedOut()));
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.wallyArmSystem.StopMoveArm(armNumber);
		String msg = this.getClass().getName();
		if(this.isTimedOut()) {
			msg += " timed out (finished).";
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
    }
}
