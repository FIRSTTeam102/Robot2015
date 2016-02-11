package org.usfirst.frc.team102.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Talon;

public class Arm {
	public Talon armMotor;
	public CANTalon canArmMotor;
	public DigitalInput armSwitch;
	public State state;

	public enum State {
		Up, Down, MovingUp, MovingDown
	}

	public Arm(int armNumber) {
		try {
			state = State.Down;
			if (RobotMap.isTestBed) {
				if (armNumber == 0) {
					// ONE CANTALON FOR THIS ONE ONLY
					canArmMotor = new CANTalon(RobotMap.canArm0 + armNumber);
					canArmMotor.ConfigFwdLimitSwitchNormallyOpen(true);
					canArmMotor.ConfigRevLimitSwitchNormallyOpen(true);
					canArmMotor.enableLimitSwitch(true, true);
				} else
				{
					armSwitch = new DigitalInput(RobotMap.armStop1 + armNumber - 1);
					armMotor = new Talon(RobotMap.armLift0 + armNumber);
				}
			} else {
				canArmMotor = new CANTalon(RobotMap.canArm0 + armNumber);
				canArmMotor.ConfigFwdLimitSwitchNormallyOpen(true);
				canArmMotor.ConfigRevLimitSwitchNormallyOpen(true);
				canArmMotor.enableLimitSwitch(true, true);
			}
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	public void findState() {
		if (state == State.Down) {
			state = State.MovingUp;
		}
		if (state == State.MovingUp) {
			state = State.Up;
		}
		if (state == State.Up) {
			state = State.MovingDown;
		}
		if (state == State.MovingDown) {
			state = State.Down;
		}
	}

}
