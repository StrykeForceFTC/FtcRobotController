package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by family on 9/17/2015.
 */
public class teleop_tankdrive extends OpMode
{
	private DcMotor DriveLeft;
	private DcMotor DriveRight;

	@Override public void init ()
	{
		DriveRight = hardwareMap.dcMotor.get("");
		DriveLeft = hardwareMap.dcMotor.get("");
		DriveLeft.setDirection(DcMotor.Direction.REVERSE);
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
