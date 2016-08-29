package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
/**
 * Created by family on 9/27/2015.
 */
public class color_sensor_light extends low_level_class
{
	private DcMotorController v_dc_motor_controller_drive;


	private DcMotor DriveLeft;
	final int v_channel_left_drive = 1;
	double inittime;
	private DcMotor DriveRight;
	final int v_channel_right_drive = 2;
	private ColorSensor Color;
	private int v_state;
	double left_goal;
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
		left_goal = 22/(4*3.141592)*1440;
		turn = (24*3.141592/360*45/(4*3.141592)*1440);
		initEncoder = DriveLeft.getCurrentPosition();
		v_state = 0;
		inittime= System.currentTimeMillis();
	}

	private boolean is_on = false;
	private double start_time = System.currentTimeMillis();

	@Override public void loop ()
	{
		if(is_on) {

			if(System.currentTimeMillis()-start_time >= 5000)
			Color.enableLed(false);

		}
		else {
			is_on = true;
			Color.enableLed(true);
			start_time = System.currentTimeMillis();
		}
		/*double timepassed=System.currentTimeMillis()-inittime;
		switch (v_state)
		{
			case 0:
				Color.enableLed(true);
				v_state++;
			case 1:
				if (timepassed <= 5000)
				{
					v_state++;
				}
			case 2:
				Color.enableLed(false);
				v_state++;
		}
		*/
		/*switch (v_state)
		{
			case 0:

				telemetry.addData("red", "" +  Color.red());
				telemetry.addData("blue", "" + Color.blue());
				telemetry.addData("green", "" +  Color.green());
				telemetry.addData("white", "" + Color.alpha());

				if (Color.alpha()>200) {

					DriveRight.setPower(0.30);
					DriveLeft.setPower(0.30);


                    /*if (DriveLeft.getCurrentPosition() - initEncoder > drive_calc(12)) {
                        DriveLeft.setPower(0);
                        DriveRight.setPower(0);
                        //v_state++;
                        initEncoder = DriveLeft.getCurrentPosition();

                    }*/
				/*}
				else
				{
					DriveLeft.setPower(0);
					DriveRight.setPower(0.25);
				}
				break;

			case 1:
				DriveLeft.setPower(-0.50);
				DriveRight.setPower(0.50);
				if (DriveLeft.getCurrentPosition()-initEncoder<-turn)
				{
					DriveLeft.setPower(0);
					DriveRight.setPower(0);
					left_goal = 101/(4*3.141592)*1440;
					v_state++;
					initEncoder = DriveLeft.getCurrentPosition();
				}
				break;
			case 2:
				DriveRight.setPower(0.50);
				DriveLeft.setPower(0.50);


				if (DriveLeft.getCurrentPosition()-initEncoder>drive_calc(50))
					DriveLeft.setPower(0);
				{
					DriveRight.setPower(0);
					v_state++;
					initEncoder = DriveLeft.getCurrentPosition();

				}
				break;
			case 3:
				DriveRight.setPower(0.50);
				DriveLeft.setPower(-0.50);


				if (DriveLeft.getCurrentPosition()-initEncoder<-drive_calc(8))
				{
					DriveLeft.setPower(0);
					DriveRight.setPower(0);
					v_state++;
					initEncoder = DriveLeft.getCurrentPosition();

				}
				break;
			case 4:
				DriveRight.setPower(0.50);
				DriveLeft.setPower(0.50);


				if (DriveLeft.getCurrentPosition()-initEncoder>drive_calc(15))
				{
					DriveLeft.setPower(0);
					DriveRight.setPower(0);
					v_state++;
					initEncoder = DriveLeft.getCurrentPosition();

				}
		}
	}

	@Override public void stop ()
	{

	*/
	}
}
