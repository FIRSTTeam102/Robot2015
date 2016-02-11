package org.usfirst.frc.team102.robot;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team102.robot.commands.DriveAndFindContainer;
import org.usfirst.frc.team102.robot.commands.DriveAndSlam;
import org.usfirst.frc.team102.robot.commands.DriveAndTurnTable;
import org.usfirst.frc.team102.robot.commands.DriveToStep;
import org.usfirst.frc.team102.robot.commands.DriveTowardFirstContainer;
import org.usfirst.frc.team102.robot.commands.DriveWithXBox;
import org.usfirst.frc.team102.robot.commands.FindContainer;
import org.usfirst.frc.team102.robot.commands.LiftArmAndGetContainer;
import org.usfirst.frc.team102.robot.commands.MoveArmSlightly;
import org.usfirst.frc.team102.robot.commands.MoveSideArm;
import org.usfirst.frc.team102.robot.commands.ReadyToRumble;
import org.usfirst.frc.team102.robot.commands.ResetArm;
import org.usfirst.frc.team102.robot.commands.ResetArmToHomePosition;
import org.usfirst.frc.team102.robot.commands.ResetArms;
import org.usfirst.frc.team102.robot.commands.ResetTurntableToZero;
import org.usfirst.frc.team102.robot.commands.ShutDownSequence;
import org.usfirst.frc.team102.robot.commands.SlamIntoTotes;
import org.usfirst.frc.team102.robot.commands.StrafeToggle;
import org.usfirst.frc.team102.robot.commands.TestDrive;
import org.usfirst.frc.team102.robot.commands.TestTurnTable;
import org.usfirst.frc.team102.robot.commands.TurnClockwise;
import org.usfirst.frc.team102.robot.commands.TurnClockwiseEnd;
import org.usfirst.frc.team102.robot.commands.TurnCounterwise;
import org.usfirst.frc.team102.robot.commands.TurnCounterwiseEnd;
import org.usfirst.frc.team102.robot.commands.TurnEighth;
import org.usfirst.frc.team102.robot.commands.TurnTableWithDelay;
import org.usfirst.frc.team102.robot.subsystems.ArmLift.armDirection;
import org.usfirst.frc.team102.robot.subsystems.DriveTrain.AutoDriveDirection;
import org.usfirst.frc.team102.robot.subsystems.Turntable.ttDirection;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// // CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	// // TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	private Joystick xBoxDriver;
	private Joystick xBoxTester;
	private JoystickButton xBoxRightBump;
	private JoystickButton xBoxLeftBump;
	private JoystickButton xBoxY;
	private JoystickButton xBoxA;
	private JoystickButton xBoxLeftJoyBotton;
	private JoystickButton xBoxB;
	private JoystickButton xBoxX;
	private JoystickButton xBoxStart;
	private JoystickButton xBoxBack;
	private JoystickButton xBoxTestX;
	private JoystickButton xBoxTestB;
	private JoystickButton xBoxTestA;
	private JoystickButton xBoxTestY;
	private JoystickButton xBoxTestRightBump;
	private JoystickButton xBoxTestLeftBumper;
	private JoystickButton xBoxTestStart;
	private JoystickButton xBoxTestRightJoyButton;
	private JoystickButton xBoxTestBack;
	private JoystickButton xBoxRightJoyButton;
	private JoystickButton xBoxTestLeftJoyButton;

	public OI() {
		xBoxDriver = Robot.driverJoystick; // This is created in Robot.java.
		xBoxTester = new Joystick(RobotMap.testJoystickPort);

		xBoxY = new JoystickButton(xBoxDriver, RobotMap.xBoxYIndex);
		xBoxY.whenPressed(new MoveSideArm(true));

		xBoxA = new JoystickButton(xBoxDriver, RobotMap.xBoxAIndex);
		xBoxA.whenPressed(new MoveSideArm(false));

		xBoxB = new JoystickButton(xBoxDriver, RobotMap.xBoxBIndex);
		xBoxB.whenPressed(new TurnClockwise());

		xBoxX = new JoystickButton(xBoxDriver, RobotMap.xBoxXIndex);
		xBoxX.whenPressed(new TurnCounterwise());

		xBoxStart = new JoystickButton(xBoxDriver,
				RobotMap.xBoxStartButtonIndex);
		xBoxStart.whenPressed(new StrafeToggle());

		xBoxBack = new JoystickButton(xBoxDriver, RobotMap.xBoxBackButtonIndex);
		xBoxBack.whenPressed(new DriveWithXBox());
		
		

		xBoxRightBump = new JoystickButton(xBoxDriver,
				RobotMap.xBoxRightBumperIndex);
		xBoxRightBump.whenPressed(new TurnClockwise());
		xBoxRightBump.whenReleased(new TurnClockwiseEnd());

		xBoxLeftBump = new JoystickButton(xBoxDriver,
				RobotMap.xBoxLeftBumperIndex);
		xBoxLeftBump.whenPressed(new TurnCounterwise());
		xBoxLeftBump.whenReleased(new TurnCounterwiseEnd());

		xBoxLeftJoyBotton = new JoystickButton(xBoxDriver,
				RobotMap.xBoxLeftJoystickPress);
		
		xBoxRightJoyButton = new JoystickButton(xBoxDriver,  RobotMap.xBoxRightJoystickPress);
		xBoxRightJoyButton.whenPressed(new ShutDownSequence());

		// Testing Stuff
		xBoxTestA = new JoystickButton(xBoxTester, RobotMap.xBoxAIndex);
		// xBoxTestA.whileHeld(new TestDrive(3));
		// xBoxTestA.whenPressed(new TurnEighth());
//		xBoxTestA.whenPressed(new FindContainer(AutoDriveDirection.forward, 2.5));
		xBoxTestA.whenPressed(new ResetArms());

		xBoxTestB = new JoystickButton(xBoxTester, RobotMap.xBoxBIndex);
		//xBoxTestB.whileHeld(new TestDrive(1));
		//xBoxTestB.whenPressed(new DriveAndFindContainer());
		xBoxTestB.whenPressed(new FindContainer(AutoDriveDirection.forward, 1.0));
		
		xBoxTestX = new JoystickButton(xBoxTester, RobotMap.xBoxXIndex);
		// xBoxTestX.whileHeld(new TestDrive(0));
		//xBoxTestX.whenPressed(new DriveAndSlam());
		xBoxTestX.whenPressed(new DriveToStep());
		
		xBoxTestY = new JoystickButton(xBoxTester, RobotMap.xBoxYIndex);
		// xBoxTestY.whileHeld(new TestDrive(2));
		// xBoxTestY.whenPressed(new DropWing());
		xBoxTestY.whenPressed(new LiftArmAndGetContainer());
		
		
		xBoxTestRightBump = new JoystickButton(xBoxTester, RobotMap.xBoxRightBumperIndex);
		xBoxTestRightBump.whileHeld(new TestTurnTable(ttDirection.ClockWise));
		// xBoxTestRightBump.whileHeld(new TestRumble(xBoxTester));

		xBoxTestLeftBumper = new JoystickButton(xBoxTester, RobotMap.xBoxLeftBumperIndex);
		// xBoxTestLeftBumper.whenPressed(new PullOutContainer(
		// AutoDriveDirection.forward));
		xBoxTestLeftBumper.whileHeld(new TestTurnTable(ttDirection.Counter));

		xBoxTestRightJoyButton = new JoystickButton(xBoxTester,  RobotMap.xBoxRightJoystickPress);
		xBoxTestRightJoyButton.whenPressed(new ShutDownSequence());

		xBoxTestLeftJoyButton = new JoystickButton(xBoxTester, RobotMap.xBoxLeftJoystickPress);
		//xBoxTestLeftJoyButton.whenPressed(new LiftArmAndGetContainer());
		
		xBoxTestStart = new JoystickButton(xBoxTester, RobotMap.xBoxStartButtonIndex);
		xBoxTestStart.whileHeld(new MoveArmSlightly(armDirection.Up));

		xBoxTestBack = new JoystickButton(xBoxTester, RobotMap.xBoxBackButtonIndex);
		xBoxTestBack.whileHeld(new MoveArmSlightly(armDirection.Down));

		// xBoxTestAlongWallDrive.whenPressed(new DriveAlongWall(2, 5.0));
	}

	public Joystick getDriverXBox() {
		return xBoxDriver;
	}
}
