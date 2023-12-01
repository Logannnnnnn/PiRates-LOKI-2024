package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Robot Centric Drive (Scorpion)")
public class RearCentricDrive extends LinearOpMode {

  private Servo leftGrip;
  private Servo rightGrip;
  private DcMotor armRotate;
  private DcMotor armExt;
  private DcMotor frontLeftMotor;
  private DcMotor backLeftMotor;
  private DcMotor frontRightMotor;
  private DcMotor backRightMotor;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {

    //Initialize Variables
    //Tracks rotation of the arm's motor
    int rotation;
    //Tracks the extension of the arm
    int ext;
    //X and Y values of stick inputs to compile drive outputs
    double yI;
    double xI;
    //Value for armLock
    boolean armLocked;

    leftGrip = hardwareMap.get(Servo.class, "leftGrip");
    rightGrip = hardwareMap.get(Servo.class, "rightGrip");
    armRotate = hardwareMap.get(DcMotor.class, "armRotate");
    armExt = hardwareMap.get(DcMotor.class, "armExt");
    frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
    backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
    frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
    backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

    //Sets initial positions and directions for grip servos
    leftGrip.setDirection(Servo.Direction.REVERSE);
    rightGrip.setDirection(Servo.Direction.FORWARD);

    //Sets variables to 0 on initialization
    rotation = 0;
    ext = 0;
    armLocked = false;
    waitForStart();

    if (opModeIsActive()) {

      //Sets behaviors and modes for motors
        //ArmExtension and ArmRotate are set to brake when receiving zero power
        //Arm Extension is set to run using encoder outputs and inputs
      armRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      armRotate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      armExt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      armExt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
      frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

      while (opModeIsActive()) {

        //Assigns variables to inputs
          //Ext and Rotation are set to receive inputs from encoders
        ext = armExt.getCurrentPosition();
        rotation = armRotate.getCurrentPosition();
        //Multipliers are applied to X and Y and they are tied to sticks on the gamepads
        yI = gamepad1.left_stick_y;
        xI = gamepad1.right_stick_x;

        //Sets power for driving wheels
        //low power mode
        if(gamepad1.x){
            if(yI > 0.2 || yI < -0.2){
            backLeftMotor.setPower(((yI-0.1)*-1)*0.25);
            backRightMotor.setPower(yI*0.25);
            frontLeftMotor.setPower(((yI-0.1)*-1)*0.25);
            frontRightMotor.setPower(yI*0.25);
          } else if (xI > 0.2) {
            backLeftMotor.setPower((xI-0.1)*0.25);
            backRightMotor.setPower(xI*0.25);
            frontLeftMotor.setPower((xI-0.1)*0.25);
            frontRightMotor.setPower(xI*0.25);
          } else if (xI < -0.2) {
            backLeftMotor.setPower((xI-0.1)*0.25);
            backRightMotor.setPower(xI*0.25);
            frontLeftMotor.setPower((xI-0.1)*0.25);
            frontRightMotor.setPower(xI*0.25);
          } else {
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
          }
        }else {
          //normal driving
          backLeftMotor.setPower(-yI+xI);
          backRightMotor.setPower(yI+xI);
          frontLeftMotor.setPower(-yI+xI);
          frontRightMotor.setPower(yI+xI);
        }

        //Arm rotation controls
          //Rotates up when Right Bumper is pressed
          //Rotates down when Left Bumper is pressed
          //Otherwise power is set to 0 (BRAKE)
          
        //if(armLocked == false){
          if (gamepad2.right_bumper) {
            armRotate.setPower(0.3);
          } else if (gamepad2.left_bumper) {
            armRotate.setPower(-0.2);
          } else {
            armRotate.setPower(0);
          }
        //}else if (armLocked == true){
         //if(armRotate.getCurrentPosition()<1200){
           //armRotate.setPower(0.3);
         //} else{
           //armRotate.setPower(0);
         //}
        //}
          
        
        
      
        if(armLocked == false){
          if(gamepad2.y){
            armLocked = true;
          }
        } else if (armLocked == true){
          if(gamepad2.x){
            armLocked = false;
          }
        }

        //Arm extension controls
          //Moves up when X is pressed
          //Moves down when B is pressed
          //Otherwise set power to 0 (BRAKE)
        //Limiters are applied if the motors position is less than 350° or greater than 2900°
          //If the position is less than 350°, the arm can only extend forward
          //If the position is greater than 2900°, the arm can only retract
        if (ext > 350 && ext < 2900) {
          if (gamepad2.dpad_up) {
            armExt.setPower(1);
          } else if (gamepad2.dpad_down) {
            armExt.setPower(-1);
          } else {
            armExt.setPower(0);
          }
        } else if (ext < 350 && ext < 2900) {
          if (gamepad2.dpad_up) {
            armExt.setPower(1);
          } else {
            armExt.setPower(0);
          }
        } else if (ext > 2900 && ext > 350) {
          if (gamepad2.dpad_down) {
            armExt.setPower(-1);
          } else {
            armExt.setPower(0);
          }
        } if(gamepad2.right_trigger > 0.5 && gamepad2.left_trigger > 0.5){
          armExt.setPower(-1);
          sleep(1000000);
        }

        //Inputs for claw grip
          //When A is pressed, the claw releases its grip
          //When Y is pressed, the claw moves to a full grip position
        if (gamepad2.a) {
          leftGrip.setPosition(0.75);
          rightGrip.setPosition(0.75);
        } else if (gamepad2.b) {
          leftGrip.setPosition(1);
          rightGrip.setPosition(1);
        }



        //Telemetry for debugging
        telemetry.addData("Current Arm Extension", ext);
        telemetry.addData("Current Arm Rotation", armRotate.getCurrentPosition());
        telemetry.addData("Arm lift Power", armRotate.getPower());
        telemetry.addData("Right Wheel Power", backRightMotor.getPower());
        telemetry.addData("Left Wheel Power", backLeftMotor.getPower());
        telemetry.addData("Arm Locked: ", armLocked);
        telemetry.update();
      }
    }
  }
}
