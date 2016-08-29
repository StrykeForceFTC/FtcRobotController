package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
/**
 * Created by family on 9/27/2015.
 */
public class SEQ_nearMount_old extends low_level_class {


    private DcMotor DriveLeft;

    private DcMotor DriveRight;

    private int v_state = 0;
    int initEncoder;
    int RinitEncoder;

     int badnearMount(int direction, double goOne, double turnTwo, double goThree, double turnFour, double goFive) {
        DriveLeft = hardwareMap.dcMotor.get("DriveLeft");

        DriveRight = hardwareMap.dcMotor.get("DriveRight");

       switch (v_state) {
           case 0:

               v_state++;
               initEncoder = DriveLeft.getCurrentPosition();
               RinitEncoder = DriveRight.getCurrentPosition();
               break;
           case 1:
               DriveLeft.setPower(1);
               DriveRight.setPower(1);

                if (Math.abs(DriveLeft.getCurrentPosition() - initEncoder) > drive_calc(goOne)) {
                    DriveLeft.setPower(0);
                    DriveRight.setPower(0);
                    v_state++;
                    initEncoder = DriveLeft.getCurrentPosition();
                //    telemetry.addData("left Enc", "" + Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder));
                //    telemetry.addData("right Enc", "" + Math.abs(DriveRight.getCurrentPosition() - RightInitEncoder));
                }
                break;

             case 2:

                DriveLeft.setPower(direction * .5);
                DriveRight.setPower(-direction * .5);
                //telemetry.addData("turn", "" + turn_calc(45));
                //telemetry.addData("clicks", ""+Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder));
                if (Math.abs(DriveLeft.getCurrentPosition() - initEncoder) > turn_calc(turnTwo)) {
                    DriveLeft.setPower(0);
                    DriveRight.setPower(0);
                    v_state++;
                    initEncoder = DriveLeft.getCurrentPosition();
                }
                break;
            case 3:
                DriveLeft.setPower(1);
                DriveRight.setPower(1);

                if (Math.abs(DriveLeft.getCurrentPosition() - initEncoder) > drive_calc(goThree)) {
                    DriveLeft.setPower(0);
                    DriveRight.setPower(0);
                    v_state++;
                    initEncoder = DriveLeft.getCurrentPosition();

                }
                break;
            case 4:
                DriveLeft.setPower(direction * .5);
                DriveRight.setPower(-direction * .5);

                if (Math.abs(DriveLeft.getCurrentPosition() - initEncoder) > turn_calc(turnFour))
                {
                    DriveLeft.setPower(0);
                    DriveRight.setPower(0);
                    v_state++;
                    initEncoder = DriveLeft.getCurrentPosition();

                }
                break;
            case 5:
                DriveLeft.setPower(0.35);
                DriveRight.setPower(0.35);

                if (DriveLeft.getCurrentPosition() - initEncoder > drive_calc(goFive)) {
                    DriveLeft.setPower(0);
                    DriveRight.setPower(0);
                    v_state++;
                    initEncoder = DriveLeft.getCurrentPosition();
                }
                break;

             }

            return v_state;
        }

    }
