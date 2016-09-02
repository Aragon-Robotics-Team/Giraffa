package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

public class TeleOp_Demo extends LinearOpMode {

    DcMotor motorBackRight;
    DcMotor motorFrontRight;
    DcMotor motorBackLeft;
    DcMotor motorFrontLeft;
    DcMotor lift;

    public void resetEncoders() {
        //resets all encoders and sets the RunMode back to use encoders
        //reset encoders
        motorBackLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorBackRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorFrontLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorFrontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        try {
            while (motorBackLeft.getCurrentPosition() != 0) {

                telemetry.addData("f_l_encoder", motorFrontLeft.getCurrentPosition());
                telemetry.addData("f_r_encoder", motorFrontRight.getCurrentPosition());
                telemetry.addData("b_l_encoder", motorBackLeft.getCurrentPosition());
                telemetry.addData("b_r_encoder", motorBackRight.getCurrentPosition());
                waitForNextHardwareCycle();
            }
        } catch (InterruptedException e) {
            telemetry.addData("Interrupted", "True");
        }

        motorBackLeft.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorBackRight.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorFrontLeft.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorFrontRight.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        motorBackLeft = hardwareMap.dcMotor.get("motor_b_l");
        motorBackRight = hardwareMap.dcMotor.get("motor_b_r");
        motorFrontLeft = hardwareMap.dcMotor.get("motor_f_l");
        motorFrontRight = hardwareMap.dcMotor.get("motor_f_r");
        lift = hardwareMap.dcMotor.get("lift");


        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();

        waitForStart();

        while (opModeIsActive()) {

            // the left stick drives sideways (left and right), forward (up), and backwards(down)
            // the right stick does all the turns
            // only one gamepad
            // y=x^5

            if(gamepad2.right_bumper) {
                motorFrontRight.setPower(Math.pow(Range.clip(gamepad2.right_stick_x - gamepad2.left_stick_x + gamepad2.left_stick_y, -1, 1), 5));
                motorBackRight.setPower(Math.pow(Range.clip(gamepad2.right_stick_x + gamepad2.left_stick_x + gamepad2.left_stick_y, -1, 1), 5));
                motorBackLeft.setPower(Math.pow(Range.clip(-gamepad2.right_stick_x + gamepad2.left_stick_x + gamepad2.left_stick_y, -1, 1), 5));
                motorFrontLeft.setPower(Math.pow(Range.clip(-gamepad2.right_stick_x - gamepad2.left_stick_x + gamepad2.left_stick_y, -1, 1), 5));

                if(gamepad2.dpad_up) {
                    lift.setPower(-0.6);
                }

                else if(gamepad2.dpad_down) {
                    lift.setPower(0.2);
                }

                else {
                    lift.setPower(-0.1);
                }
            }

            else {
                motorFrontRight.setPower(Math.pow(Range.clip(gamepad1.right_stick_x - gamepad1.left_stick_x + gamepad1.left_stick_y, -1, 1), 5));
                motorBackRight.setPower(Math.pow(Range.clip(gamepad1.right_stick_x + gamepad1.left_stick_x + gamepad1.left_stick_y, -1, 1), 5));
                motorBackLeft.setPower(Math.pow(Range.clip(-gamepad1.right_stick_x + gamepad1.left_stick_x + gamepad1.left_stick_y, -1, 1), 5));
                motorFrontLeft.setPower(Math.pow(Range.clip(-gamepad1.right_stick_x - gamepad1.left_stick_x + gamepad1.left_stick_y, -1, 1), 5));

                if(gamepad1.dpad_up) {
                    lift.setPower(-0.6);
                }

                else if(gamepad1.dpad_down) {
                    lift.setPower(0.2);
                }
                else {
                    lift.setPower(-0.1);
                }
            }

            telemetry.addData("Text", "XDrive_V1");
            telemetry.addData("left motor", motorBackLeft.getPower());
            telemetry.addData("right motor", motorBackRight.getPower());
            telemetry.addData("f_l_encoder", motorFrontLeft.getCurrentPosition());
            telemetry.addData("f_r_encoder", motorFrontRight.getCurrentPosition());
            telemetry.addData("b_l_encoder", motorBackLeft.getCurrentPosition());
            telemetry.addData("b_r_encoder", motorBackRight.getCurrentPosition());

            waitOneFullHardwareCycle();
        }
    }
}
