package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by family on 9/27/2015.
 */
public class colorsensor extends low_level_class
{
	private DcMotorController v_dc_motor_controller_drive;


	private DcMotor DriveLeft;
	private DcMotor DriveRight;
	private ColorSensor Color;
	int initEncoder;
	double turn;
	@Override public void init ()
	{
		v_dc_motor_controller_drive
				= hardwareMap.dcMotorController.get("Drive");

		DriveLeft = hardwareMap.dcMotor.get ("DriveLeft");

		DriveRight = hardwareMap.dcMotor.get ("DriveRight");
		DriveLeft.setDirection (DcMotor.Direction.FORWARD);
		DriveRight.setDirection (DcMotor.Direction.REVERSE);
		Color = hardwareMap.colorSensor.get ("Color");
}

	@Override public void start ()
	{

	}
	@Override public void loop ()
	{
		telemetry.addData("red", "" +  Color.red());
		telemetry.addData("blue", "" + Color.blue());
		telemetry.addData("green", "" + Color.green());
		telemetry.addData("white", "" + Color.alpha());
		DriveRight.setPower(-.1);

		if(Color.alpha()<3)
		{
			DriveLeft.setPower(.1);
		}
		else
		{
			DriveLeft.setPower(-.1);
		}
	}

	@Override public void stop ()
	{

	}
}