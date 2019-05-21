/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             *
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
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.commands.DiffDrive;
import frc.robot.Robot;

public class Drivetrain extends Subsystem 
{
  //SPARK MAX LEFT MOTORS
  private final WPI_TalonSRX LEFT_M_ONE;
  
  //SPARK MAX RIGHT MOTOR
  private final WPI_TalonSRX RIGHT_M_ONE;

  //SPEED CONTROLLER GROUPS
  private final SpeedControllerGroup LEFT_M_GROUP;
  private final SpeedControllerGroup RIGHT_M_GROUP;

  //DIFFERENTIAL DRIVE
  private final DifferentialDrive DIFF_DRIVE;

  //SHIFTING SOLENOIDS
  private final DoubleSolenoid SHIFT_SOL;

  //NAVX MXP GYRO
  private final AHRS GYRO;
  private final double KP_GYRO;

  //GRAYHILL OPTICAL ENCODERS
  public Encoder leftEnc;
  public Encoder rightEnc;

  //DRIVETRAIN CONSTRUCTOR
  public Drivetrain() 
  {
    //SPARK MAX LEFT MOTORS
    this.LEFT_M_ONE = new WPI_TalonSRX(Robot.ROBOTMAP.getLeftMotorPortOne());
    
    //SPARK MAX RIGHT MOTORS
    this.RIGHT_M_ONE = new WPI_TalonSRX(Robot.ROBOTMAP.getRightMotorPortOne());

    //SPEED CONTROLLER GROUPS
    this.LEFT_M_GROUP = new SpeedControllerGroup(this.LEFT_M_ONE);
    this.RIGHT_M_GROUP = new SpeedControllerGroup(this.RIGHT_M_ONE);

    //DIFFERENTIAL DRIVE
    this.DIFF_DRIVE = new DifferentialDrive(this.LEFT_M_GROUP, this.RIGHT_M_GROUP);

    //SHIFTING SOLENOIDS
    this.SHIFT_SOL = new DoubleSolenoid(Robot.ROBOTMAP.getShiftPortOn(), Robot.ROBOTMAP.getShiftPortOff());

    //NAVX MXP GYRO
    this.GYRO = new AHRS(SPI.Port.kMXP);
    this.KP_GYRO = Robot.ROBOTMAP.getKPGyro();

    //GRAYHILL OPTICAL ENCODERS
    this.leftEnc = new Encoder(Robot.ROBOTMAP.getLeftEncPortA(), Robot.ROBOTMAP.getLeftEncPortB(), false, EncodingType.k4X);
    this.rightEnc = new Encoder(Robot.ROBOTMAP.getRightEncPortA(), Robot.ROBOTMAP.getRightEncPortB(), false, EncodingType.k4X);

    //SET ROBOT TO LOW GEAR
    SHIFT_SOL.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DiffDrive());
  }

  //DRIVING METHOD
  public void arcadeDrive(Joystick stick) {
    this.DIFF_DRIVE.arcadeDrive(stick.getY(), -stick.getTwist());
  }

  //SHIFTING METHOD -- if { LOW } else { HIGH }
  public void shiftGear() {
    if(SHIFT_SOL.get() == DoubleSolenoid.Value.kForward) {
      this.SHIFT_SOL.set(DoubleSolenoid.Value.kReverse);
    } else {
      this.SHIFT_SOL.set(DoubleSolenoid.Value.kForward);
    }
  }

  //MOVING STRAIGHT USING THE GYRO METHOD
  public void gyroMoveStraight(double speed)
  {
    this.DIFF_DRIVE.arcadeDrive(speed, -this.GYRO.getAngle() * this.KP_GYRO);
  }

  //MOVING STRAIGHT USING GYRO AND ANGLE VALUE METHOD
  public void gyroMoveStraight(double speed, double angle)
  {
    this.DIFF_DRIVE.arcadeDrive(-speed, -angle * this.KP_GYRO);
  }

  //ROTATE ROBOT
  public void rotate(double speed)
  {
    this.LEFT_M_GROUP.set(speed);
    this.RIGHT_M_GROUP.set(speed);
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

  public double getLeftEncRate() {
    return this.leftEnc.getRate();
  }

  public double getLeftEncDist() {
    return this.leftEnc.getDistance();
  }

  public double getRightEncRate() {
    return this.rightEnc.getRate();
  }

  public double getRightEncDist() {
    return this.rightEnc.getDistance();
  }

  public AHRS getGyro()
  {
    return this.GYRO;
  }

  public double getGyroAngle() {
    return this.GYRO.getAngle(); 
  }

  public double getGyroAxis() {
    return this.GYRO.getBoardYawAxis().board_axis.getValue();
  }

  public void resetGyro() {
    this.GYRO.reset();
    this.GYRO.zeroYaw();
  }
}