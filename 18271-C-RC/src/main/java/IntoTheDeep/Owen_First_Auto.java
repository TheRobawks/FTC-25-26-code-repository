package IntoTheDeep;

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

@TeleOp

public class Owen_First_Auto extends OpMode {
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    ElapsedTime runtime = new ElapsedTime();
    double arcived_time = 0;
    int flag = 0;
    
    public void init() {
        
        // Declare our motors
        // Make sure your ID's match your configuration
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        
        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        
    }
    @Override
    public void loop() {
        //make sure if's have ==
        if(flag==0){
            arcived_time = runtime.seconds();
            flag = 1;
        }
        if(runtime.seconds()-arcived_time<0.75){
            motorFrontLeft.setPower(1);
            motorBackLeft.setPower(1);
            motorFrontRight.setPower(-1);
            motorBackRight.setPower(-1);
        }
        if(runtime.seconds()-arcived_time>0.75){
            motorFrontLeft.setPower(1);
            motorBackLeft.setPower(1);
            motorFrontRight.setPower(1);
            motorBackRight.setPower(1);
        }
        telemetry.addData("frontleftPower", arcived_time);
        telemetry.addData("backleftPower", runtime.seconds());
    }
    
}
