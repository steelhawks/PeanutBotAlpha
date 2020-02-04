/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FIRST. All Rights Resered.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.Robot;

public class CommandLinker 
{
  /*****
   * Joystick Objects
   *****/
  public final Joystick DRIVE_JOYSTICK = new Joystick(Robot.ROBOTMAP.getJoystickPortOne());

  public CommandLinker() 
  {
    //configureCommands();
  }

  public void configureCommands()
  {
    CommandScheduler.getInstance().registerSubsystem(Robot.DRIVETRAIN);
    CommandScheduler.getInstance().registerSubsystem(Robot.ULTRA);
    CommandScheduler.getInstance().registerSubsystem(Robot.VISION);

    Button SHIFT_BUTTON = new JoystickButton(this.DRIVE_JOYSTICK, Robot.ROBOTMAP.getShiftButton());
    Button ALIGN_BUTTON = new JoystickButton(this.DRIVE_JOYSTICK, Robot.ROBOTMAP.getAlignButton());
    Button BAY_BUTTON = new JoystickButton(this.DRIVE_JOYSTICK, Robot.ROBOTMAP.getBayButton());
    Button BALL_BUTTON = new JoystickButton(this.DRIVE_JOYSTICK, Robot.ROBOTMAP.getBallButton());
    Button PORT_BUTTON = new JoystickButton(this.DRIVE_JOYSTICK, Robot.ROBOTMAP.getPortButton());


    SHIFT_BUTTON.whenPressed(new ShiftGear());
    ALIGN_BUTTON.whenPressed(new Align());
    BAY_BUTTON.whenPressed(new RequestBay());
    BALL_BUTTON.whenPressed(new RequestBall());
    PORT_BUTTON.whenPressed(new RequestPort());


    CommandScheduler.getInstance().setDefaultCommand(Robot.DRIVETRAIN, new DiffDrive());
  }
}
