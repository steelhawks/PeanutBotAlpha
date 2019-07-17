/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.commands.DiffDrive;
import frc.robot.Robot;

public class Drivetrain extends Subsystem 
{
  //SPARK MAX LEFT MOTORS
  private final WPI_TalonSRX m_leftOne;
  
  //SPARK MAX RIGHT MOTOR
  private final WPI_TalonSRX m_rightOne;

  //SPEED CONTROLLER GROUPS
  public final SpeedControllerGroup m_leftGroup;
  public final SpeedControllerGroup m_rightGroup;

  //DIFFERENTIAL DRIVE
  private final DifferentialDrive diffDrive;

  //VARIABLE RPM ELECTRO-SHIFT
  private int shiftStatus;
  private double rPMCoefficient;

  //NAVX MXP GYRO
  public final AHRS gyro;
  private final double g_constant;

  //GRAYHILL OPTICAL ENCODERS
  public Encoder enc_left;
  public Encoder enc_right;

  //DRIVETRAIN CONSTRUCTOR
  public Drivetrain() 
  {
    //SPARK MAX LEFT MOTORS
    this.m_leftOne = new WPI_TalonSRX(Robot.ROBOTMAP.m_leftOne);
    
    //SPARK MAX RIGHT MOTORS
    this.m_rightOne = new WPI_TalonSRX(Robot.ROBOTMAP.m_rightOne);

    //SPEED CONTROLLER GROUPS
    this.m_leftGroup = new SpeedControllerGroup(this.m_leftOne);
    this.m_rightGroup = new SpeedControllerGroup(this.m_rightOne);

    //DIFFERENTIAL DRIVE
    this.diffDrive = new DifferentialDrive(this.m_leftGroup, this.m_rightGroup);

    //NAVX MXP GYRO
    this.gyro = new AHRS(SPI.Port.kMXP);
    this.g_constant = Robot.ROBOTMAP.g_constant;

    //VARIABLE RPM ELECTRO-SHIFT
    this.shiftStatus = 1;
    this.rPMCoefficient = 1.75;

    //GRAYHILL OPTICAL ENCODERS
    this.enc_left = new Encoder(Robot.ROBOTMAP.enc_leftA, Robot.ROBOTMAP.enc_leftB, false, EncodingType.k4X);
    this.enc_right = new Encoder(Robot.ROBOTMAP.enc_rightA, Robot.ROBOTMAP.enc_rightB, false, EncodingType.k4X);
  }

  @Override
  public void initDefaultCommand() 
  {
    setDefaultCommand(new DiffDrive());
  }

  //DRIVING METHOD
  public void arcadeDrive() 
  {
    this.diffDrive.arcadeDrive(this.rPMCoefficient * Robot.OI.js_drive.getY(), -Robot.OI.js_drive.getTwist());
  }

  //SHIFTING METHOD
  public void shiftGear() 
  {
    if (this.shiftStatus == 1) 
    {
      this.shiftStatus = 2;
      this.rPMCoefficient = (2.0/3);
    } 
    else if (this.shiftStatus == 2)
    {
      this.shiftStatus = 3;
      this.rPMCoefficient = 1;
    }
    else if (this.shiftStatus == 3)
    {
      this.shiftStatus = 1;
      this.rPMCoefficient = 0.5;
    }
  }

  //MOVING STRAIGHT USING THE GYRO METHOD
  public void gyroMoveStraight(double speed)
  {
    this.diffDrive.arcadeDrive(speed, -this.gyro.getAngle() * this.g_constant);
  }

  //MOVING STRAIGHT USING GYRO AND ANGLE VALUE METHOD
  public void gyroMoveStraight(double speed, double angle)
  {
    this.diffDrive.arcadeDrive(-speed, -angle * this.g_constant);
  }

  //ROTATE ROBOT
  public void rotate(double speed)
  {
    this.m_leftGroup.set(speed);
    this.m_rightGroup.set(speed);
  }

  //STOP ROBOT
  public void stop()
  {
    rotate(0);
  }

  //CONVERT AN INT SPEED INTO A DECIMAL SPEED
  public double decimalSpeed(double speed)
  {
    return ((int)(((speed + 350) / 700.0) * 100) / 100.0);
  }

  public void resetGyro() 
  {
    this.gyro.reset();
    this.gyro.zeroYaw();
  }
}
