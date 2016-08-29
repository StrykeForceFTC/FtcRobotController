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
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class testing_the_IR extends OpMode
{
    IrSeekerSensor irSeeker;
    private DcMotorController v_dc_motor_controller_drive;
    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    public testing_the_IR()
    {
    }
    @Override public void init()
    {
        irSeeker = hardwareMap.irSeekerSensor.get("ir_seeker");
        v_dc_motor_controller_drive
                = hardwareMap.dcMotorController.get("Drive");
        DriveLeft = hardwareMap.dcMotor.get ("DriveLeft");
        DriveRight = hardwareMap.dcMotor.get ("DriveRight");
        DriveRight.setDirection (DcMotor.Direction.REVERSE);
        DriveLeft.setPower(0);
        DriveRight.setPower(0);


    }

    @Override
    public void loop()
    {
        double angle = 0;
        double strength = 0;
        if (irSeeker.signalDetected())
        {
            angle = irSeeker.getAngle();
            strength = irSeeker.getStrength();
        }
        if (angle < 0)
        {
            DriveRight.setPower(0.5);
            DriveLeft.setPower(-0.5);
        }
        else if (angle > 0)
        {
            DriveRight.setPower(-0.5);
            DriveLeft.setPower(0.5);
        }
        telemetry.addData("angle", angle);
        telemetry.addData("strength", strength);
    }

    @Override
    public void stop()
    {

    }
}
