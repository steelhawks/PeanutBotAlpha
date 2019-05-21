/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.ShiftGear;
import frc.robot.commands.Align;

public class OI 
{
  /*****
   * Joystick Objects
   *****/
  private final Joystick DRIVE_JOYSTICK = new Joystick(Robot.ROBOTMAP.getJoystickPortOne());

  public OI()
  {
    Button SHIFT_BUTTON = new JoystickButton(getDriveJoystick(), Robot.ROBOTMAP.getShiftButton());
    Button ALIGN_BUTTON = new JoystickButton(getDriveJoystick(), Robot.ROBOTMAP.getAlignButton());

    SHIFT_BUTTON.whenPressed(new ShiftGear());
    ALIGN_BUTTON.whenPressed(new Align());

    SHIFT_BUTTON.close();
    ALIGN_BUTTON.close();
  }

  /*****
   * Getter methods
   *****/
  public Joystick getDriveJoystick()
  {
    return this.DRIVE_JOYSTICK;
  }
}
