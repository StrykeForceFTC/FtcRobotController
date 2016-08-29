package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
/**
 * Created by family on 9/27/2015.
 */
public class blueauto extends low_level_class
{
  private DcMotorController v_dc_motor_controller_drive;

  private DcMotor DriveLeft;
  final int v_channel_left_drive = 1;

  private DcMotor DriveRight;
  final int v_channel_right_drive = 2;
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
  }

  @Override public void start ()
  {
    left_goal = 22/(4*3.141592)*1440;
    turn = 0; /*(24*3.141592/360*45/(4*3.141592)*1440);*/
    initEncoder = DriveLeft.getCurrentPosition();
    v_state = 0;
  }

  @Override public void loop ()
  {

    switch (v_state)
    {
      //Blue, going to mountain
      case 0:
        DriveRight.setPower(1);
        DriveLeft.setPower(1);
        //telemetry.addData("LeftEncoder", "" + DriveLeft.getCurrentPosition());
        //telemetry.addData("RightEncoder" , ""+ DriveRight.getCurrentPosition());


        if (DriveLeft.getCurrentPosition()-initEncoder>drive_calc(20))
        {
          DriveLeft.setPower(0);
          DriveRight.setPower(0);
          v_state++;
          initEncoder = DriveLeft.getCurrentPosition();

        }
        break;
      case 1:
        DriveRight.setPower(-1);
        DriveLeft.setPower(1);


        if (Math.abs(DriveLeft.getCurrentPosition() - initEncoder)>drive_calc(10))
        {
          DriveLeft.setPower(0);
          DriveRight.setPower(0);
          v_state++;
          initEncoder = DriveLeft.getCurrentPosition();

        }
        break;
      case 2:
        DriveRight.setPower(1);
        DriveLeft.setPower(1);


        if (DriveLeft.getCurrentPosition()-initEncoder>drive_calc(20))
        {
          DriveLeft.setPower(0);
          DriveRight.setPower(0);
          v_state++;
          initEncoder = DriveLeft.getCurrentPosition();

        }
        break;

      case 3:
        DriveRight.setPower(-1);
        DriveLeft.setPower(1);


        if (Math.abs (DriveLeft.getCurrentPosition()-initEncoder)>drive_calc(15))
        {
          DriveLeft.setPower(0);
          DriveRight.setPower(0);
          v_state++;
          initEncoder = DriveLeft.getCurrentPosition();

        }
        break;

      case 4:
        DriveRight.setPower(1);
        DriveLeft.setPower(1);


        if (DriveLeft.getCurrentPosition()-initEncoder>drive_calc(120))
        {
          DriveLeft.setPower(0);
          DriveRight.setPower(0);
          v_state++;
          initEncoder = DriveLeft.getCurrentPosition();

        }
        break;
    }
  }

  @Override public void stop ()
  {

  }
}
