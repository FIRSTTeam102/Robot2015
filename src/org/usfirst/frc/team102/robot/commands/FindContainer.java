package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;
import org.usfirst.frc.team102.robot.subsystems.DriveTrain.AutoDriveDirection;

import Team102Lib.MessageLogger;
import Team102Lib.MovingStat;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class FindContainer extends Command {

	public AutoDriveDirection direction;
	double[] distanceMeasurements = new double[2];

	MovingStat leadingStats = null;
	MovingStat trailingStats = null;
	double leadingUpper = 0.0;
	double leadingLower = 0.0;
	double trailingUpper = 0.0;
	double trailingLower = 0.0;
	double leadingUpperStdDev = 0.0;
	double trailingUpperStdDev = 0.0;
	int point = 0;

	public FindContainer(AutoDriveDirection direction, double timeoutLength) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.wallyDriveTrain);
		this.direction = direction;
		this.setTimeout(timeoutLength);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		MessageLogger.LogMessage(this.getClass().getName());

		Robot.wallyDriveTrain.SetUpDistanceSensor();
		Robot.wallyDriveTrain.DriveAlongStep(direction, 1.0);

		if (direction == AutoDriveDirection.forward) {
			leadingStats = Robot.wallyDriveTrain.frontMovingStats;
			trailingStats = Robot.wallyDriveTrain.rearMovingStats;
			leadingUpper = RobotMap.ForwardDistDetectUpperThreshold;
			leadingLower = RobotMap.ForwardDistDetectLowerThreshold;
			trailingUpper = RobotMap.RearDistDetectUpperThreshold;
			trailingLower = RobotMap.RearDistDetectLowerThreshold;
			leadingUpperStdDev = RobotMap.ForwardDistStdDevThreshold;
			trailingUpperStdDev = RobotMap.RearDistStdDevThreshold;
		} else {
			leadingStats = Robot.wallyDriveTrain.rearMovingStats;
			trailingStats = Robot.wallyDriveTrain.frontMovingStats;

			leadingUpper = RobotMap.RearDistDetectUpperThreshold;
			leadingLower = RobotMap.RearDistDetectLowerThreshold;
			leadingUpperStdDev = RobotMap.RearDistStdDevThreshold;

			trailingUpper = RobotMap.ForwardDistDetectUpperThreshold;
			trailingLower = RobotMap.ForwardDistDetectLowerThreshold;
			trailingUpperStdDev = RobotMap.ForwardDistStdDevThreshold;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// Get the measurements from the distance sensors.
		Robot.wallyDriveTrain.getDistanceSensors(distanceMeasurements); // This also updates the stats.
		point++;

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		System.out.println(String.format("%f, %f", leadingStats.average, trailingStats.average));
		boolean finished = false;
		boolean leadingContainerWithinLimit = ((leadingStats.average > leadingLower) && (leadingStats.average < leadingUpper));
		boolean leadingContainerWithinStdDev = (leadingStats.stdDev < leadingUpperStdDev);
		boolean trailingContainerWithinLimit = ((trailingStats.average > trailingLower) && (trailingStats.average < trailingUpper));
		boolean trailingContainerWithinStdDev = (trailingStats.stdDev < trailingUpperStdDev);

		// The following line should be commented out in the real robot.
		// MessageLogger.LogMessage(String.format("%d: leading: %5.2f, %5.3f : trailing: %5.2f, %5.3f"
		// , point
		// , leadingStats.average
		// , leadingStats.stdDev
		// , trailingStats.average
		// , trailingStats.stdDev
		// ));
		//
		// // This should also be commented out on the real robot.
		// if(leadingContainerWithinLimit || leadingContainerWithinStdDev ||
		// trailingContainerWithinLimit || trailingContainerWithinStdDev)
		// {
		// MessageLogger.LogMessage(String.format("leading: %b, %b : trailing: %b, %b"
		// , leadingContainerWithinLimit
		// , leadingContainerWithinStdDev
		// , trailingContainerWithinLimit
		// , trailingContainerWithinStdDev
		// ));
		// }
		// if(leadingContainerWithinLimit && leadingContainerWithinStdDev &&
		// trailingContainerWithinLimit && trailingContainerWithinStdDev)
		if (leadingContainerWithinLimit && trailingContainerWithinLimit)
			finished = true;
		else if (isTimedOut()) {
			MessageLogger.LogMessage("Timed out looking for container.");
			finished = true;
		}
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.wallyDriveTrain.TurnOffMotors();
		Robot.wallyDriveTrain.CloseDistanceSensor();
		String msg = this.getClass().getName();
		if (this.isTimedOut()) {
			msg += " timed out.";
			// Scheduler.getInstance().removeAll();
			Scheduler.getInstance().add(new StopAllMotors());
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
