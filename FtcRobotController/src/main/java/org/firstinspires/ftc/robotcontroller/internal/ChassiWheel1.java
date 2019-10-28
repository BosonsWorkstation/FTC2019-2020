package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Swivel Drive Test", group = "Linear Opmode")
public class ChassiWheel1 extends LinearOpMode {
    private DcMotor RightWheel;
    private DcMotor LeftWheel;
    private DcMotor CenterWheel;
    private DcMotor ClawArm;
    private Servo Claw;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();
        telemetry.addData("Test","Test ");

        RightWheel = hardwareMap.dcMotor.get("Right Wheel");
        LeftWheel = hardwareMap.dcMotor.get("Left Wheel");
        CenterWheel = hardwareMap.dcMotor.get("Center Wheel");
//        ClawArm = hardwareMap.dcMotor.get("Claw Arm");
//        Claw = hardwareMap.servo.get("Claw");

        LeftWheel.setDirection(DcMotor.Direction.FORWARD);
        RightWheel.setDirection(DcMotor.Direction.REVERSE);
        CenterWheel.setDirection(DcMotor.Direction.FORWARD);



        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Driver Presses Play
        waitForStart();
        //run
        double LeftPower = 0.0;
        double RightPower = 0.0;
        double CenterPower = 0.0;
        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double turn = -gamepad1.right_stick_x;
            double ClawUp = -gamepad2.left_stick_y;
            double ClawClose = -gamepad2.right_trigger;
            double ClawOpen = -gamepad2.left_trigger;

            LeftPower = Range.clip(drive + turn, -1, 1);
            RightPower = Range.clip(drive - turn, -1, 1);
             CenterPower = Range.clip(ClawUp, -1, 1);
            LeftWheel.setPower(LeftPower);
            RightWheel.setPower(RightPower);
            CenterWheel.setPower(CenterPower);

            //servo
            if (gamepad1.dpad_up) {
                CenterWheel.setPower(.25);
                Thread.sleep(500);
            }
            if (gamepad1.dpad_right) {
                CenterWheel.setPower(-.25);
                Thread.sleep(500);

            }
            if (gamepad1.dpad_down) {

            }
            if (gamepad1.dpad_left) {

            }

            if (gamepad1.y) {
                CenterWheel.setTargetPosition(1);
            }
            else if (gamepad1.a) {
                CenterWheel.setTargetPosition(1);
            }
            telemetry.addData("Target Power", RightPower);
            telemetry.addData("Target Power", LeftPower);
            telemetry.addData("Motor Power", RightWheel.getPower());
            telemetry.addData("Motor Power", LeftWheel.getPower());
            telemetry.addData("ServoPosition", CenterWheel.getTargetPosition());
            telemetry.addData("runtime", runtime.toString());
            telemetry.addData("Status", "Running");
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", LeftPower, RightPower);
            telemetry.update();
        }
    }
}


