package org.usfirst.frc.team102.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team102.robot.Robot;

/**
 *
 */
public class DriveWithXBox extends Command {

	public DriveWithXBox() {
		requires(Robot.wallyDriveTrain); // reserve the chassis subsystem
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// MessageLogger.LogMessage("DriveWithXBox Initalized");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		try {
			Robot.wallyDriveTrain
					.driveWithXBox(Robot.oi.getDriverXBox(), false);
			Robot.wallyDriveTrain.dashboardMessage();
			Robot.wallyPDP.LogStats();
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();

	}
}
