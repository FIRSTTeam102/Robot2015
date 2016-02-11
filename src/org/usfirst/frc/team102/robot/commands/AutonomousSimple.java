package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousSimple extends Command {

    public AutonomousSimple() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wallyDriveTrain);
    	this.setTimeout(1.32);
    	
    	// this.setTimeout(5.0);	// Testing
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.wallyDriveTrain.driveStrafeInAuto(true, 0.5);
    	System.out.println("Currently running Autonomous Simple");
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
    	Robot.wallyDriveTrain.TurnOffMotors();
    	System.out.println("Stopped Running Autonomous Simple");
    	System.out.println("Front Strafe Speed: " + Robot.wallyDriveTrain.frontStrafe.get());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
