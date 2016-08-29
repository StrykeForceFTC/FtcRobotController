package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by family on 9/27/2015.
 */
public class  SEQ_Mount extends low_level_class {


    private DcMotor DriveLeft;
    private DcMotor Angle;
    private DcMotor DriveRight;
    private double timeout=0;
    private double initialtime;
    private int v_state = 0;
    int LeftInitEncoder;
    int RightInitEncoder;
    int LeftOldEncoder;
    int LeftNewEncoder;
    int RememberLastState;


     int nearMount(int direction, double goOne, double turnTwo, double goThree, double turnFour, double goFive) {
        DriveLeft = hardwareMap.dcMotor.get("DriveLeft");
        Angle = hardwareMap.dcMotor.get("Angle");
        DriveRight = hardwareMap.dcMotor.get("DriveRight");

        switch (v_state) {

            case -1:  //this is the error case.  this should never be reached
                telemetry.addData("StrykeForce Error","timeout or other");
                DriveLeft.setPower(0);
                DriveRight.setPower(0);
                Angle.setPower(0);
                break;
            case 0:  //initialize running
                initialtime = System.currentTimeMillis();  //reset timer
                timeout = 20000;                                //set timeout for case 1
                v_state++;
                LeftInitEncoder = DriveLeft.getCurrentPosition();   //reset encoder to current position to sam
                RightInitEncoder = DriveRight.getCurrentPosition();  //not using this yet
                LeftOldEncoder = LeftInitEncoder;                //initialize "no movement" detector
                break;
            case 1:  //move forward away from starting position
                DriveLeft.setPower(1);
                DriveRight.setPower(1);
                LeftNewEncoder = DriveLeft.getCurrentPosition();

                if (Math.abs(LeftNewEncoder - LeftInitEncoder) > drive_calc(goOne)) {
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
                Angle.setPower(.35);
                telemetry.addData("time","" + initialtime);
                if ((System.currentTimeMillis()-initialtime) > 1000) {
                    Angle.setPower(0);
                    v_state++;
                    initialtime=System.currentTimeMillis();
                }
                break;
            case 3:  //turn toward mountain

                DriveLeft.setPower(direction * .5);
                DriveRight.setPower(-direction * .5);
                //telemetry.addData("turn", "" + turn_calc(45));
                //telemetry.addData("clicks", ""+Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder));
                if (Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder) > turn_calc(turnTwo)) {
                    DriveLeft.setPower(0);
                    DriveRight.setPower(0);
                    v_state++;
                    initialtime = System.currentTimeMillis();
                    timeout = 15000;
                    LeftInitEncoder = DriveLeft.getCurrentPosition();
                }
                if (System.currentTimeMillis()-initialtime > timeout){
                     v_state = -1;
                }
                break;

            case 4:  //drive until robot is in front of ramp
                DriveLeft.setPower(1);
                DriveRight.setPower(1);

                if (Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder) > drive_calc(goThree)) {
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

            case 5:     //turn to face the mountain ramp
                DriveLeft.setPower(direction * .5);
                DriveRight.setPower(-direction * .5);

                if (Math.abs(DriveLeft.getCurrentPosition() - LeftInitEncoder) > turn_calc(turnFour))
                {
                    DriveLeft.setPower(0);
                    DriveRight.setPower(0);
                    v_state++;
                    initialtime = System.currentTimeMillis();
                    timeout = 20000;
                    LeftInitEncoder = DriveLeft.getCurrentPosition();

                }
                if (System.currentTimeMillis()-initialtime > timeout){
                    v_state = -1;
                }
                break;

            case 6:   //drive onto and up the ramp
                DriveLeft.setPower(0.63);
                DriveRight.setPower(0.63);

                if (DriveLeft.getCurrentPosition() - LeftInitEncoder > drive_calc(goFive)) {
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

            case 20:
                DriveLeft.setPower(-1);       //then backup if we got stuck
                DriveRight.setPower(-1);
                LeftNewEncoder = DriveLeft.getCurrentPosition();
                if (Math.abs(LeftNewEncoder-LeftOldEncoder) > drive_calc(1)) {  //backup 1 inch
                    v_state = RememberLastState;  //return to where we left off
                    DriveLeft.setPower(.5);     //speed will be re-adjusted in the case we paused
                    DriveRight.setPower(.5);
                }
                break;

            }

            return v_state;
        }

    }
