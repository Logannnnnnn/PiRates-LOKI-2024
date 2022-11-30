package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TheSebastianSpecial extends OpMode {

    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;

    // DcMotor armMotor;
    // Servo coneClaw;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");
        
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FrontLeft");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FrontRight");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BackLeft");
        backRightMotor = hardwareMap.get(DcMotor.class, "BackRight");
        
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        // armMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        // coneClaw = hardwareMap.get(Servo.class, "Claw");

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        int  precision = (gamepad1.right_bumper) ? 3: 1;

        double leftStick = gamepad1.left_stick_y;
        double rightStick = gamepad1.right_stick_y;
        double rightTrigger = gamepad1.right_trigger;
        double leftTrigger = gamepad1.left_trigger;

        double FLeft = 0;
        double FRight = 0;
        double BLeft = 0;
        double BRight = 0;

        if(leftStick != 0 || rightStick != 0) {
            FLeft  = -leftStick;
            FRight = -rightStick;
            BLeft = FLeft;
            BRight = FRight;
        }
        else {
            FLeft = rightTrigger - leftTrigger;
            FRight = leftTrigger - rightTrigger;
            BRight = rightTrigger - leftTrigger;
            BLeft = leftTrigger - rightTrigger;
        }
        frontLeftMotor.setPower(FLeft/precision);
        backLeftMotor.setPower(BLeft/precision);
        frontRightMotor.setPower(FRight/precision);
        backRightMotor.setPower(BRight/precision);
    }
}
