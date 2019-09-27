package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


    @TeleOp(name = "Basic Linear Opmode SHAWNBRANCH", group = "Linear Opmode")
    public class MyFIRSTJavaOpMode extends LinearOpMode {
        NormalizedColorSensor colorSensor;
        private DcMotor rightWheel;
        private DcMotor leftWheel;
        private DcMotor clawArm;
        private Servo claw;
        private ElapsedTime runtime = new ElapsedTime();
        View relativeLayout;


        @Override public void runOpMode() throws InterruptedException {
            int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("Relative Layout", "id", hardwareMap.appContext.getPackageName());
            relativeLayout = ((Activity)hardwareMap.appContext).findViewById(relativeLayoutId);

            try {
                runSample();
            } finally {
                relativeLayout.post(new Runnable() {
                    public void run() {
                        relativeLayout.setBackgroundColor(Color.WHITE);
                    }
                });
            }
            protected void runSample() throws InterruptedException {
            //the type of color set
            float[] hsvValues = new float[3];
            final float values[] = hsvValues;
            //keeps the button state
            boolean bPrevState = false;
            boolean bCurrState = false;


            colorSensor = hardwareMap.get(NormalizedColorSensor.class, "Color Sensor");

            //turns on the light if it isn't already on
            if (colorSensor instanceof SwitchableLight) {
                ((SwitchableLight) colorSensor).enableLight(true);
            }
        }

            telemetry.addData("Status", "Initializing");
            telemetry.update();

            //getting the names of the hardware in the configuration profile from the phones
            rightWheel = hardwareMap.dcMotor.get("Right Wheel");
            leftWheel = hardwareMap.dcMotor.get("Left Wheel");
            clawArm = hardwareMap.dcMotor.get("Claw Arm");
            claw = hardwareMap.servo.get("Claw");

            //because one motor will be flipped, set one opposite of the other
            leftWheel.setDirection(DcMotor.Direction.FORWARD);
            rightWheel.setDirection(DcMotor.Direction.REVERSE);

            telemetry.addData("Status", "Initialized");
            telemetry.update();

            //Driver Presses Play
            waitForStart();

            //run
            double leftPower = 0.0;
            double rightPower = 0.0;
            double clawUpPower = 0.0;
            while (opModeIsActive()) {

                //controller mapping
                double drive = -gamepad1.left_stick_y;
                double turn = -gamepad1.right_stick_x;
                double ClawUp = -gamepad2.left_stick_y;

                //set max and min power
                leftPower = Range.clip(drive + turn, -1, 1);
                rightPower = Range.clip(drive - turn, -1, 1);
                clawUpPower = Range.clip(ClawUp, -0.5, 0.5);

                //set power from the controller input
                leftWheel.setPower(leftPower);
                rightWheel.setPower(rightPower);
                clawArm.setPower(clawUpPower);

                //servo
                if (gamepad1.y) {
                    claw.setPosition(1.0);
                }
                else if (gamepad1.a) {
                    claw.setPosition(1.0);
                }

                //send data to phone screen
                telemetry.addData("Target Power", rightPower);
                telemetry.addData("Target Power", leftPower);
                telemetry.addData("Motor Power", rightWheel.getPower());
                telemetry.addData("Motor Power", leftWheel.getPower());
                telemetry.addData("ServoPosition", claw.getPosition());
                telemetry.addData("runtime", runtime.toString());
                telemetry.addData("Status", "Running");
                telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
                telemetry.update();
            }
        }
    }

