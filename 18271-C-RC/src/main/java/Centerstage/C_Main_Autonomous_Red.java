//WORK IN PROGRESS new comment/lines have !! make it quicker change potion variable or detectio spin direction
package Centerstage;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

@TeleOp
public class C_Main_Autonomous_Red extends OpMode {
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor motorElbowRight;
    DcMotor motorElbowLeft;
    Servo ServoHandLeft;
    Servo ServoHandRight;
    Servo ServoWrist;
    ColorSensor Color;
    DistanceSensor Distance;
    boolean found = false;
    double position = 0;
    final double DEADBAND = 0.1;
    int handpositionint = 0;
    double wristPosition = 0;
    int elbowdownint = 0;
    int elbowupint = 0;
    ElapsedTime runtime = new ElapsedTime();
    int flag1 = 0;
    int flag2 = 0;
    int flag3 = 0;
    int counter1 = 0;
    int counter2 = 0;
    double arcived_time = 0;
    int i = 0;
    double end_serch_timestamp=0;
    double start_serch_timestamp=0;
    int a = 0;
    double arcived_time2 = 0;
    int start_serch_encoderstamp = 0;
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
        ServoHandLeft = hardwareMap.servo.get("ServoHandLeft");
        ServoHandRight = hardwareMap.servo.get("ServoHandRight");
        ServoWrist = hardwareMap.servo.get("ServoWrist");
        Color = hardwareMap.colorSensor.get("Color");
        Distance = hardwareMap.get(DistanceSensor.class, "Distance");
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        double distanceCM = Distance.getDistance(DistanceUnit.CM);
        telemetry.addData("Coler blue",blue);

        if (flag1 == 0) {
            flag1 = 1;
            arcived_time = runtime.seconds();
        }
        if ((runtime.seconds()-arcived_time) < 2) {
                ServoWrist.setPosition(0);
                ServoHandRight.setPosition(0.1);
                ServoHandLeft.setPosition(0);
            a = motorFrontLeft.getCurrentPosition();
            if(1370 > motorFrontLeft.getCurrentPosition()) {
                motorFrontLeft.setPower(0.55);
            }
            if(1370 > motorBackLeft.getCurrentPosition()) {
                motorBackLeft.setPower(0.55);
            }
            if(1370 > motorFrontRight.getCurrentPosition()) {
                motorFrontRight.setPower(0.4);
            }
            if(1370 > motorBackRight.getCurrentPosition()) {
                motorBackRight.setPower(0.4);
            }
            if(1370 < motorFrontLeft.getCurrentPosition()) {
                motorFrontLeft.setPower(0);
            }
            if(1370 < motorBackLeft.getCurrentPosition()) {
                motorBackLeft.setPower(0);
            }
            if(1370 < motorFrontRight.getCurrentPosition()) {
                motorFrontRight.setPower(0);
            }
            if(1370 < motorBackRight.getCurrentPosition()) {
                motorBackRight.setPower(0);
            }
            telemetry.addData("status3","in loop 1");
            telemetry.addData("found_blue",flag2);
            start_serch_timestamp = runtime.seconds();
            //!!
            start_serch_encoderstamp = motorFrontLeft.getCurrentPosition();
            
        }
        if(distanceCM < 10){
            found = true;
        }
        if(runtime.seconds()-start_serch_timestamp>10){
            found = true;
        }
        if ((runtime.seconds()-arcived_time) > 2) {
            telemetry.addData("status3","sucsess2");
            telemetry.addData("status4",found);
            if(found == false){
                motorFrontLeft.setPower(-0.55);
                motorBackLeft.setPower(-0.55);
                motorFrontRight.setPower(0.4);
                motorBackRight.setPower(0.4);
                ServoWrist.setPosition(0);
                ServoHandRight.setPosition(0.1);
                ServoHandLeft.setPosition(0);
                
            }
            if(found == true){
                if(flag2==0){
                    end_serch_timestamp = runtime.seconds();
                    flag2 = 1;
                    motorFrontLeft.setPower(0);
                    motorBackLeft.setPower(0);
                    motorFrontRight.setPower(0);
                    motorBackRight.setPower(0);
                }
                telemetry.addData("found_blue",(end_serch_timestamp-start_serch_timestamp));
                if(end_serch_timestamp<runtime.seconds()){
                    if(end_serch_timestamp+1.8>runtime.seconds()){
                    //expariment with different positions!!
                        if(motorElbowRight.getCurrentPosition()>0){
                            motorElbowRight.setPower(0);
                            motorElbowLeft.setPower(0);
                            ServoHandLeft.setPosition(0.1);
                        }
                        if(motorElbowRight.getCurrentPosition()<0){
                            motorElbowRight.setPower(1);
                            motorElbowLeft.setPower(-1);
                            telemetry.addData("armpostions2",motorElbowRight.getCurrentPosition());
                        }
                        ServoWrist.setPosition(0.153);
                    }
                }
                if(end_serch_timestamp+1.8<runtime.seconds()) {
                    if(end_serch_timestamp+3.6>runtime.seconds()) {
                        motorElbowRight.setPower(-0.5);
                        motorElbowLeft.setPower(0.5);
                        ServoWrist.setPosition(0.75);
                        ServoHandRight.setPosition(0.1);
                    }
                }
            }    
        }
        telemetry.addData("status","in loop");
        telemetry.addData("status1",runtime.seconds());
        telemetry.addData("status2",runtime.seconds()-arcived_time);
        telemetry.addData("status4",a);
        telemetry.addData("status5",(runtime.seconds()-arcived_time));
        telemetry.addData("DistanceCM",distanceCM);
    }
}
