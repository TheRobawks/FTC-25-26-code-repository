package IntoTheDeep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class Auto_Far_Park_Only extends LinearOpMode {
    // Declare motors for the chassis
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    // Constants for encoder calculations
    final double COUNTS_PER_MOTOR_REV = 1440; // Encoder counts per motor revolution
    final double DRIVE_GEAR_REDUCTION = 1.0; // No gear reduction
   final double WHEEL_DIAMETER_INCHES = 2.95; // 75mm in inches
final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                               (WHEEL_DIAMETER_INCHES * Math.PI);
final double ROBOT_TURN_CIRCUMFERENCE = 21.25 * Math.PI; // Adjust this based on actual robot
final double TURN_90_DEGREES_INCHES = (ROBOT_TURN_CIRCUMFERENCE / 4) / 2;



    @Override
    public void runOpMode() {
        // Initialize chassis motors
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

        // Reverse the right side motors
        motorBackLeft.setDirection(DcMotor.Direction.FORWARD);
        motorBackRight.setDirection(DcMotor.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        // Set up motors for encoder mode
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        // Step 1: Turn 90 degrees
        encoderDrive(0.5, TURN_90_DEGREES_INCHES, -TURN_90_DEGREES_INCHES, 5.0);

        // Step 2: Move forward 6.5 feet
        encoderDrive(0.5, 29, 29, 5.0);
    }

    /**
     * Drive using encoders for a specific distance.
     * 
     * @param speed          Motor power/speed
     * @param leftInches     Distance for left wheels in inches
     * @param rightInches    Distance for right wheels in inches
     * @param timeoutSeconds Timeout in seconds
     */
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutSeconds) {
        int newFrontLeftTarget;
        int newBackLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;

        // Calculate target positions
        newFrontLeftTarget = motorFrontLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        newBackLeftTarget = motorBackLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        newFrontRightTarget = motorFrontRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
        newBackRightTarget = motorBackRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);

        motorFrontLeft.setTargetPosition(newFrontLeftTarget);
        motorBackLeft.setTargetPosition(newBackLeftTarget);
        motorFrontRight.setTargetPosition(newFrontRightTarget);
        motorBackRight.setTargetPosition(newBackRightTarget);

        // Switch to RUN_TO_POSITION mode
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set power and start movement
        motorFrontLeft.setPower(speed);
        motorBackLeft.setPower(speed);
        motorFrontRight.setPower(speed);
        motorBackRight.setPower(speed);

        // Wait until the motors finish or timeout expires
        while (opModeIsActive() && (motorFrontLeft.isBusy() && motorFrontRight.isBusy())) {
            telemetry.addData("Path1", "Running to %7d :%7d", newFrontLeftTarget, newFrontRightTarget);
            telemetry.addData("Path2", "Running at %7d :%7d",
                    motorFrontLeft.getCurrentPosition(),
                    motorFrontRight.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion
        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackRight.setPower(0);

        // Reset motors to use encoders
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}

