/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Ultra;

public class Robot extends TimedRobot 
{
  /*****
   * Robot Objects
   *****/
  public static final RobotMap ROBOTMAP = new RobotMap();
  public static final Drivetrain DRIVETRAIN = new Drivetrain();
  public static final Vision VISION = new Vision();
  public static final Ultra ULTRA = new Ultra();
  public static final CommandLinker COMMAND_LINKER = new CommandLinker();

  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {}

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() 
  {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {}
}
