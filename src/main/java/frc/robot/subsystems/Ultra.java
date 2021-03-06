/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.Robot;

public class Ultra extends SubsystemBase
{
    private final Ultrasonic ULTRA = new Ultrasonic(Robot.ROBOTMAP.getUltraPing(), Robot.ROBOTMAP.getUltraEcho());

    public void enable()
    {
        this.ULTRA.setEnabled(true);
        this.ULTRA.setAutomaticMode(true);
    }

    public void disable()
    {
        this.ULTRA.setEnabled(false);
    }

    public double getRange()
    {
        return this.ULTRA.getRangeInches();
    }

    public double getRangeMM()
    {
        return this.ULTRA.getRangeMM();
    }

    public boolean isCloseShift()
    {
        if(this.ULTRA.getRangeInches() <= 70)
        {
            return true;
        }
        return false;
    }

    public boolean isClose()
    {
        if(this.ULTRA.getRangeInches() <= 40)
        {
            return true;
        }
        return false;
    }

    public boolean isRangeValid()
    {
        return this.ULTRA.isRangeValid();
    }
}