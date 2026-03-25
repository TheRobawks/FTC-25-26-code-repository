// package Centerstage;
// 
// import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
// import com.qualcomm.robotcore.eventloop.opmode.OpMode;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.hardware.DcMotorSimple;
// import com.qualcomm.robotcore.hardware.Servo;
// import com.qualcomm.robotcore.util.ElapsedTime;
// 
// @TeleOp(name="ArmCode2", group="TeleOp")
// public class OldArmCode extends OpMode {
// 
//     private ElapsedTime runtime = new ElapsedTime();
//     private DcMotor motorElbowLeft;
//     private DcMotor motorElbowRight;
//     private Servo ServoWrist;
// 
//     private boolean manualMode = false;
//     private final double armManualDeadband = 0.03;
//     private final double gripperClosedPosition = 1.0;
//     private final double gripperOpenPosition = 0.5;
//     private final double wristUpPosition = 1.0;
//     private final double wristDownPosition = 0.0;
//     private final int armHomePosition = 0;
//     private final int armIntakePosition = 10;
//     private final int armScorePosition = 600;
//     private final int armShutdownThreshold = 5;
// 
//     private DcMotor motorFrontLeft;
//     private DcMotor motorBackLeft;
//     private DcMotor motorFrontRight;
//     private DcMotor motorBackRight;
//     private final double DEADBAND = 0.1;
// 
//     @Override
//     public void init() {
//         // Initialize hardware here (motors, servos, etc.)
//         // Make sure your ID's match your configuration
//         motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
//         motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
//         motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
//         motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
//         ServoWrist = hardwareMap.servo.get("ServoWrist");
// 
//         // Reverse the right side motors
//         // Reverse left motors if you are using NeveRests
//         motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//         motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
//         motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
// 
//         int wristPositionInt = 0;
// 
//         telemetry.addData("Status", "Initialized");
// 
//         motorElbowLeft = hardwareMap.get(DcMotor.class, "motorElbowLeft");
//         motorElbowRight = hardwareMap.get(DcMotor.class, "motorElbowRight");
//         ServoWrist = hardwareMap.get(Servo.class, "wrist");
// 
//         motorElbowLeft.setDirection(DcMotor.Direction.FORWARD);
//         motorElbowRight.setDirection(DcMotor.Direction.REVERSE);
//         motorElbowLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         motorElbowRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         motorElbowLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//         motorElbowRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//         motorElbowLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//         motorElbowRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//         motorElbowLeft.setPower(0.0);
//         motorElbowRight.setPower(0.0);
// 
//         telemetry.addData("Status", "Initialized");
//     }
// 
//     @Override
//     public void start() {
//         runtime.reset();
// 
//         motorElbowLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         motorElbowRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//         motorElbowLeft.setTargetPosition(armHomePosition);
//         motorElbowRight.setTargetPosition(armHomePosition);
//         motorElbowLeft.setPower(1.0);
//         motorElbowRight.setPower(1.0);
//         motorElbowLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//         motorElbowRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//     }
// 
//     @Override
//     public void loop() {
//         // Your teleop code here
//         double wristUp = gamepad1.left_bumper ? 1.0 : 0.0;
//         double wristDown = gamepad1.right_bumper ? 1.0 : 0.0;
//         boolean handUp = gamepad1.left_trigger > 0.5;
//         boolean handDown = gamepad1.right_trigger > 0.5;
// 
//         // Rest of your code...
//     }
// 
//     @Override
//     public void stop() {
//         // Cleanup and stop the robot
//     }
// }
// 
// 
// 
