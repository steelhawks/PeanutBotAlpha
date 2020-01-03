/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Robot;
import java.util.Set;

public class DiffDrive implements Command 
{
  public DiffDrive() {}

  @Override
  public Set<Subsystem> getRequirements() 
  {
    return null;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() 
  {
    Robot.DRIVETRAIN.arcadeDrive(Robot.COMMAND_LINKER.DRIVE_JOYSTICK);
  }

  @Override
  public boolean isFinished() 
  {
    return false;
  }

  @Override
  public void end(boolean interrupted) {}
}