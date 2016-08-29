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
public class Sam_Speed_Drive extends low_level_class
{
    private DcMotorController v_dc_motor_controller_drive;

    private DcMotor DriveLeft;
    final int v_channel_right_drive = 1;

    private DcMotor DriveRight;
    final int v_channel_left_drive = 2;
    int initEncoder;

    @Override public void init ()
    {
        v_dc_motor_controller_drive
                = hardwareMap.dcMotorController.get("Drive");

        DriveLeft = hardwareMap.dcMotor.get ("DriveLeft");

        DriveRight = hardwareMap.dcMotor.get ("DriveRight");
        DriveRight.setDirection (DcMotor.Direction.REVERSE);
        v_dc_motor_controller_drive.setMotorChannelMode(1, DcMotorController.RunMode.RUN_USING_ENCODERS);
        v_dc_motor_controller_drive.setMotorChannelMode(2, DcMotorController.RunMode.RUN_USING_ENCODERS);
    }
    @Override public void start ()
    {
        initEncoder = DriveLeft.getCurrentPosition();
        DriveRight.setPower(.50);
        DriveLeft.setPower(.50);

    }

    @Override public void loop ()
    {


        if (Math.abs(DriveLeft.getCurrentPosition()- initEncoder)>drive_calc(100))
        {
            DriveRight.setPower(0);
            DriveLeft.setPower(0);
        }


    }
    @Override public void stop()
    {
        DriveRight.setPower(0);
        DriveLeft.setPower(0);

    }



}
