package Centerstage;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

@TeleOp

public class SensorTester extends OpMode {
    ColorSensor Color;
        DistanceSensor Distance;
    
    // todo: write your code here
    @Override
    public void init() {
        
        Color = hardwareMap.colorSensor.get("Color");
        Distance = hardwareMap.get(DistanceSensor.class, "Distance");
    }
    @Override
    public void loop() {
        double red = Color.red();
        double green = Color.green();
        double blue = Color.blue();
        double distanceCM = Distance.getDistance(DistanceUnit.CM);
        telemetry.addData("Coler blue",blue);
        telemetry.addData("Coler red",red);
        telemetry.addData("Coler green",green);
        telemetry.addData("DistanceCM",distanceCM);
    }
}
