/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import org.json.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.subsystems.TrackingWS;

public class Vision extends SubsystemBase
{
    // private NetworkTableInstance networkTables = NetworkTableInstance.getDefault(); // :)   
    // private NetworkTable networkTablesData = networkTables.getTable("SmartDashboard");

    private boolean initAlign;
    private boolean alignAngle;
    private double angle;
    private double xPosLeftLimit;
    private double xPosRightLimit;
    private JSONObject closestTarget;

    //Aligns the robot
    public void alignCurve()
    {
        double tuneValue = 1000;

        if (Robot.COMMAND_LINKER.DRIVE_JOYSTICK.getRawButtonPressed(2))
        {
            end();
        }
        else if (getDistance() > 15) //!Robot.IR.isClose()
        {
            //Robot.DRIVETRAIN.gyroMoveStraight(Robot.ULTRA.getRange() / 40);
            if (Math.abs(Robot.DRIVETRAIN.getGyro().getAngle()) < getNTAngle() && getXPos() < getXPosLeftLimit())
            {
                // Robot.DRIVETRAIN.gyroMoveStraight(-0.4, -(int)(getNTAngle())); //Robot.ULTRA.getRange() / 500
                Robot.DRIVETRAIN.LEFT_M_GROUP.set(-(getDistance()) / tuneValue);
                Robot.DRIVETRAIN.RIGHT_M_GROUP.set((getDistance() * (getXPosDiff(getXPosLeftLimit()) * 0.45 )) / tuneValue);
            }
            else if(Math.abs(Robot.DRIVETRAIN.getGyro().getAngle()) < getNTAngle() && getXPos() > getXPosRightLimit()) 
            {
                // Robot.DRIVETRAIN.gyroMoveStraight(-0.4, (int)(getNTAngle())); //Robot.ULTRA.getRange() / 500
                Robot.DRIVETRAIN.LEFT_M_GROUP.set(-(getDistance() * (getXPosDiff(getXPosRightLimit()) * 0.45 )) / tuneValue);
                Robot.DRIVETRAIN.RIGHT_M_GROUP.set((getDistance()) / tuneValue);
            }
            else 
            {
                // Robot.DRIVETRAIN.gyroMoveStraight(-0.4); //Robot.ULTRA.getRange() / 500
                Robot.DRIVETRAIN.LEFT_M_GROUP.set(-(getDistance()) / tuneValue);
                Robot.DRIVETRAIN.RIGHT_M_GROUP.set((getDistance()) / tuneValue);
            }
        }
        else
        {
            Robot.DRIVETRAIN.LEFT_M_GROUP.set(0);
            Robot.DRIVETRAIN.RIGHT_M_GROUP.set(0);
            alignAngle = true;
        } 
    }

    public void align()
    {
        getXPos();
        getNTAngle();
        getDistance();
        if (Robot.COMMAND_LINKER.DRIVE_JOYSTICK.getRawButtonPressed(2))
        {
            end();
        }
        else if (!this.initAlign)
        {
            System.out.println("Aligning");
            if (Math.abs(Robot.DRIVETRAIN.getGyro().getAngle()) < (getAngle() - 0.1) && getXPos() < getXPosLeftLimit())
            {
                Robot.DRIVETRAIN.rotate(0.225); 
            }
            else if(Math.abs(Robot.DRIVETRAIN.getGyro().getAngle()) < (getAngle() - 0.1) && getXPos() > getXPosRightLimit()) 
            {
                Robot.DRIVETRAIN.rotate(-0.225);
            }
            else 
            {
                Robot.DRIVETRAIN.resetGyro();
                Robot.DRIVETRAIN.stop();
                System.out.println("Aligned!");
                this.initAlign = true;
            }
        }
        else
        {
            if (getDistance() > 15) //!Robot.ULTRA.isClose()
            {
                // Robot.DRIVETRAIN.gyroMoveStraight(-0.6); //Robot.DRIVETRAIN.decimalSpeed(Robot.ULTRA.getRange())
                alignCurve();
            }
            else
            {
                Robot.DRIVETRAIN.stop();
                System.out.println("Distanced!");
                alignAngle = true;
            }
        } 
    }
// :)

