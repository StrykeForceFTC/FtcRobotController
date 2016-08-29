package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by family on 9/27/2015.
 */
public class SEQ_Beacon extends low_level_class {


	private DcMotor DriveLeft;
	private DcMotor Angle;
	private DcMotor DriveRight;
	private DcMotor Rack1;
	private DcMotor Rack2;
	private double timeout=0;
	private double initialtime;
	private int v_state = 0;
	int LeftInitEncoder;
	int RightInitEncoder;
	int LeftOldEncoder;
	int LeftNewEncoder;
	int RememberLastState;
	private ColorSensor Color;
	boolean found;

	int Beacon(int Direction, double MoveOne, double SpinTwo, int MoveThree, double PowerFour, int endtime) {
		DriveLeft = hardwareMap.dcMotor.get("DriveLeft");
		Angle = hardwareMap.dcMotor.get("Angle");
		DriveRight = hardwareMap.dcMotor.get("DriveRight");
		Rack1 = hardwareMap.dcMotor.get ("Rack1");
		Rack2 = hardwareMap.dcMotor.get ("Rack2");
		Color = hardwareMap.colorSensor.get ("Color");


		switch (v_state) {

			case -1:  //this is the error case.  this should never be reached
				telemetry.addData("StrykeForce Error","timeout or other");
				DriveLeft.setPower(0);
				DriveRight.setPower(0);
				Angle.setPower(0);

				break;
			case 0:  //initialize running
				initialtime = System.currentTimeMillis();  //reset timer
				timeout = 15000;                                //set timeout for case 1
				v_state++;
				LeftInitEncoder = DriveLeft.getCurrentPosition();   //reset encoder to current position
				RightInitEncoder = DriveRight.getCurrentPosition();  //not using this yet
				LeftOldEncoder = LeftInitEncoder;                //initialize "no movement" detector
				break;

			case 1:  //move forward away from starting position
				DriveLeft.setPower(1);
				DriveRight.setPower(1);
				LeftNewEncoder = DriveLeft.getCurrentPosition();

				if (Math.abs(LeftNewEncoder - LeftInitEncoder) > drive_calc(MoveOne)) {
					DriveLeft.setPower(0);
					DriveRight.setPower(0);
					v_state++;
					initialtime = System.currentTimeMillis();
					timeout = 7000;
					LeftInitEncoder = LeftNewEncoder;
					//    telemetry.addData("left Enc", "" + Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder));
					//    telemetry.addData("right Enc", "" + Math.abs(DriveRight.getCurrentPosition() - RightInitEncoder));
				}
				//if (Math.abs(LeftNewEncoder - LeftOldEncoder) < 1){   //are we stuck?
				//    RememberLastState = v_state;
				//    v_state = 20;
				//}
				LeftOldEncoder = LeftNewEncoder;   //reset "no movement" detector

				if (System.currentTimeMillis()-initialtime > timeout){
					v_state = -1;
				}
				break;
			case 2:
				DriveLeft.setPower(Direction * .5);
				DriveRight.setPower(-Direction * .5);
				//telemetry.addData("turn", "" + turn_calc(45));
				//telemetry.addData("clicks", ""+Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder));
				if (Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder) > turn_calc(SpinTwo)) {
					DriveLeft.setPower(0);
					DriveRight.setPower(0);
					v_state++;
					initialtime = System.currentTimeMillis();
					timeout = 7000;
					LeftInitEncoder = DriveLeft.getCurrentPosition();
				}
				if (System.currentTimeMillis()-initialtime > timeout){
					v_state = -1;
				}
				break;
			case 3:
				DriveLeft.setPower(1);
				DriveRight.setPower(1);
		//		telemetry.addData("distance", "" + drive_calc(MoveThree));
		//		telemetry.addData("clicks", ""+Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder));
				if (Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder) > drive_calc(MoveThree)) {
					DriveLeft.setPower(0);
					DriveRight.setPower(0);
					v_state++;
					initialtime = System.currentTimeMillis();
					timeout = 5000;
					LeftInitEncoder = DriveLeft.getCurrentPosition();
				}
				break;
			case 4:
				//follow white line
				//telemetry.addData("red", "" +  Color.red());
				//telemetry.addData("blue", "" + Color.blue());
				//telemetry.addData("green", "" + Color.green());
				telemetry.addData("white", "" + Color.alpha());
				if(Direction == -1) {
					if (Color.alpha() >= 1) {
						DriveLeft.setPower(PowerFour);
						DriveRight.setPower(0);
						found = true;
						//		telemetry.addData("world"," ");
					}
					if (Color.alpha() < 1) {
						DriveLeft.setPower(0);
						DriveRight.setPower(PowerFour);
						//		telemetry.addData("hello"," ");

					}
				}
				if(Direction == 1)
				{
					if (Color.alpha() >= 1)
					{
						DriveLeft.setPower(0);
						DriveRight.setPower(PowerFour);
						found = true;
					}
					if (Color.alpha() < 1)
					{
						DriveLeft.setPower(PowerFour);
						DriveRight.setPower(0);
						//		telemetry.addData("hello"," ");
					}
				}

				if (System.currentTimeMillis()-initialtime > endtime)
				{
					if (found == true)
					{
						v_state++;
						DriveLeft.setPower(0);
						DriveRight.setPower(0);
						initialtime = System.currentTimeMillis();
					}
					if (found == false)
					{
						v_state=-1;
					}
				}
				break;
			case 5:

				Angle.setPower(1);
				if(System.currentTimeMillis()-initialtime>500)
				{
					Angle.setPower(0);
					v_state++;
					initialtime = System.currentTimeMillis();
				}
				break;
			case 6:
				Rack1.setPower(-1);
				Rack2.setPower(1);
				telemetry.addData("time", "" + (System.currentTimeMillis()-initialtime));
				if(System.currentTimeMillis()-initialtime>1000)
				{
					Rack1.setPower(0);
					Rack2.setPower(0);
					v_state++;
					initialtime = System.currentTimeMillis();
				}
				break;
			case 7:
				Rack2.setPower(1);

				if(System.currentTimeMillis()-initialtime>1000)
				{
					Rack2.setPower(0);
				}
				break;
		}

		return v_state;
	}

}
