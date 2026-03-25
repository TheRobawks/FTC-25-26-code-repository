package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="HangV2", group="TeleOp")
public class HangV2 extends OpMode {

    private DcMotor motorHangRight;
    private DcMotor motorHangLeft;

    @Override
    public void init() {
        motorHangRight = hardwareMap.get(DcMotor.class, "motorHangRight");
        motorHangLeft = hardwareMap.get(DcMotor.class, "motorHangLeft");

        motorHangRight.setDirection(DcMotor.Direction.REVERSE);
        motorHangLeft.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop() {
        double power = gamepad1.right_stick_y; // Use right stick of gamepad2 for control

        motorHangRight.setPower(power);
        motorHangLeft.setPower(power);
    }
}
