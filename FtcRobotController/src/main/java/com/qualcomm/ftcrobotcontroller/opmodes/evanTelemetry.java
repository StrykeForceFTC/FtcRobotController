package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by family on 9/17/2015.
 */
public class evanTelemetry extends OpMode {
	private DcMotorController v_dc_motor_controller_drive;

	private DcMotor DriveLeft;
	final int v_channel_left_drive = 1;
	long inittime;
	private DcMotor DriveRight;
	final int v_channel_right_drive = 2;
	int initEncoder;
	int RinitEncoder;
	@Override public void init ()
	 {
		v_dc_motor_controller_drive
				= hardwareMap.dcMotorController.get("Drive");

		DriveLeft = hardwareMap.dcMotor.get ("DriveLeft");

		DriveRight = hardwareMap.dcMotor.get ("DriveRight");
		DriveRight.setDirection (DcMotor.Direction.REVERSE);
	}

	@Override public void start ()
	{
		inittime = System.currentTimeMillis();
		DriveLeft.setPower(.50);
		DriveRight.setPower(.50);
	}
	@Override public void loop ()
	{
		if(System.currentTimeMillis()-inittime > 2000)
		{
			DriveLeft.setPower (0);
			DriveRight.setPower (0);
		}

		initEncoder = DriveLeft.getCurrentPosition();
		RinitEncoder = DriveRight.getCurrentPosition();
		telemetry.addData("postion", "" +  DriveLeft.getCurrentPosition());
	}
	@Override public void stop ()
	{}


}
