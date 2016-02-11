package org.usfirst.frc.team102.robot.subsystems;

import java.util.HashMap;
import java.util.Vector;

import org.usfirst.frc.team102.robot.RobotMap;
import org.usfirst.frc.team102.robot.commands.ReadyToRumble;

import Team102Lib.MessageLogger;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Turntable extends Subsystem {
	private DigitalInput turntableStop0;
	private DigitalInput turntableStop1;
	private DigitalInput turntableStop2;
	private DigitalInput turntableStop3;
	private Counter counter0;
	private Counter counter1;
	private Counter counter2;
	private Counter counter3;
	// private Boolean direction;
	private HashMap<Integer, Counter> counterlist;
	private HashMap<Integer, DigitalInput> switchList;
	public double currentPosition = 0;
	public int targetPosition = 0;
	public Talon turntableMotor;
	private ttDirection direction;
	private Vector<DigitalInput> ttSwitchList;

	public enum ttDirection {
		Counter, ClockWise, Stop, TimeOutStop
	}

	public Turntable() {
		try {
			turntableStop0 = new DigitalInput(RobotMap.turntableStop0);
			turntableStop1 = new DigitalInput(RobotMap.turntableStop1);
			turntableStop2 = new DigitalInput(RobotMap.turntableStop2);
			turntableStop3 = new DigitalInput(RobotMap.turntableStop3);
			if (RobotMap.isTestBed) {
				turntableMotor = new Talon(RobotMap.testbedTurntableTurn);
			} else {
				turntableMotor = new Talon(RobotMap.robotTurntableTurn);
			}
			// direction = true;
			Counter counter0 = new Counter(turntableStop0);
			Counter counter1 = new Counter(turntableStop1);
			Counter counter3 = new Counter(turntableStop3);
			Counter counter2 = new Counter(turntableStop2);
			counterlist = new HashMap<Integer, Counter>();
			counterlist.put(-3, counter1);
			counterlist.put(-2, counter2);
			counterlist.put(-1, counter3);
			counterlist.put(0, counter0);
			counterlist.put(1, counter1);
			counterlist.put(2, counter2);
			counterlist.put(3, counter3);
			switchList = new HashMap<Integer, DigitalInput>();
			switchList.put(-3, turntableStop1);
			switchList.put(-2, turntableStop2);
			switchList.put(-1, turntableStop3);
			switchList.put(0, turntableStop0);
			switchList.put(1, turntableStop1);
			switchList.put(2, turntableStop2);
			switchList.put(3, turntableStop3);
			ttSwitchList = new Vector<DigitalInput>();
			ttSwitchList.add(turntableStop0);
			ttSwitchList.add(turntableStop1);
			ttSwitchList.add(turntableStop2);
			ttSwitchList.add(turntableStop3);
			setAtInitialPosition();
			direction = ttDirection.Stop;

		} catch (Exception ex1) {
			ex1.printStackTrace();
			DriverStation.reportError(ex1.getMessage(), true);
		}
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public boolean isSwitchSet(int switchNumber)
	{
		return !(switchList.get(switchNumber).get());
	}
	public boolean isAtZero() {

		if ((turntableStop0.get() == true)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isTTAtTarget() {
		Counter count = counterlist.get(targetPosition);
		DigitalInput sw = switchList.get(targetPosition);
		MessageLogger.LogMessage(String.format(
				"Turntable: Target Switch: %d Counter: %d, Switch: %b",
				targetPosition, count.get(), sw.get()));
		if ((count.get() > 0) || (sw.get() == false)) {
			currentPosition = targetPosition;
			return true;
		} else {
			return false;
		}
	}

	public void dashboardMessage() {
		if (getLongSideArm() != -1) {
			SmartDashboard.putString("DB/String 0", "LongSideArm is: "
					+ getLongSideArm());
			SmartDashboard.putString("DB/String 1", "ShortSideArm is: "
					+ getShortSideArm());
		} else {
			SmartDashboard.putString("DB/String 0", "LongSideArm is: " + "N/A");
			SmartDashboard
					.putString("DB/String 1", "ShortSideArm is: " + "N/A");
		}

		if (direction == ttDirection.ClockWise) {
			SmartDashboard.putString("DB/String 2", "Move Clockwise.");
			;
		} else if (direction == ttDirection.Counter) {
			SmartDashboard.putString("DB/String 2", "Move Counter.");
		} else if (direction == ttDirection.Stop) {
			SmartDashboard.putString("DB/String 2", "Stop.");
		} else if (direction == ttDirection.TimeOutStop) {
			SmartDashboard.putString("DB/String 2", "Timed out Stop.");
		}
		String msg = "TT Switch: ";
		for (int i = 0; i < 4; i++) {
			if (ttSwitchList.get(i).get())
				msg += "1";
			else
				msg += "0";
		}
		SmartDashboard.putString("DB/String 5", msg);
	}

	public boolean isSwitchPassed0() {
		return counter0.get() > 0;
	}

	public boolean isSwitchPassed1() {
		return counter1.get() > 0;
	}

	public boolean isSwitchPassed2() {
		return counter2.get() > 0;
	}

	public boolean isSwitchPassed3() {
		return counter3.get() > 0;
	}

	public void resetCounters() {
		for (HashMap.Entry<Integer, Counter> c : counterlist.entrySet()) {
			c.getValue().reset();
		}
	}

	public boolean beginCounterWise() {
		direction = ttDirection.Counter;
		currentPosition = Math.min(currentPosition + 0.5, 3.0);
		turntableMotor.set(RobotMap.TurnTableCounterClockwiseSpeed);
		resetCounters();
		targetPosition = Math.min((int) Math.floor(currentPosition + 1), 3);
		if (Math.floor(currentPosition + 1) > 3) {
			Scheduler.getInstance().add(new ReadyToRumble());
			return false;
		} else
			return true;
	}
	
	public boolean beginClockwise() {
		currentPosition = Math.max(currentPosition - 0.5, -3.0);
		direction = ttDirection.ClockWise;
		turntableMotor.set(RobotMap.TurnTableClockwiseSpeed);
		resetCounters();
		targetPosition = Math.max((int) Math.ceil(currentPosition - 1), -3);
		if (Math.ceil(currentPosition - 1) < -3) {
			Scheduler.getInstance().add(new ReadyToRumble());
			return false;
		} else
			return true;
	}

	public double tellPosition() {
		return currentPosition;
	}

	public void setAtZero() {
		direction = ttDirection.Stop;
		currentPosition = 0;
		targetPosition = 1;
	}

	public void setAtInitialPosition() {
		direction = ttDirection.Stop;
		currentPosition = RobotMap.initialTurntablePosition;
		targetPosition = 0;
	}

	public void checkTimeOut(boolean timedout) {
		if (timedout == true) {
			direction = ttDirection.TimeOutStop;
		}
	}

	public void stopMessage() {
		direction = ttDirection.Stop;
	}

	public int getLongSideArm() {
		if (Math.floor(currentPosition) - currentPosition != 0) {
			return -1;
		} else {
			if (currentPosition > -1) {
				return (int) currentPosition;
			} else {
				return (int) (currentPosition + 4);
			}
		}
	}

	public int findShortFromLong() {
		if (getLongSideArm() != -1) {
			return (getLongSideArm() + 2) % 4;
		} else {
			return -1;
		}
	}

	public int getShortSideArm() {
		if ((Math.floor(currentPosition) - currentPosition != 0)
				|| findShortFromLong() == -1) {
			return -1;
		} else {
			return (int) findShortFromLong();
		}
	}
}
