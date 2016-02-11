package org.usfirst.frc.team102.robot.subsystems;

import java.util.Vector;

import org.usfirst.frc.team102.robot.Arm;
import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;
import org.usfirst.frc.team102.robot.Arm.State;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArmLift extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Arm arm0;
	public Arm arm1;
	public Arm arm2;
	public Arm arm3;
	public Vector<Arm> armlist;

	public enum armDirection {
		Up, Down
	};

	public ArmLift() {
		try {
			arm0 = new Arm(0);
			arm1 = new Arm(1);
			arm2 = new Arm(2);
			arm3 = new Arm(3);
			armlist = new Vector<Arm>();
			armlist.add(arm0);
			armlist.add(arm1);
			armlist.add(arm2);
			armlist.add(arm3);
		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	public void initDefaultCommand() {

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());

	}

	public void UpdateDriverStation() {
		String msg = "Arm: ";
		for (int i = 0; i < 4; i++) {
			if (RobotMap.isTestBed) {
				if (i != 0) {
					if (!armlist.get(i).armSwitch.get())
						msg += "11";
					else
						msg += "00";
				} else {
					if (armlist.get(i).canArmMotor.isFwdLimitSwitchClosed())
						msg += "1";
					else
						msg += "0";
					if (armlist.get(i).canArmMotor.isRevLimitSwitchClosed())
						msg += "1";
					else
						msg += "0";
				}
			} else {
				if (armlist.get(i).canArmMotor.isFwdLimitSwitchClosed())
					msg += "1";
				else
					msg += "0";
				if (armlist.get(i).canArmMotor.isRevLimitSwitchClosed())
					msg += "1";
				else
					msg += "0";
			}
		}
		SmartDashboard.putString("DB/String 3", msg);
	}

	public void BeginMoveArm(int armNumber) {
		changeState(armNumber);
		double motorSpeed = RobotMap.upArmSpeed;
		if ((armlist.get(armNumber).state == Arm.State.MovingDown))
			motorSpeed = RobotMap.downArmSpeed;
		if (RobotMap.isTestBed) {
			if (armNumber == 0)
				// ONE CANTALON FOR THIS ONE ONLY
				armlist.get(armNumber).canArmMotor.set(motorSpeed);
			else
				armlist.get(armNumber).armMotor.set(motorSpeed);
		} else {
			armlist.get(armNumber).canArmMotor.set(motorSpeed);
		}
		// armlist.get(armNumber).counter.reset();
	}

	public void changeState(int armNumber) {
		if (armlist.get(armNumber).state == State.Down) {
			armlist.get(armNumber).state = State.MovingUp;
		} else if (armlist.get(armNumber).state == State.MovingUp) {
			armlist.get(armNumber).state = State.MovingDown;
		} else if (armlist.get(armNumber).state == State.Up) {
			armlist.get(armNumber).state = State.MovingDown;
		} else if (armlist.get(armNumber).state == State.MovingDown) {
			armlist.get(armNumber).state = State.MovingUp;
		}
	}

	public void StopMoveArm(int armNumber) {
		if (armNumber < 0)
			return;
		if (RobotMap.isTestBed) {
			if (armNumber == 0)
				// ONE CANTALON FOR THIS ONE ONLY
				armlist.get(armNumber).canArmMotor.set(0.0);
			else
				armlist.get(armNumber).armMotor.set(0.0);
		} else {
			armlist.get(armNumber).canArmMotor.set(0.0);
		}
	}

	public void StartMoveArm(int armNumber, double speed) {
		if (armNumber < 0)
			return;
		if (RobotMap.isTestBed) {
			if (armNumber == 0)
				// ONE CANTALON FOR THIS ONE ONLY
				armlist.get(armNumber).canArmMotor.set(speed);
			else
				armlist.get(armNumber).armMotor.set(speed);
		} else {
			armlist.get(armNumber).canArmMotor.set(speed);
		}
	}

	public boolean isArmAtSwitch(int armNumber) {
		if (armNumber < 0)
			return true;
		return (armlist.get(armNumber).armSwitch.get() == false);
	}

	public boolean isArmAtUpperSwitch(int armNumber) {
		if (armNumber < 0)
			return true;
		if((RobotMap.isTestBed) && (armNumber != 0))
			return !armlist.get(armNumber).armSwitch.get();
		else
			return armlist.get(armNumber).canArmMotor.isRevLimitSwitchClosed();
	}

	public boolean isArmAtLowerSwitch(int armNumber) {
		if (armNumber < 0)
			return true;
		if((RobotMap.isTestBed) && (armNumber != 0))
			return !armlist.get(armNumber).armSwitch.get();
		else
			return armlist.get(armNumber).canArmMotor.isFwdLimitSwitchClosed();
	}

	public String canArmSwitchState(int armNumber) {
		if (armNumber < 0)
			return "unknown";
		if (Robot.wallyArmSystem.isArmAtUpperSwitch(armNumber)) {
			if (Robot.wallyArmSystem.isArmAtLowerSwitch(armNumber))
				return "Both";
			else
				return "Upper";
		} else {
			if (Robot.wallyArmSystem.isArmAtLowerSwitch(armNumber))
				return "Lower";
			else
				return "Neither";
		}
	}
}
