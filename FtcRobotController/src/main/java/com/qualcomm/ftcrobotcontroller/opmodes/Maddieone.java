package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by family on 9/17/2015.
 */
public class Maddieone extends OpMode
{
	int y=54*3;
	@Override public void init ()
	{
		telemetry.addData("information", "" + y);
	}
	@Override public void start ()
	{

	}
	@Override public void loop ()
	{

	}
	@Override public void stop ()
	{

	}


}
