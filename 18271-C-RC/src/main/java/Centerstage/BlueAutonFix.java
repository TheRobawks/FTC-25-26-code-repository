package Centerstage;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp
public class BlueAutonFix extends OpMode {
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorElbowRight;
    DcMotor motorElbowLeft;
    Servo ServoHand;
    ColorSensor Color;
    boolean found = false;
    final double DEADBAND = 0.1;
    int handpositionint = 0;
    double wristPosition = 0;
    int elbowdownint = 0;
    int elbowupint = 0;
    ElapsedTime runtime = new ElapsedTime();
    int flag1 = 0;
    int flag2 = 0;
    int counter1 = 0;
    int counter2 = 0;
    double arcived_time = 0;
    int i = 0;
    double end_serch_timestamp=0;
    double start_serch_timestamp=0;

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
        ServoHand = hardwareMap.servo.get("ServoHand");
        Color = hardwareMap.colorSensor.get("Color");
        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorElbowRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorElbowLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }
    
    @Override
    public void loop() {
        double red = Color.red();
        double green = Color.green();
        double blue = Color.blue();
        telemetry.addData("Coler blue",blue);
        
        if (flag1 == 0) {
            flag1 = 1;
            telemetry.addData("status3","sucsess");
            arcived_time = runtime.seconds();
            ServoHand.setPosition(1);
        }
        if ((runtime.seconds()-arcived_time) < 1.3) {
            motorFrontLeft.setPower(0.6);
            motorBackLeft.setPower(0.6);
            motorFrontRight.setPower(0.5);
            motorBackRight.setPower(0.5);
            telemetry.addData("status3","in loop 1");
            telemetry.addData("found_blue",flag2);
            start_serch_timestamp = runtime.seconds();
        }
        if(blue > 100){
            found = true;
        }
        if(runtime.seconds()-start_serch_timestamp>10){
            found = true;
        }
        if ((runtime.seconds()-arcived_time) > 1.3) {
            telemetry.addData("status3","sucsess2");
            telemetry.addData("status4",found);
            if(found == false){
                motorFrontLeft.setPower(-0.5);
                motorBackLeft.setPower(-0.5);
                motorFrontRight.setPower(0.5);
                motorBackRight.setPower(0.5);
            }
            if(found == true){
                if(flag2==0){
                    end_serch_timestamp = runtime.seconds();
                    flag2 = 1;
                }
                telemetry.addData("found_blue",(end_serch_timestamp-start_serch_timestamp));
                if(end_serch_timestamp+0.2>runtime.seconds()){
                    motorFrontLeft.setPower(0.6);
                    motorBackLeft.setPower(0.6);
                    motorFrontRight.setPower(0.5);
                    motorBackRight.setPower(0.5);
                }
                if(end_serch_timestamp+0.2<runtime.seconds()){
                    if(end_serch_timestamp+0.6>runtime.seconds()){
                        if(end_serch_timestamp-start_serch_timestamp<1){
                    
                            motorFrontLeft.setPower(0);
                            motorBackLeft.setPower(0);
                            motorFrontRight.setPower(0);
                            motorBackRight.setPower(0);
                        }
                        if(end_serch_timestamp-start_serch_timestamp>3){
                            if(end_serch_timestamp-start_serch_timestamp<10){
                                motorFrontLeft.setPower(0.5);
                                motorBackLeft.setPower(0.5);
                                motorFrontRight.setPower(-0.5);
                                motorBackRight.setPower(-0.5);
                            }
                        }
                        if(end_serch_timestamp-start_serch_timestamp>10){
                            motorFrontLeft.setPower(0);
                            motorBackLeft.setPower(0);
                            motorFrontRight.setPower(0);
                            motorBackRight.setPower(0);
                        }
                        if(end_serch_timestamp-start_serch_timestamp<3){
                            if(end_serch_timestamp-start_serch_timestamp>1){
                                motorFrontLeft.setPower(-0.6);
                                motorBackLeft.setPower(-0.6);
                                motorFrontRight.setPower(-0.5);
                                motorBackRight.setPower(-0.5);
                            }
                        }
                    }
                }
                if(end_serch_timestamp+0.6<runtime.seconds()){
                    if(end_serch_timestamp+1.1>runtime.seconds()){
                        if(end_serch_timestamp+0.8>runtime.seconds()){
                                motorFrontLeft.setPower(0.5);
                                motorBackLeft.setPower(0.5);
                                motorFrontRight.setPower(0.5);
                                motorBackRight.setPower(0.5);
                        }
                        if(end_serch_timestamp+0.8<runtime.seconds()){
                            if(end_serch_timestamp-start_serch_timestamp>3){
                                motorFrontLeft.setPower(-0.5);
                                motorBackLeft.setPower(-0.5);
                                motorFrontRight.setPower(-0.5);
                                motorBackRight.setPower(-0.5);
                            }
                        }
                        if(end_serch_timestamp-start_serch_timestamp<3){
                            motorFrontLeft.setPower(0);
                            motorBackLeft.setPower(0);
                            motorFrontRight.setPower(0);
                            motorBackRight.setPower(0);
                        }
                       
                    }
                }
                if(end_serch_timestamp+1<runtime.seconds()){
                    if(end_serch_timestamp+1.1+end_serch_timestamp-start_serch_timestamp>runtime.seconds()){
                        motorFrontLeft.setPower(0.5);
                        motorBackLeft.setPower(0.5);
                        motorFrontRight.setPower(-0.5);
                        motorBackRight.setPower(-0.5);
                    }
                }
                if(end_serch_timestamp+1.1+end_serch_timestamp-start_serch_timestamp<runtime.seconds()){
                    if(end_serch_timestamp+2.3+end_serch_timestamp-start_serch_timestamp>runtime.seconds()){
                        motorFrontLeft.setPower(-0.5);
                        motorBackLeft.setPower(-0.5);
                        motorFrontRight.setPower(-0.4);
                        motorBackRight.setPower(-0.4);
                    }
                }
                double simlify = end_serch_timestamp+2.3+end_serch_timestamp-start_serch_timestamp;
                if(end_serch_timestamp+2.3+end_serch_timestamp-start_serch_timestamp<runtime.seconds()){
                    if(simlify+1.3>runtime.seconds()){
                        motorFrontLeft.setPower(-0.5);
                        motorBackLeft.setPower(-0.5);
                        motorFrontRight.setPower(0.5);
                        motorBackRight.setPower(0.5);
                    }
                }
                if(simlify+1.3<runtime.seconds()){
                    if(simlify+4.3>runtime.seconds()){
                        motorFrontLeft.setPower(0.6);
                        motorBackLeft.setPower(0.6);
                        motorFrontRight.setPower(0.5);
                        motorBackRight.setPower(0.5);
                    }
                }
                if(simlify+4.3<runtime.seconds()){
                    motorFrontLeft.setPower(0);
                    motorBackLeft.setPower(0);
                    motorFrontRight.setPower(0);
                    motorBackRight.setPower(0);
                }
            }
        }
        telemetry.addData("status","in loop");
        telemetry.addData("status1",runtime.seconds());
        telemetry.addData("status2",runtime.seconds()-arcived_time);
    }
}
