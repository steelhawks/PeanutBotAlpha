/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class RobotMap 
{
  /*****
   * Drivetrain variables
   *****/

  //Right Motor Ports
  public final int m_rightOne = 0;

  //Left Motor Ports
  public final int m_leftOne = 1;

  //Greyhill Encoder Ports
  public final int enc_leftA = 0;
  public final int enc_leftB = 1; 
  public final int enc_rightA = 2;
  public final int enc_rightB = 3;

  //Gyro
  public final double g_constant = 0.008;

  /*****
   * Ultrasonic variables
   *****/

  public final int s_ultraPing = 8;
  public final int s_ultraEcho = 9;

  /*****
   * Driverstation variables
   *****/

  //Input Ports
  public final int js_one = 0;

  //Button ports
  public final int btn_shift = 1;
  public final int btn_align = 3;

  /*****
   * Pathfollower variables
   *****/

  public final int ticksPerRev = 1024;
  public final double wheelDiameter = 6.0;
  public final double maxVelocity = 15.9;
  public final String pathName = "PathOne";
}
