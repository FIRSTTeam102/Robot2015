package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.subsystems.DriveTrain.AutoDriveDirection;
import org.usfirst.frc.team102.robot.subsystems.Turntable.ttDirection;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 */
public class Autonomous extends CommandGroup {

	public Autonomous() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// THIS IS THE ACTUAL AUTONOMOUS CODE!! MAKE SURE THE TEST CODE BELOW IS
		// COMMENTED OUT!!!
		//addSequential(new DriveToStep()); // Works

		// Drive to the first container.
		//addSequential(new DriveTowardFirstContainer()); // Works
		// addSequential(new
		// PrintCommand(DriveTowardFirstContainer.class.getName() +
		// " completed"));
		//addSequential(new TurnClockwise());
		addSequential(new BarelyLiftArm(true)); // bring the arm up to engage
		// the container.
		 addSequential(new PullOutContainer(AutoDriveDirection.forward,
		 ttDirection.ClockWise));
		// addSequential(new PrintCommand(PullOutContainer.class.getName()
		// +" completed"));

		// Drive to the second container.
		// addSequential(new DriveAlongStep(AutoDriveDirection.forward, 2.0));
		// this
		// timeout
		// is
		// a
		// guess.
		// TODO: need to know if the above timed out.

		// addSequential(new BarelyLiftArm(true)); // bring the arm up to engage
		// the container.
		// addSequential(new PullOutContainer(AutoDriveDirection.forward,
		// ttDirection.ClockWise));
		// addSequential(new TurnClockwise()); // A total of 180 degress
		// clockwise.

		// Drive to the third container.
		// addSequential(new DriveAlongStepToSpace(AutoDriveDirection.reverse,
		// 2.0)); // this timeout is a guess.
		// addSequential(new DriveAlongStepToSpace(AutoDriveDirection.reverse,
		// 2.0)); // this timeout is a guess.
		// addSequential(new DriveAlongStepToSpace(AutoDriveDirection.reverse,
		// 2.0)); // this timeout is a guess.

		// TODO: need to know if the above timed out.

		// addSequential(new BarelyLiftArm(true)); // bring the arm up to engage
		// the container.
		// addSequential(new PullOutContainer(AutoDriveDirection.reverse,
		// ttDirection.Counter));

		// Drive to the fourth container.
		// addSequential(new DriveAlongStep(AutoDriveDirection.reverse, 2.0));
		// // this timeout is a guess.
		// addSequential(new MoveSideArm(true));

		// addSequential(new ThreeCheekHaul()));

		// THIS IS THE TEST AUTONOMOUS CODE!! MAKE SURE IT IS COMMENTED OUT
		// BEFORE GOING ON THE FIELD.

		// Test 1
		 //addSequential(new DriveAndTurnTable());


		// Test 2 - Can be tested in the pit
		// addSequential(new TurnTableWithDelay());

		// Test 3
		// addParallel(new FindContainer(AutoDriveDirection.forward, 2.5));

		// Test 4 -- From start to Finding first container.
		// addSequential(new DriveToStep()); // Works
		// addSequential(new DriveTowardFirstContainer()); // Works

		// Test 5 - Can be tested in the pit
		// addSequential(new BarelyLiftArm(true)); // bring the arm up to engage
		// the container.
		// addSequential(new PullOutContainer(AutoDriveDirection.reverse,
		// ttDirection.Counter));

	}
}
