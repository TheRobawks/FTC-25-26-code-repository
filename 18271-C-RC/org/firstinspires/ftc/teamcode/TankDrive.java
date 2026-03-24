package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TankDrive (Blocks to Java)")
public class TankDrive extends LinearOpMode {

  private DcMotor moter2;
  private DcMotor motor1;

  /**
   * This OpMode offers Tank Drive style TeleOp control for a direct drive robot.
   *
   * In this Tank Drive mode, the left and right joysticks (up
   * and down) drive the left and right motors, respectively.
   */
  @Override
  public void runOpMode() {
    moter2 = hardwareMap.get(DcMotor.class, "moter2");
    motor1 = hardwareMap.get(DcMotor.class, "motor1");

    // Reverse one of the drive motors.
    // You will have to determine which motor to reverse for your robot.
    // In this example, the right motor was reversed so that positive
    // applied power makes it move the robot in the forward direction.
    moter2.setDirection(DcMotor.Direction.REVERSE);
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        // The Y axis of a joystick ranges from -1 in its topmost position to +1 in its bottommost position.
        // We negate this value so that the topmost position corresponds to maximum forward power.
        motor1.setPower(gamepad1.left_stick_y);
        moter2.setPower(gamepad1.right_stick_y);
        if (gamepad1.left_bumper) {
          motor1.setPower(1);
        }
        if (gamepad1.right_bumper) {
          moter2.setPower(1);
        }
        if (gamepad1.left_trigger != 0) {
          motor1.setPower(-gamepad1.left_trigger);
        }
        if (gamepad1.right_trigger != 0) {
          moter2.setPower(-gamepad1.right_trigger);
        }
        telemetry.addData("Left Pow", motor1.getPower());
        telemetry.addData("Right Pow", moter2.getPower());
        telemetry.update();
      }
    }
  }
}
