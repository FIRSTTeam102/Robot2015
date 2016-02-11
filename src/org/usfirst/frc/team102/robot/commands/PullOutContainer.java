package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.subsystems.DriveTrain.AutoDriveDirection;
import org.usfirst.frc.team102.robot.subsystems.Turntable.ttDirection;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 */
public class PullOutContainer extends CommandGroup {
    
    public  PullOutContainer(AutoDriveDirection direction, ttDirection turnDirection) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
		addSequential(new PrintCommand(this.getClass().getName()));
    	addParallel(new MoveSideArm(true));						// Long side arm.
    	addParallel(new DriveAlongStepSlowSpeed(direction));
//    	if(turnDirection == ttDirection.ClockWise)
 		addParallel(new TurnClockwiseWithDelay());
//    	else
//    		addParallel(new TurnCounterwiseWithDelay());
    	//addParallel(new TurnClockwiseWithDelay());
    }
}
