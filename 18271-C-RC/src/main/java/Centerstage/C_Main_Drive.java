package Centerstage;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class C_Main_Drive extends OpMode {
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorElbowRight;
    DcMotor motorElbowLeft;
    DcMotor motorHangRight;
    DcMotor motorHangLeft;
    Servo ServoHandLeft;
    Servo ServoHandRight;
    Servo ServoWrist;
    Servo ServoLauncher;
    final double DEADBAND = 0.1;
    double wristPosition = 0;
    double Launch = 0;
    double handleftposition = 0;
    double handrightposition = 0;
    int elbowdownint = 0;
    int elbowupint = 0;
    ElapsedTime runtime = new ElapsedTime();
    int flag1 = 0;
    int flag2 = 0;
    int counter1 = 0;
    int counter2 = 0;
    double arcived_time = 0;
    int i = 0;
    double hangint = 0;

    @Override
    public void init() {
        // Declare our motors
        // Make sure your ID's match your configuration
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        motorElbowRight= hardwareMap.dcMotor.get("motorElbowRight");
        motorElbowLeft= hardwareMap.dcMotor.get("motorElbowLeft");
        motorHangRight = hardwareMap.dcMotor.get("motorHangRight");
        motorHangLeft = hardwareMap.dcMotor.get("motorHangLeft");
        ServoHandLeft = hardwareMap.servo.get("ServoHandLeft");
        ServoHandRight = hardwareMap.servo.get("ServoHandRight");
        ServoWrist = hardwareMap.servo.get("ServoWrist");
        ServoLauncher = hardwareMap.servo.get("ServoLauncher");
        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorElbowRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorElbowLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorHangRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorHangLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }
    
    @Override
    public void loop() {
        double wristup = gamepad1.left_trigger;
        double wristdown = gamepad1.right_trigger;
        boolean handleft = gamepad1.left_bumper;
        boolean handright = gamepad1.right_bumper;
        boolean elbowup = gamepad1.y;
        boolean elbowdown = gamepad1.a;
        boolean xsyclepickup = gamepad1.x;
        boolean bsycleplace = gamepad1.b;
        double y = gamepad1.left_stick_y; // Remember, this is reversed!
        double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = -gamepad1.right_stick_x;
        boolean y_button_lancher = gamepad2.y;
        boolean x_button_lancher = gamepad2.x;
        boolean uphang = gamepad2.a;
        boolean Downhang = gamepad2.b;
        if(y_button_lancher == true){
            Launch = 1;
        }
        if(x_button_lancher == true){
            Launch = 0;
        }
        if(uphang == true){
            hangint = 1;
        };
        if(Downhang == true){
            hangint = -0.3;
        };
        if(uphang == false){
            if(Downhang == false){
                hangint = 0;
            }
        };
        if(flag1 == 0) {
          if(wristup > 0.1) {
            flag1 = 1;
            double arcived_time = runtime.seconds();
            }
        }
        if(flag1 == 1){
            if(wristup >= 0.1) {
                if((arcived_time - runtime.seconds()) < -0.01){
                    i = i+1;
                    wristPosition = wristPosition + (wristup * 0.01);
                    counter1 = counter1 + 1;
                    arcived_time = runtime.seconds();
                    
                }
            }
        }
        if(flag2 == 0) {
            if(wristdown >= 0.1) {
                flag2 = 1;
                double arcived_time1 = runtime.seconds();
            }
        }
        if(flag2 == 1){
            if(wristdown >= 0.1) {
                if((arcived_time - runtime.seconds()) < -0.01){
                    wristPosition = wristPosition - (wristdown) * 0.01;
                    counter2 = counter2 + 1;
                    arcived_time = runtime.seconds();
                }
            }
        }
        if(wristPosition > 1){
          wristPosition = 1;
        };
        if(wristPosition < 0){
          wristPosition = 0;
        };
        if(elbowup == true){
            elbowupint = 1;
        };
        if(elbowdown  == true){
            elbowdownint = -1;
        };
        if(elbowdown  == false){
            elbowdownint = 0;
        };
        if(elbowup  == false){
            elbowupint = 0;
        };
        
        if(handleft == true){
            handleftposition = 0.1;
        };
        if(handleft == false){
            handleftposition = 0;
        };
        if(handright == true){
            handrightposition = 0;
        };
        if(handright == false){
            handrightposition = 0.1;
        };
        telemetry.update();
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;
        //double wristPosition = (wristup - wristdown);
        int elbowpower = (elbowupint + elbowdownint);
        if(bsycleplace == true) {
            if(motorElbowRight.getCurrentPosition()>-190){
                telemetry.addData("armpostions",motorElbowRight.getCurrentPosition());
                motorElbowRight.setPower(-1);
                motorElbowLeft.setPower(1);
            }
            if(motorElbowRight.getCurrentPosition()<-190){
                motorElbowRight.setPower(1);
                motorElbowLeft.setPower(-1);
                telemetry.addData("armpostions2",motorElbowRight.getCurrentPosition());
            }
            wristPosition = 0.153;
        }
        if(xsyclepickup == true) {
            if(motorElbowRight.getCurrentPosition()>0){
                telemetry.addData("armpostions",motorElbowRight.getCurrentPosition());
                motorElbowRight.setPower(-1);
                motorElbowLeft.setPower(1);
            }
            if(motorElbowRight.getCurrentPosition()<0){
                motorElbowRight.setPower(1);
                motorElbowLeft.setPower(-1);
                telemetry.addData("armpostions2",motorElbowRight.getCurrentPosition());
            }
            wristPosition = 0.153;
        }
        ServoWrist.setPosition(wristPosition);
        ServoHandLeft.setPosition(handleftposition);
        ServoHandRight.setPosition(handrightposition);
        ServoLauncher.setPosition(Launch);
        motorFrontLeft.setPower(-frontLeftPower*0.75);
        motorBackLeft.setPower(-backLeftPower*0.75);
        motorFrontRight.setPower(-frontRightPower*0.75);
        motorBackRight.setPower(-backRightPower*0.75);
        motorElbowRight.setPower(-elbowpower);
        motorElbowLeft.setPower(elbowpower);
        motorHangLeft.setPower(hangint);
        motorHangRight.setPower(hangint);
        telemetry.addData("counter",i);
        telemetry.addData("status2",(arcived_time-runtime.seconds()));
        telemetry.addData("status1",wristPosition);
        telemetry.addData("status",wristup*0.1);
        telemetry.addData("armpostion",motorElbowRight.getCurrentPosition());
        telemetry.addData("hand",handrightposition);
        
    }
}

