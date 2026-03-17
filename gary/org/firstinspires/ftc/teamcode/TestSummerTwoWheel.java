package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TestSummerTwoWheel", group="Test")
public class TestSummerTwoWheel extends LinearOpMode {

    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private Servo testServo;
    private Servo testServo2;

    @Override
    public void runOpMode() {
        // Hardware mapping
        leftMotor = hardwareMap.get(DcMotor.class, "motor0");
        rightMotor = hardwareMap.get(DcMotor.class, "motor1");
        testServo = hardwareMap.get(Servo.class, "servo0");
        testServo2 = hardwareMap.get(Servo.class, "servo1");

        // Motor setup
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Servo setup
        testServo.setPosition(0.5);
        testServo2.setPosition(0.5);

        telemetry.addLine("Ready to start - Two Motors, Two Servos");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // One-stick arcade drive
            double drive = -gamepad1.left_stick_y;
            double turn  = gamepad1.left_stick_x;

            double leftPower  = drive + turn;
            double rightPower = drive - turn;

            leftPower  = Math.max(-1.0, Math.min(1.0, leftPower));
            rightPower = Math.max(-1.0, Math.min(1.0, rightPower));

            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);

            // First servo: A/B/Y
            if (gamepad1.a) {
                testServo.setPosition(0.0);
            } else if (gamepad1.b) {
                testServo.setPosition(0.5);
            } else if (gamepad1.y) {
                testServo.setPosition(1.0);
            }

            // Second servo: X / Right bumper / Left bumper
            if (gamepad1.x) {
                testServo2.setPosition(0.0);
            } else if (gamepad1.right_bumper) {
                testServo2.setPosition(0.5);
            } else if (gamepad1.left_bumper) {
                testServo2.setPosition(1.0);
            }

            // Telemetry
            telemetry.addData("Left Motor Power", leftPower);
            telemetry.addData("Right Motor Power", rightPower);
            telemetry.addData("Servo1 Pos", testServo.getPosition());
            telemetry.addData("Servo2 Pos", testServo2.getPosition());
            telemetry.update();
        }
    }
}
