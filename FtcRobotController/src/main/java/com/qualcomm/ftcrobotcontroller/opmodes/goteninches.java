package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
/**
 * Created by family on 9/17/2015.
 */
public class goteninches extends OpMode
{ private DcMotorController v_dc_motor_controller_drive;

	private DcMotor DriveLeft;
	final int v_channel_left_drive = 1;
	long inittime ;
	private DcMotor DriveRight;
	final int v_channel_right_drive = 2;
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
		if(System.currentTimeMillis()-inittime > 860)
		{
			DriveLeft.setPower (0);
			DriveRight.setPower (0);
		}

	}
	@Override public void stop ()
	{}


}
