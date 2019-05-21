package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.Robot;

public class Ultra extends Subsystem
{
    private final Ultrasonic ULTRA = new Ultrasonic(Robot.ROBOTMAP.getUltraPing(), Robot.ROBOTMAP.getUltraEcho());

    @Override
    public void initDefaultCommand() {}

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