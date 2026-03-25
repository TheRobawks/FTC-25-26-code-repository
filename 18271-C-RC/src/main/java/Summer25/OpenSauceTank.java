package Summer25;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "OpenSauceTank")
public class OpenSauceTank extends LinearOpMode {

  private DcMotor motor1;
  private DcMotor moter2;

  /**
   * This OpMode offers Tank Drive style TeleOp control for a direct drive robot.
   *
   * In this Tank Drive mode, the left and right   (up
   * and down) drive the left and right motors, respectively.
   */
  @Override
  public void runOpMode() {
    motor1 = hardwareMap.get(DcMotor.class, "motor1");
    moter2 = hardwareMap.get(DcMotor.class, "moter2");

    // Reverse one of the drive motors.
    // You will have to determine which motor to reverse for your robot.
    // In this example, the right motor was reversed so that positive
    // applied power makes it move the robot in the forward direction.
    motor1.setDirection(DcMotor.Direction.REVERSE);
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        // The Y axis of a joystick ranges from -1 in its topmost position to +1 in its bottommost position.
        // We negate this value so that the topmost position corresponds to maximum forward power.
        moter2.setPower(-gamepad1.left_stick_y);
        motor1.setPower(-gamepad1.right_stick_y);
        telemetry.addData("Left Pow", moter2.getPower());
        telemetry.addData("Right Pow", motor1.getPower());
        telemetry.update();
      }
    }
  }
}