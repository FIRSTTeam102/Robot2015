package org.usfirst.frc.team102.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// THIS IS WHERE WE CHANGE BETWEEN TEST BED AND ROBOT!!!!!!!!
	public static boolean isTestBed;
	public static boolean isSimpleAutonomous;
	// MOTOR CONTROLLERS DEFINED HERE
	public static final int frontLeft = 9;
	public static final int frontStrafe = 8;
	public static final int frontRight = 6;
	// public static final int rearLeft = 9;
	public static final int rearStrafe = 7;
	// public static final int rearRight = 6;
	public static final int armLift0 = 1;
	public static final int armLift1 = 2;
	public static final int armLift2 = 3;
	public static final int armLift3 = 4;
	public static final int robotTurntableTurn = 0;
	public static final int testbedTurntableTurn = 5;

	// CANTALONS DEFINED HERE
	public static final int canFrontLeft = 1;
	public static final int canFrontStrafe = 2;
	public static final int canFrontRight = 3;
	public static final int canRearLeft = 4;
	public static final int canRearStrafe = 5;
	public static final int canRearRight = 6;
	public static final int canArm0 = 7;
	public static final int canArm1 = 8;
	public static final int canArm2 = 9;
	public static final int canArm3 = 10;

	// JOYSTICK AXES HERE
	public static final int xBoxLeftXAxis = 0;
	public static final int xBoxLeftYAxis = 1;
	public static final int xBoxLeftTriggerAxis = 2; // Left trigger 0.0-0.5,
	public static final int xBoxRightTriggerAxis = 3; // Right trigger 0.5-1.0
	public static final int xBoxRightXAxis = 4;
	public static final int xBoxRightYAxis = 5;
	public static final int xBoxDPadHorizontalAxis = 6;

	// XBox Controller Button Indexes
	public static final int xBoxAIndex = 1;
	public static final int xBoxBIndex = 2;
	public static final int xBoxXIndex = 3;
	public static final int xBoxYIndex = 4;
	public static final int xBoxLeftBumperIndex = 5;
	public static final int xBoxRightBumperIndex = 6;
	public static final int xBoxBackButtonIndex = 7;
	public static final int xBoxStartButtonIndex = 8;
	public static final int xBoxLeftJoystickPress = 9;
	public static final int xBoxRightJoystickPress = 10;
	public static final int driverJoystickPort = 0;
	public static final int operatorJoystickPort = 2;
	public static final int testJoystickPort = 3;

	// DIGITAL INPUTS DEFINED HERE
	public static final int DistanceSensor = 0;
	public static final int armStop1 = 2;
	public static final int armStop2 = 3;
	public static final int armStop3 = 4;
	public static final int turntableStop0 = 6;
	public static final int turntableStop1 = 7;
	public static final int turntableStop2 = 8;
	public static final int turntableStop3 = 9;
	public static final int wedgePosition = 14;

	// ANALOG INPUTS HERE.
	public static final int gyroChannel = 0;
	public static final int FrontDistanceSensor = 1;
	public static final int RearDistanceSensor = 2;

	// PNEUMATICS DEFINED HERE
	public static final int wingSolenoid = 1;

	// constants are defined here
	public static final int sensorDetectThreshhold = 10;
	public static final int sensorUndetectThreshhold = -1;
	public static final double downArmSpeed = 0.25; // Speed to move arms down.
	public static final double upArmSpeed = -0.75;
	public static final double initialTurntablePosition = 1.0;
	public static final double GyroCounterClockwiseLimit = -5.0; // Angle at which to cancel autonomous if drive to step is askew.
	public static final double GyroClockwiseLimit = 8.0; //
	public static final double EighthTurnTime = 0.67; // Time to turn the turntable 1/8 of a turn.
	public static final double TurnTableClockwiseSpeed = -0.20;
	public static final double TurnTableCounterClockwiseSpeed = -TurnTableClockwiseSpeed;

	// Thresholds for detecting the container on the Forward and Rear distance
	// sensors. If the moving average of
	// both sensors is within this range, the container is considered detected.
	public static final double ForwardDistDetectUpperThreshold = 2200;
	public static final double ForwardDistDetectLowerThreshold = 1700;

	public static final double RearDistDetectUpperThreshold = 2200;
	public static final double RearDistDetectLowerThreshold =1700;

	// public static final double ForwardDistDetectUpperThreshold = 1500;
	// public static final double ForwardDistDetectLowerThreshold = 850;
	//
	// public static final double RearDistDetectUpperThreshold = 1500;
	
	// public static final double RearDistDetectLowerThreshold = 850;

	// Standard deviation of the forward and rear distance sensors required for
	// container to be considered detected.
	public static final double ForwardDistStdDevThreshold = 100;
	public static final double RearDistStdDevThreshold = 100;

	public static final boolean logPDPStats = false; // indicate whether we should log statistics about the Power Distribution Panel

	// Arm Reset Timeouts.
	public static final double armResetFromBelowTime = 0.6;
	public static final double armResetFromAboveTime = 2.0;
	public static final double armResetLowerSearchTimeout = 3.0;
}
