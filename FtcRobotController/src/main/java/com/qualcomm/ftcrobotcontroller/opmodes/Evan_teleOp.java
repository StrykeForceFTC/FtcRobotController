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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.util.Range;


public class Evan_teleOp extends OpMode {

    private DcMotorController v_dc_motor_controller_drive;

    private DcMotor DriveLeft;
    private DcMotor DriveRight;

    public Evan_teleOp() {

    }

    @Override public void init() {

        v_dc_motor_controller_drive = hardwareMap.dcMotorController.get("Drive");

        DriveLeft = hardwareMap.dcMotor.get ("DriveLeft");

        DriveRight = hardwareMap.dcMotor.get ("DriveRight");
        DriveLeft.setDirection (DcMotor.Direction.FORWARD);
        DriveRight.setDirection (DcMotor.Direction.REVERSE);
       }
    @Override
    public void loop() {
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;
        float driveStraight = gamepad1.right_trigger;

          // clip the right/left drive values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

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


    }


    @Override
    public void stop() {

        DriveLeft.setPower(0);
        DriveRight.setPower(0);
    }
}