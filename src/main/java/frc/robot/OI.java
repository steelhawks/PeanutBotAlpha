/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
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
  public final Joystick js_drive = new Joystick(Robot.ROBOTMAP.js_one);

  public OI()
  {
    Button shift = new JoystickButton(this.js_drive, Robot.ROBOTMAP.btn_shift);
    Button align = new JoystickButton(this.js_drive, Robot.ROBOTMAP.btn_align);

    shift.whenPressed(new ShiftGear());
    align.whenPressed(new Align());

    shift.close();
    align.close();
  }
}
