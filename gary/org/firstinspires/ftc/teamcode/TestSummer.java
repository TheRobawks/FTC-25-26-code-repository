package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TestSummer", group="Test")
public class TestSummer extends LinearOpMode {

    private DcMotor testMotor;
    private Servo testServo;

    @Override
    public void runOpMode() {
        // Hardware mapping
        testMotor = hardwareMap.get(DcMotor.class, "motor0");
        testServo = hardwareMap.get(Servo.class, "servo0");

        // Optional: Set initial motor and servo states
        testMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        testMotor.setDirection(DcMotor.Direction.FORWARD);
        testMotor.setPower(0);

        testServo.setPosition(0.5);  // Center position

        telemetry.addLine("Ready to start - Core Hex + Servo");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Motor control: left stick Y (invert for natural forward)
            double motorPower = -gamepad1.left_stick_y;
            testMotor.setPower(motorPower);

            // Servo control: A = 0.0, B = 0.5, Y = 1.0
            if (gamepad1.a) {
                testServo.setPosition(0.0);
            } else if (gamepad1.b) {
                testServo.setPosition(0.5);
            } else if (gamepad1.y) {
                testServo.setPosition(1.0);
            }

            telemetry.addData("Motor Power", motorPower);
            telemetry.addData("Servo Pos", testServo.getPosition());
            telemetry.update();
        }
    }
}