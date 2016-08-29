package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by Evan Wahmhoff on 8/29/2016.
 */
public class Evan_Controller extends OpMode {
    private DcMotorController v_dc_motor_controller_drive;

    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    private DcMotor Rack1;
    private DcMotor Rack2;
    private DcMotor Angle;
    boolean speedMode = true;
    int rackMode;
    private Servo TedLeft;
    private Servo TedRight;
    private double LeftTedPosition;
    private double RightTedPosition;

    public Evan_Controller() {

    }


    @Override public void init() {

        v_dc_motor_controller_drive = hardwareMap.dcMotorController.get("Drive");
        DriveLeft = hardwareMap.dcMotor.get ("DriveLeft");
        DriveRight = hardwareMap.dcMotor.get ("DriveRight");


    }

    @Override
    public void loop() {
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;
        float driveStraight = gamepad1.right_trigger;
        boolean normSpeed = gamepad1.y;
        boolean halfSpeed = gamepad1.x;

        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        if (right < 0) {
            right = (float) Math.sqrt(Math.abs(right));
            right = -right;
        } else {
            right = (float) Math.sqrt(right);
        }

        if (left < 0) {
            left = (float) Math.sqrt(Math.abs(left));
            left = -left;
        } else {
            left = (float) Math.sqrt(left);
        }
        if (normSpeed == true) {
            speedMode = true;
        }

        if (halfSpeed == true) {
            speedMode = false;
        }
        if (speedMode == false)

        {
            left = left / 2;
            right = right / 2;
        }
        if (driveStraight < .1 && gamepad1.left_trigger < .1) {
            DriveRight.setPower(right);
            DriveLeft.setPower(left);
        }
        if (driveStraight > .1) {
            DriveRight.setPower(driveStraight);
            DriveLeft.setPower(driveStraight);
        }
        if (gamepad1.left_trigger > .1) {
            DriveLeft.setPower(-gamepad1.left_trigger);
            DriveRight.setPower(-gamepad1.left_trigger);
        }
    }

    @Override
    public void stop(){

        DriveLeft.setPower(0);
        DriveRight.setPower(0);
        Angle.setPower(0);
        }
}