package IntoTheDeep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class Main_Auto_Low_Bucket_Far_Park extends LinearOpMode {
    // Declare motors for the chassis
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    // Declare hardware for the arm
    DcMotor armMotor;
    Servo clawServo;
    Servo wristServo;

    // Constants for encoder calculations
    final double COUNTS_PER_MOTOR_REV = 1440; // Encoder counts per motor revolution
    final double DRIVE_GEAR_REDUCTION = 1.0; // No gear reduction
    final double WHEEL_DIAMETER_INCHES = 2.95; // 75mm in inches
    final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                   (WHEEL_DIAMETER_INCHES * Math.PI);
    final double ROBOT_TURN_CIRCUMFERENCE = 21.25 * Math.PI; // Adjust this based on actual robot
    final double TURN_45_DEGREES_INCHES = (ROBOT_TURN_CIRCUMFERENCE / 8) / 2;
    final double TURN_90_DEGREES_INCHES = (ROBOT_TURN_CIRCUMFERENCE / 4) /2;

    // Servo positions for the claw and wrist
    //double clawOpenPosition = 0.1;
    double clawClosePosition = 0.0;
    double wristUpPosition = 0.0;
    double wristDownPosition = 1.0;

    @Override
    public void runOpMode() {
        // Initialize chassis motors
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

        // Initialize arm hardware
        armMotor = hardwareMap.dcMotor.get("armMotor");
        clawServo = hardwareMap.servo.get("clawServo");
        wristServo = hardwareMap.servo.get("wristServo");

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

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        // Step 1: Drive to basket
        encoderDrive(0.5, -TURN_45_DEGREES_INCHES, TURN_45_DEGREES_INCHES, 5.0);
        encoderDrive(0.5, 5, 5, 5.0);
        encoderDrive(0.5, -TURN_90_DEGREES_INCHES, TURN_90_DEGREES_INCHES, 5.0);
        moveArm(0.5, -250);
        encoderDrive(0.5,5.5,5.5,5.0);

        
        

        // Step 4: Open the claw
       // clawServo.setPosition(clawOpenPosition);
        //sleep(500); // Wait for the claw to fully open
        // Step 6: Adjust the wrist position
        wristServo.setPosition(wristDownPosition);
        sleep(2000);
        
        // Step 5: Open the claw
        clawServo.setPosition(clawClosePosition);
        sleep(1500); // Wait for the claw to fully close
        
         //Step 6: Rotate wrist back to starting position
        wristServo.setPosition(wristUpPosition);
        sleep(500);
        
        encoderDrive(0.5,-10,-10,5.0);
        moveArm(0.5, 250);
        encoderDrive(0.5, TURN_45_DEGREES_INCHES, -TURN_45_DEGREES_INCHES, 5.0);
        encoderDrive(0.5,-20.5,-22.5,5.0);
        encoderDrive(0.5, TURN_45_DEGREES_INCHES, -TURN_45_DEGREES_INCHES, 5.0);
        encoderDrive(0.5,-2,-2,5.0);
        encoderDrive(0.5, -TURN_45_DEGREES_INCHES, TURN_45_DEGREES_INCHES, 5.0);
        encoderDrive(0.5, -7.5, -7.5, 5.0);
        encoderDrive(0.5, TURN_90_DEGREES_INCHES, -TURN_90_DEGREES_INCHES, 5.0);
        encoderDrive(0.5,-6,-6,5.0); 
        
        
    }

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

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(speed);
        motorBackLeft.setPower(speed);
        motorFrontRight.setPower(speed);
        motorBackRight.setPower(speed);

        while (opModeIsActive() && (motorFrontLeft.isBusy() && motorFrontRight.isBusy())) {
            telemetry.addData("Path1", "Running to %7d :%7d", newFrontLeftTarget, newFrontRightTarget);
            telemetry.addData("Path2", "Running at %7d :%7d",
                    motorFrontLeft.getCurrentPosition(),
                    motorFrontRight.getCurrentPosition());
            telemetry.update();
        }

        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackRight.setPower(0);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveArm(double power, int targetPosition) {
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(targetPosition);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(power);

        while (opModeIsActive() && armMotor.isBusy()) {
            telemetry.addData("Arm", "Moving to %7d", targetPosition);
            telemetry.update();
        }

        armMotor.setPower(0);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
















