package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class summerCodeOne extends LinearOpMode {
    @Override
    public void runOpMode() {

        DcMotor motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight"); // input 0
        DcMotor motorBackRight = hardwareMap.dcMotor.get("motorBackRight"); // input 1
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft"); // input 2
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");  // input 3

        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor motorArmExtender = hardwareMap.dcMotor.get("motorArmExtender"); // Ex 0
        DcMotor motorIntake = hardwareMap.dcMotor.get("motorIntake"); // Ex 1
        DcMotor motorArm = hardwareMap.dcMotor.get("motorArm"); // Ex 2

        motorFrontLeft.setZeroPowerBehavior(BRAKE);
        motorFrontRight.setZeroPowerBehavior(BRAKE);
        motorBackLeft.setZeroPowerBehavior(BRAKE);
        motorBackRight.setZeroPowerBehavior(BRAKE);

        motorArmExtender.setZeroPowerBehavior(BRAKE);
        motorArm.setZeroPowerBehavior(BRAKE);
        motorIntake.setZeroPowerBehavior(BRAKE);

        Servo servoDroneShooter = hardwareMap.servo.get("ServoDroneShooter"); // servo 0
        Servo servoPixelHolderOne = hardwareMap.servo.get("ServoDroneShooter"); // servo 1
        Servo servoPixelHolderTwo = hardwareMap.servo.get("ServoDroneShooter"); // servo 2

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);

            if (gamepad2.y) {
                servoDroneShooter.setPosition(0);
            } else {
                servoDroneShooter.setPosition(1);
            }

            double extenderPower = gamepad2.right_stick_y;
            if (gamepad2.right_stick_y > 0.1 || gamepad2.right_stick_y < -0.1) {
                motorArmExtender.setPower(extenderPower);
            } else {
                motorArmExtender.setPower(0);
            }
            if (gamepad2.x) {
                motorIntake.setPower(1);
            } else {
                motorIntake.setPower(0);
            }

            if (gamepad2.left_bumper) {
                servoPixelHolderOne.setPosition(0.6);
            } else { servoPixelHolderOne.setPosition(0.5);
            }
            if (gamepad2.right_bumper) {
                servoPixelHolderTwo.setPosition(0.6);
            } else { servoPixelHolderTwo.setPosition(0.5);
            }

            double motorArmPower = gamepad2.left_stick_y;
            if (gamepad2.left_stick_y > 0.1 || gamepad2.right_stick_y < -0.1) {
                motorArm.setPower(motorArmPower);

            }   else {
                motorArm.setPower(0);
            }

        }
    }
}