package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by family on 9/17/2015.
 */
public class Red_diagonalBeacon extends SEQ_Beacon
{
    private DcMotorController v_dc_motor_controller_drive;
    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    private int ReturnState;
    private Servo TedLeft;
    private Servo TedRight;
    private DcMotor Angle;
    private double StartTime;
    private ColorSensor Color;

    @Override public void init ()
    {
        v_dc_motor_controller_drive
                = hardwareMap.dcMotorController.get("Drive");

        DriveLeft = hardwareMap.dcMotor.get ("DriveLeft");

        DriveRight = hardwareMap.dcMotor.get ("DriveRight");
        DriveRight.setDirection (DcMotor.Direction.REVERSE);
        TedLeft=hardwareMap.servo.get("TedLeft");
        TedRight=hardwareMap.servo.get("TedRight");
        TedLeft.setPosition(.45);
        TedRight.setPosition(.45);
        Angle=hardwareMap.dcMotor.get("Angle");
        Color = hardwareMap.colorSensor.get ("Color");
    }
    @Override public void start ()
    {
        StartTime = System.currentTimeMillis();
        Color.enableLed(true);

    }

    @Override public void loop ()
    {
        //if ((System.currentTimeMillis()- StartTime) > 1000) {  //delay before starting
            //-1 = left; 1 = right
            //nearMount (direction, MoveOne, SpinTwo, TimeThree, PowerFour)
            ReturnState = Beacon(-1, 72, 0, 0, 0.50,19000);
            if (ReturnState < 0) {
                telemetry.addData("ERROR timeout. last mode:", "" + ReturnState);
           // }
        }
    }
    @Override public void stop()
    {
        DriveLeft.setPower(0);
        DriveRight.setPower(0);
        Angle.setPower(0);

    }
}