    public boolean containsData(String data){
        if(data.equals("{\"target\":[]}")){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean objectPresent(String trackingData)
    {
        try 
        {   
            if(containsData(trackingData)){
                JSONObject targetData = new JSONObject(trackingData);
                String targetDataValues = (targetData.getString("targets"));
                JSONArray targetArray = new JSONArray(targetDataValues);
                closestTarget = (JSONObject)targetArray.get(0);
                return true;
            }
            else{
                return false;
            }
            
        }
        catch (Exception e)
        {
            // System.out.println("ERROR: Value not found in JSON Array!");
            return false;
        }
    }

    public String getShape()
    {
        try
        {
            return closestTarget.getString("shape");
        }
        catch (Exception e)
        {
            return "object not found";
        }
    }

    //Returns the x coordinate value of the center of the hatch
    public double getXPos()
    {
        try
        {
            System.out.println(getShape() + " Center X: " + closestTarget.getDouble("xpos"));
            return closestTarget.getInt("xpos");
        }
        catch(Exception e)
        {
            return 0;
        }
        
    }

    //Returns the y coordinate value of the center of the hatch
    public double getYPos()
    {
        try
        {
            System.out.println(getShape() + " Center Y: " + closestTarget.getDouble("ypos"));
            return closestTarget.getInt("ypos");
        }
        catch(Exception e)
        {
            return 0;
        }
       
    }

    //Returns the apparent calculated distance between the robot and the hatch
    public double getDistance()
    {
        try
        {
            System.out.println(getShape() + " Distance: " + closestTarget.getDouble("dist"));
            return closestTarget.getInt("dist");
        }
        catch(Exception e)
        {
            return 0;
        }
        
    }

    //Returns the angle that the robot's line of vision and the center of the hatch creates || :/
    public double getNTAngle()
    {
        try
        {
            System.out.println(getShape() + " Angle to Line of Vision: " + closestTarget.getDouble("angle"));
            return closestTarget.getDouble("angle");
        }
        catch(Exception e)
        {
            return 0;
        }
    }

    //Sets the angle that was returned into an angle variable that can be used
    public void setAngle(double angle)
    {
       this.angle = angle;
    }

    //Returns the angle that the gyro needs in order to turn
    public double getAngle()
    {
        //System.out.println(this.angle);
        return this.angle;
    }

    //Sets the x coordinate of the left border of the hatch
    public void setXPosLeftLimit(double xPosLeftLimit)
    {
       this.xPosLeftLimit = xPosLeftLimit;
    }

    //Sets the x coordinate of the right border of the hatch
    public void setXPosRightLimit(double xPosRightLimit)
    {
       this.xPosRightLimit = xPosRightLimit;
    }

    //Returns the x coordinate of the left border of the hatch
    public double getXPosLeftLimit()
    {
        return this.xPosLeftLimit;
    }
   
    //Returns the x coordinate of the right border of the hatch
    public double getXPosRightLimit()
    {
       return this.xPosRightLimit;
    }

    //Returns the difference between the right || left border and center
    public double getXPosDiff(double xPos)
    {
        if (xPos > 160)
        {
            return xPos - 160;
        }
        return Math.abs(160 - xPos);
    }

    //Returns whether alignment and distancing to the hatch is complete
    public boolean isAligned()
    {
        return this.alignAngle;
    }

    public void end()
    {
        Robot.DRIVETRAIN.stop();
        System.out.println("Quit...");
        this.alignAngle = true;
    }

    //Sets the gyro back to default zero values
    public void reset()
    {
        alignAngle = false;
        initAlign = false;
    }

    // public void test()
    // {
    //     getNTAngle();
    //     getXPos();
    //     getYPos();
    //     getDistance();
    // }
}