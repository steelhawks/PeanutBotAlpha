/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Align;
import frc.robot.commands.DiffDrive;
import frc.robot.commands.ShiftGear;

public class CommandLinker 
{
  /*****
   * Joystick Objects
   *****/
  public final Joystick DRIVE_JOYSTICK = new Joystick(Robot.ROBOTMAP.getJoystickPortOne());

  public CommandLinker()
  {
    Button SHIFT_BUTTON = new JoystickButton(this.DRIVE_JOYSTICK, Robot.ROBOTMAP.getShiftButton());
    Button ALIGN_BUTTON = new JoystickButton(this.DRIVE_JOYSTICK, Robot.ROBOTMAP.getAlignButton());

    SHIFT_BUTTON.whenPressed(new ShiftGear());
    ALIGN_BUTTON.whenPressed(new Align());

    CommandScheduler.getInstance().setDefaultCommand(Robot.DRIVETRAIN, new DiffDrive());
  }
}
