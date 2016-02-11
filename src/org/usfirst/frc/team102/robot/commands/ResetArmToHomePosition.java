package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetArmToHomePosition extends Command {

	int armNumber;
	boolean findingLowerSwitch = false;
    public ResetArmToHomePosition(int armNumber) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wallyArmSystem);
    	this.armNumber = armNumber;
    	this.setTimeout(RobotMap.armResetFromBelowTime);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
    	if(Robot.wallyArmSystem.isArmAtLowerSwitch(armNumber))
    	{
    		this.setTimeout(RobotMap.armResetFromBelowTime);
    		Robot.wallyArmSystem.StartMoveArm(armNumber, -RobotMap.downArmSpeed);	// Go at the slower speed.
    		MessageLogger.LogMessage(String.format("arm %d is at lower switch", armNumber));
    	}
    	else if(Robot.wallyArmSystem.isArmAtUpperSwitch(armNumber))
    	{
    		this.setTimeout(RobotMap.armResetFromAboveTime);
    		Robot.wallyArmSystem.StartMoveArm(armNumber, RobotMap.downArmSpeed);
    		MessageLogger.LogMessage(String.format("arm %d is at upper switch", armNumber));
    	}
    	else
    	{
    		MessageLogger.LogMessage(String.format("arm %d is not at either switch", armNumber));
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if(findingLowerSwitch)
//    	{
//        	if(Robot.wallyArmSystem.isArmAtLowerSwitch(armNumber))
//        	{
//        		MessageLogger.LogMessage(String.format("arm %d is at lower switch resetting timeout.", armNumber));
////        		It appears we cannot reset a timeout in the middle of a command.
////        		this.setTimeout(RobotMap.armResetFromBelowTime);
//        		Robot.wallyArmSystem.StartMoveArm(armNumber, -RobotMap.downArmSpeed);		// Go at the slower speed.   
//        		findingLowerSwitch = false;
//        	}
//    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.wallyArmSystem.StopMoveArm(armNumber);
		String msg = this.getClass().getName();
		if (findingLowerSwitch)
			msg += " ResetArm timed out looking for lower switch.";
		else if(this.isTimedOut()) {
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
    	end();
    }
}
