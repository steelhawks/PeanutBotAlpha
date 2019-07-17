/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Sensors;

public class Robot extends TimedRobot 
{
  /*****
   * Robot Objects
   *****/
  public static RobotMap ROBOTMAP;
  public static OI OI;
  public static PathFollower PATHFOLLOWER;
  public static Drivetrain DRIVETRAIN;
  public static Vision VISION;
  public static Sensors SENSORS;

  @Override
  public void robotInit() 
  {
    ROBOTMAP = new RobotMap();
    OI = new OI();
    PATHFOLLOWER = new PathFollower();
    DRIVETRAIN = new Drivetrain();
    VISION = new Vision();
    SENSORS = new Sensors();
    Robot.PATHFOLLOWER.init();
  }

  @Override
  public void robotPeriodic() 
  {
    SmartDashboard.putString("Match Time", returnTime());
    SmartDashboard.putBoolean("Valid Path", Robot.PATHFOLLOWER.isValid());
  }

  @Override
  public void disabledInit() 
  {
    Robot.DRIVETRAIN.stop();
    Robot.PATHFOLLOWER.stop();
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() 
  {
    Robot.PATHFOLLOWER.followPath();
  }

  @Override
  public void teleopInit()
  {
    Robot.PATHFOLLOWER.stop();
  }

  @Override
  public void teleopPeriodic() 
  {
    Scheduler.getInstance().run();
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  public String returnTime() {
    boolean isAuton = DriverStation.getInstance().isAutonomous();
    int time = (int) DriverStation.getInstance().getMatchTime();

    if (time == -1) {
      time = 0;
    }

    String minutes = Integer.toString(time / 60);
    String seconds = Integer.toString(time % 60);

    if (time % 60 < 10) 
    {
      seconds = "0" + seconds;
    }

    if (isAuton) 
    {
      return "Sandstorm: " + minutes + ":" + seconds;
    }
    else 
    {
      return "Teleop: " + minutes + ":" + seconds;
    }
  }
}
