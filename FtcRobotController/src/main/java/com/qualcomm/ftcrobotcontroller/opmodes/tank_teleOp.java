/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.util.Range;


public class tank_teleOp extends OpMode {

    private DcMotorController v_dc_motor_controller_drive;

    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    private DcMotor Rack1;
    private DcMotor Rack2;
    private DcMotor Angle;
    boolean speedMode = true;
    int rackMode;
    private Servo TedLeft;
    private Servo TedRight;
    private double LeftTedPosition;
    private double RightTedPosition;
    GyroSensor sensorGyro;
    public tank_teleOp() {

    }
    int xVal, yVal, zVal;
    @Override public void init() {

        v_dc_motor_controller_drive = hardwareMap.dcMotorController.get("Drive");

        DriveLeft = hardwareMap.dcMotor.get ("DriveLeft");

        DriveRight = hardwareMap.dcMotor.get ("DriveRight");
        Rack1 = hardwareMap.dcMotor.get ("Rack1");
        Rack2 = hardwareMap.dcMotor.get ("Rack2");
        Angle = hardwareMap.dcMotor.get ("Angle");
        DriveLeft.setDirection (DcMotor.Direction.FORWARD);
        DriveRight.setDirection (DcMotor.Direction.REVERSE);
        TedLeft=hardwareMap.servo.get("TedLeft");
        TedRight=hardwareMap.servo.get("TedRight");

        sensorGyro = hardwareMap.gyroSensor.get("gyro");
        //initialize TED positions
        TedLeft.setPosition(0.45);
        TedRight.setPosition(0.45);
        //initialize TED variables so that the TED's don't jump at the start of LOOP
        LeftTedPosition=0.45;
        RightTedPosition=0.45;
        rackMode = 1;
        xVal= 0;
        yVal= 0;
        zVal= 0;
    }

    @Override
    public void loop() {
        telemetry.addData("TedL", TedLeft.getPosition());
        telemetry.addData("TedR", TedRight.getPosition());
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;
        float driveStraight = gamepad1.right_trigger;
        boolean normSpeed = gamepad1.y;
        boolean halfSpeed = gamepad1.x;

        float left2 = -gamepad2.left_stick_y;
        float right2 = -gamepad2.right_stick_y;
        boolean rackNorm = gamepad2.a;
        boolean rackLeft = gamepad2.x;
        boolean rackRight = gamepad2.b;

        //if the right trigger is pressed, move the TED flag down
        if (gamepad2.right_trigger>.1) {
            RightTedPosition=RightTedPosition - 0.01;
        }

        //if the right bumper is pressed, move the TED flag up
        if (gamepad2.right_bumper) {
            RightTedPosition=RightTedPosition + 0.01;
        }
        //make sure the TED position stays in the range that the servo can handle
        RightTedPosition = Range.clip(RightTedPosition, 0, .44);

        //if the left trigger is pressed, move the TED flag down
        if (gamepad2.left_trigger>.05) {
            LeftTedPosition=LeftTedPosition + 0.01;
        }

        //if the left bumper is pressed, move the TED flag up
        if (gamepad2.left_bumper) {
            LeftTedPosition=LeftTedPosition - 0.01;
        }
        //make sure the TED position stays in the range that the servo can handle
        LeftTedPosition = Range.clip(LeftTedPosition, 0.5, 1);

        // clip the right/left drive values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        //take the squareroot of the left/right sticks to give better control on the low end.
        //you can't take the squareroot of a negative number, so remove sign, then take sqrt root, then reapply sign
       if (right < 0)
       {right = (float)Math.sqrt(Math.abs(right));
           right = -right; }
        else {right = (float)Math.sqrt(right);}

        if (left < 0)
        {left = (float)Math.sqrt(Math.abs(left));
            left = -left; }
        else {left = (float)Math.sqrt(left);}

        if (left2 < 0)
        {left2 = (float)Math.sqrt(Math.abs(left2));
            left2 = -left2; }
        else {left2 = (float)Math.sqrt(left2);}

        //handle the speed mode buttons to determine whether to run at full speed or partial speed
        if (normSpeed == true)
        {
            speedMode = true;
        }

        if(halfSpeed == true)
        {
            speedMode = false;
        }

        //once we know what speed mode to be in, adjust speed if in slow mode.
        if (speedMode == false)

        {
            left = left/2;
            right = right/2;
        }

        if (rackNorm == true)
        {
            rackMode = 1;
        }
        if (rackLeft == true)
        {
            rackMode = 2;
        }
        if (rackRight == true)
        {
            rackMode = 3;
        }

        if (rackMode == 1 )
        {
            Rack1.setPower(-right2);
            Rack2.setPower(right2);
        }
        if (rackMode == 2 )
        {
            Rack2.setPower(right2);
        }
        if (rackMode == 3)
        {
            Rack1.setPower(-right2);
        }
        telemetry.addData("leftTED","" + TedLeft.getPosition());
        telemetry.addData("rightTED","" + TedRight.getPosition());

        // write the values to the motors

        //check to see if the go-straight trigger is being pressed.  if not, run from sticks
        //if go-straight trigger is pressed, then use trigger to determine both motor powers.
        if ( driveStraight < .1 && gamepad1.left_trigger <.1)
        {
        DriveRight.setPower(right);
        DriveLeft.setPower(left);
        }
        if (driveStraight > .1)
        {
            DriveRight.setPower(driveStraight);
            DriveLeft.setPower(driveStraight);
        }

        //if left trigger is pressed, run backwards
        if(gamepad1.left_trigger > .1)
        {
            DriveLeft.setPower(-gamepad1.left_trigger);
            DriveRight.setPower(-gamepad1.left_trigger);
        }

        /*Rack1.setPower(-right2);
        Rack2.setPower(right2*.6);*/  //lower the power on this one to try to match the speed of the other one
        Angle.setPower(left2*.5);

        TedLeft.setPosition(LeftTedPosition);
        TedRight.setPosition(RightTedPosition);

        sensorGyro.calibrate();
        telemetry.addData("Gyro x" , "" + sensorGyro.rawX());
        telemetry.addData("Gyro y" , "" + sensorGyro.rawY());
        telemetry.addData("Gyro z" , "" + sensorGyro.rawZ());
        telemetry.addData("Gyro heading" , "" + sensorGyro.getHeading());
       // telemetry.addData("Gyro rotation" , "" + sensorGyro.getRotation());



    }


    @Override
    public void stop() {

        DriveLeft.setPower(0);
        DriveRight.setPower(0);
        Angle.setPower(0);
        Rack1.setPower(0);
        Rack2.setPower(0);

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}
