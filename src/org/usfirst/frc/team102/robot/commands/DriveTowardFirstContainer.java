package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.subsystems.DriveTrain.AutoDriveDirection;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 */
public class DriveTowardFirstContainer extends CommandGroup {

	public DriveTowardFirstContainer() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
		addSequential(new PrintCommand(this.getClass().getName()));
		addSequential(new SlamIntoTotes(AutoDriveDirection.reverse, 0.25));
		addParallel(new TurnClockwise());
		addSequential(new PrintCommand(this.getClass().getName()));
		// addParallel(new FindContainer(AutoDriveDirection.forward, 7.5));	// Testing
		addParallel(new FindContainer(AutoDriveDirection.forward, 2.0));
		//addParallel(new DropWingWithDelay());
		// addParallel(new TurnTableWithDelay()); // for now
	}
//	@Override
//	protected void end() {
//		super.end();
//		String msg = this.getClass().getName();
//		if (this.isTimedOut())
//			msg += " timed out.";
//		else if (this.isCanceled())
//			msg += " cancelled.";
//		else
//			msg += " finished.";
//		MessageLogger.LogMessage(msg);
//	}
}
