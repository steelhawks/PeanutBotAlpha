/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DashboardWS;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.TrackingWS;
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
  public static final TrackingWS TRACKERWS = new TrackingWS();
  public static final DashboardWS DASHBOARDWS = new DashboardWS();
  public WPI_TalonSRX light = new WPI_TalonSRX(ROBOTMAP.getLightPort());

  @Override
  public void robotInit() 
  {
    //COMMAND_LINKER.configureCommands();
    TRACKERWS.connect();
    DASHBOARDWS.connect();
    light.configContinuousCurrentLimit(1);
    light.enableCurrentLimit(true);

   /* CommandScheduler.getInstance().registerSubsystem(Robot.DRIVETRAIN);
    CommandScheduler.getInstance().registerSubsystem(Robot.ULTRA);
    CommandScheduler.getInstance().registerSubsystem(Robot.VISION);

    Button SHIFT_BUTTON = new JoystickButton(Robot.COMMAND_LINKER.DRIVE_JOYSTICK, Robot.ROBOTMAP.getShiftButton());
    Button ALIGN_BUTTON = new JoystickButton(Robot.COMMAND_LINKER.DRIVE_JOYSTICK, Robot.ROBOTMAP.getAlignButton());

    SHIFT_BUTTON.whenPressed(new ShiftGear());
    ALIGN_BUTTON.whenPressed(new Align());

    CommandScheduler.getInstance().setDefaultCommand(Robot.DRIVETRAIN, new DiffDrive());*/
  }

  @Override
  public void robotPeriodic() 
  {
    VISION.objectPresent(TRACKERWS.getTargetData());
    //light.set(Math.abs(COMMAND_LINKER.DRIVE_JOYSTICK.getRawAxis(3)/0.5));
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() 
  {
    CommandScheduler.getInstance().enable();
  }

  @Override
  public void teleopPeriodic() 
  {
    CommandScheduler.getInstance().run();
    light.set(1.0);
  }

  @Override
  public void testPeriodic() {}
}
