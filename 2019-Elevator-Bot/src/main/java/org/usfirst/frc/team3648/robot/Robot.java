/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3648.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team3648.robot.Buttons;

public class Robot extends TimedRobot {
	//Define the controller and port (0)
	private Joystick driveController = new Joystick(0);
	
	//Define the spark (motor controller) for each wheel. The number in parentheses is the port it is in on the roboRio
	private Spark frontLeft = new Spark(0);
	private Spark backLeft = new Spark(1);
	private Spark frontRight = new Spark(3);
	private Spark backRight = new Spark(2);

	private Spark elevatorSpool = new Spark(4);

	private float elevatorSpeed = 1f;
	
	//To do tank drive we need to group the wheels on each side into a group
	private SpeedControllerGroup leftSide = new SpeedControllerGroup(frontLeft, backLeft);
	private SpeedControllerGroup rightSide = new SpeedControllerGroup(frontRight, backRight);
	
	//The drive chassis object. We will call the move commands for the wheels from this
	private DifferentialDrive drive = new DifferentialDrive(leftSide, rightSide);
	
	//Speed modifier
	private float motorMod = 1;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
	}

	@Override
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		//Drive the chassis at a speed decided by the joystick position * the speed modifier. 1 is full speed forward, -1 is full speed backward
		drive.tankDrive(driveController.getRawAxis(5)*motorMod, driveController.getRawAxis(1)*motorMod);
		
		//Change the speed modifier based on the trigger being held. Lets us have a slow, normal, and fast mode
		if(driveController.getRawAxis(Buttons.LEFT_TRIGGER)>0.5){
			motorMod = 0.7f;
		}else if(driveController.getRawAxis(Buttons.RIGHT_TRIGGER)>0.5){
			motorMod = 1f;
		}else{
			motorMod = 0.85f;
		}

		//Don't do this
		elevatorSpool.set(
			(driveController.getRawButton(Buttons.RIGHT_SHOULDER) ? elevatorSpeed : 0 ) +
			(driveController.getRawButton(Buttons.LEFT_SHOULDER) ? -elevatorSpeed : 0 )
		);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
