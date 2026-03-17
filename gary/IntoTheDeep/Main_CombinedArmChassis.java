package IntoTheDeep;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Main_CombinedArmChassis extends OpMode {
    // Declare motors for the chassis
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    // Declare hardware for the arm
    DcMotor armMotor;
    Servo clawServo;
    Servo wristServo;

    // Servo positions for the claw and wrist
    double clawOpenPosition = 0.0;
    double clawClosePosition = 1.0;
    double wristUpPosition = 0.0;
    double wristDownPosition = 1.0;
    double wristIncrement = 0.1;//Change to 0.01?
    double currentWristPosition = 0.0;  // Start at the middle position
    double armSlowFactor = 0.6;  // Slow movement factor for the arm

    // Debounce variables for the wrist movement
    private boolean previousLeftBumper = false;
    private boolean previousRightBumper = false;

    final double DEADBAND = 0.1;

    @Override
    public void init() {
        // Initialize chassis motors
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

        // Reverse the right side motors
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize arm hardware
        armMotor = hardwareMap.dcMotor.get("armMotor");
        clawServo = hardwareMap.servo.get("clawServo");
        wristServo = hardwareMap.servo.get("wristServo");

        // Set motor direction and zero power behavior for the arm motor
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        // Chassis control using the gamepad left and right sticks
        double y = gamepad1.left_stick_y; // Forward/backward
        double x = -gamepad1.left_stick_x * 1.1; // Strafe (counteract imperfect strafing)
        double rx = -gamepad1.right_stick_x; // Rotate
        
        // Denominator ensures that power ratios stay in the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        motorFrontLeft.setPower(frontLeftPower);
        motorBackLeft.setPower(backLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorBackRight.setPower(backRightPower);

        // Arm control: using D-pad for arm up and down
        double armPower = 0.0;
        if (gamepad1.dpad_up) {
            armPower = armSlowFactor;  // Move arm up slowly
        } else if (gamepad1.dpad_down) {
            armPower = -armSlowFactor; // Move arm down slowly
        }
        armMotor.setPower(armPower);

        // Control the claw servo with gamepad buttons
        if (gamepad1.a) {
            clawServo.setPosition(clawOpenPosition); // Open the claw
        } else if (gamepad1.b) {
            clawServo.setPosition(clawClosePosition); // Close the claw
        }

        // Wrist control: Incremental movement with bumper buttons
        if (gamepad1.left_bumper && !previousLeftBumper) {
            // Move wrist up by an incremental amount
            currentWristPosition = Math.max(wristUpPosition, currentWristPosition - wristIncrement);
            wristServo.setPosition(currentWristPosition);
            telemetry.addData("Test","pls work");
        } else if (gamepad1.right_bumper && !previousRightBumper) {
            // Move wrist down by an incremental amount
            currentWristPosition = Math.min(wristDownPosition, currentWristPosition + wristIncrement);
            wristServo.setPosition(currentWristPosition);
            telemetry.addData("Test","pls work R");
        } else {
            // When no bumper is pressed, maintain current wrist position
            wristServo.setPosition(currentWristPosition);
        }

        // Update debounce states for the bumpers
        previousLeftBumper = gamepad1.left_bumper;
        previousRightBumper = gamepad1.right_bumper;

        // Telemetry for debugging
        telemetry.addData("frontleftPower", frontLeftPower);
        telemetry.addData("backleftPower", backLeftPower);
        telemetry.addData("backrightPower", backRightPower);
        telemetry.addData("frontrightPower", frontRightPower);
        telemetry.addData("Arm Power", armPower);
        telemetry.addData("Claw Position", clawServo.getPosition());
        telemetry.addData("Wrist Position", currentWristPosition);
        telemetry.addData("Left Bumper", gamepad1.left_bumper);
        telemetry.addData("Right Bumper", gamepad1.right_bumper);
        telemetry.update();
    }
}
