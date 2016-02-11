package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetTurntableToZero extends Command {

	int numSwitchesSeen = 0;
	boolean switchSeen = false;
    public ResetTurntableToZero() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wallyTurntable);
    	this.setTimeout(2.0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());
    	// if we are already at the home position.  Do not move.
    	if(!Robot.wallyTurntable.isSwitchSet(0))
    	{
    		// Assume the turntable knows its position
    		double pos = Robot.wallyTurntable.currentPosition;
    		if(pos < 0.0)
    		{
    			// turn counterclockwise
    			Robot.wallyTurntable.turntableMotor.set(RobotMap.TurnTableCounterClockwiseSpeed);
    		}
    		else if(pos > 0.0)
    		{
    			Robot.wallyTurntable.turntableMotor.set(RobotMap.TurnTableClockwiseSpeed);
    		}
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if(Robot.wallyTurntable.currentPosition == RobotMap.initialTurntablePosition)
    	
    	// if we are already at the home position.  Do not move.
    	if(!Robot.wallyTurntable.isSwitchSet(0))
    	{
    		for(int i = 1; i < 4; i++)
    		{
    			if((!switchSeen) || (Robot.wallyTurntable.isSwitchSet(i)))
    			{
    				numSwitchesSeen++;
    				this.setTimeout(2.0);
    				switchSeen = true;
    			}
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return (Robot.wallyTurntable.isSwitchSet(0) || this.isTimedOut() || (numSwitchesSeen == 3));
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.wallyTurntable.turntableMotor.set(0.0);
    	Robot.wallyTurntable.setAtInitialPosition();

		String msg = this.getClass().getName();
		if (numSwitchesSeen >= 3)
			msg += " too many switches seen.";
		else if(this.isTimedOut()) {
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
