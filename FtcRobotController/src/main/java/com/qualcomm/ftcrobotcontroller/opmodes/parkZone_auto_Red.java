package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.Range;
/**
 * Created by family on 9/17/2015.
 */
public class parkZone_auto_Red extends SEQ_Mount
{
    private DcMotorController v_dc_motor_controller_drive;
    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    private int ReturnState;
    private Servo TedLeft;
    private Servo TedRight;
    private DcMotor Angle;
    private double StartTime;

    @Override public void init ()
    {
        v_dc_motor_controller_drive
                = hardwareMap.dcMotorController.get("Drive");

        DriveLeft = hardwareMap.dcMotor.get ("DriveLeft");

        DriveRight = hardwareMap.dcMotor.get ("DriveRight");
        DriveRight.setDirection (DcMotor.Direction.REVERSE);
        TedLeft=hardwareMap.servo.get("TedLeft");
        TedRight=hardwareMap.servo.get("TedRight");
        TedLeft.setPosition(.5);
        TedRight.setPosition(.44);
        Angle=hardwareMap.dcMotor.get("Angle");
    }
    @Override public void start ()
    {
        StartTime = System.currentTimeMillis();

    }

    @Override public void loop ()
    {
        if ((System.currentTimeMillis()- StartTime) > 1000) {  //delay before starting
            //-1 = left; 1 = right
            //nearMount (direction, goOne, turnTwo, goThree, turnFour, goFive)
            ReturnState = nearMount(-1, 85, 0, 0, 0, 0);
            if (ReturnState < 0) {
                telemetry.addData("ERROR timeout. last mode:", "" + ReturnState);
            }
        }
    }
    @Override public void stop()
    {
        DriveLeft.setPower(0);
        DriveRight.setPower(0);
        Angle.setPower(0);

    }
}
