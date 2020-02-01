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

import java.util.HashSet;
import java.util.Set;

public class Align implements Command
{
    public Align() {}

    @Override
    public Set<Subsystem> getRequirements() 
    {
        Set<Subsystem> list = new HashSet<Subsystem>();
        list.add(Robot.DRIVETRAIN);
        list.add(Robot.VISION);
        list.add(Robot.ULTRA);
        return list;
    }

    @Override
    public void initialize()
    {
        Robot.DRIVETRAIN.resetGyro();
        Robot.VISION.reset();
        Robot.VISION.setAngle(Robot.VISION.getNTAngle());
        Robot.VISION.setXPosLeftLimit(157.5);
        Robot.VISION.setXPosRightLimit(162.5);
        Robot.ULTRA.enable();
    }

    @Override
    public void execute()
    {
        Robot.VISION.align();
    }

    @Override
    public boolean isFinished()
    {
        return Robot.VISION.isAligned();
    }

    @Override
    public void end(boolean interrupted)
    {
        Robot.DRIVETRAIN.stop();
        Robot.ULTRA.disable();
    }
}