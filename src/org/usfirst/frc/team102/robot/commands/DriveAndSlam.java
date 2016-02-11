package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.subsystems.DriveTrain.AutoDriveDirection;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveAndSlam extends CommandGroup {
    
    public  DriveAndSlam() {
     
    	addSequential(new DriveAndTurnTable());
		addSequential(new SlamIntoTotes(AutoDriveDirection.reverse, 0.25));
    }
}
