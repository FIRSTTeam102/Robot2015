package org.usfirst.frc.team102.robot.commands;

import org.usfirst.frc.team102.robot.subsystems.DriveTrain.AutoDriveDirection;
import org.usfirst.frc.team102.robot.subsystems.Turntable.ttDirection;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LiftArmAndGetContainer extends CommandGroup {
    
    public  LiftArmAndGetContainer() {
		 addSequential(new BarelyLiftArm(true));
		 addSequential(new PullOutContainer(AutoDriveDirection.forward, ttDirection.ClockWise));
    }
}
