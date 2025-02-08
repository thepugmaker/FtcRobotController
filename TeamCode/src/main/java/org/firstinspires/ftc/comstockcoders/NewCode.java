package org.firstinspires.ftc.comstockcoders;

import androidx.appcompat.app.WindowDecorActionBar;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "NewCode")
@Disabled
public class NewCode extends LinearOpMode {

    private DcMotor Arm_Motor;
    private Servo MoveClaw;
    private DcMotor Right_Motor;
    private DcMotor Left_Motor;
    private Servo RightClaw;
    private Servo WristClaw;
    private boolean ArmIsPressed;
    private boolean isGrabbing = false;
    private enum State {
        Bucket,
        Grab,
        idle
    }
    private State state;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        Arm_Motor = hardwareMap.get(DcMotor.class, "Arm_Motor");
        MoveClaw = hardwareMap.get(Servo.class, "MoveClaw");
        Right_Motor = hardwareMap.get(DcMotor.class, "Right_Motor");
        Left_Motor = hardwareMap.get(DcMotor.class, "Left_Motor");
        RightClaw = hardwareMap.get(Servo.class, "RightClaw");
        WristClaw = hardwareMap.get(Servo.class, "WristClaw");

        // Init blocks go here
        Left_Motor.setDirection(DcMotor.Direction.REVERSE);
        Arm_Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        isGrabbing = false;
        waitForStart();
        if (opModeIsActive()) {
            MoveClaw.setPosition(0);
            WristClaw.setPosition(1);
            // Run code

            // Main while loop for all op mode action
            while (opModeIsActive()) {
                // Calls all functions in a loop
                Movement();
                GamepadArmMotor();
                SwitchStates();
                // MoveClawMovement();
                // ClawGrab();
                Telemetry2();
                telemetry.update();
            }
        }
    }

    // Controlls at movement
    private void Movement() {
        Left_Motor.setPower((gamepad1.left_stick_y + -gamepad1.right_stick_x) / 1);
        Right_Motor.setPower((gamepad1.left_stick_y - -gamepad1.right_stick_x) / 1);
    }

    // Controlls arm motor for gamepad 2
    private void GamepadArmMotor() {
        if (gamepad2.right_bumper) {
            // Down
            Arm_Motor.setPower(1 / 2);
        } else if (gamepad2.left_bumper) {
            // Up
            Arm_Motor.setPower(-1 / 2);
            MoveClaw.setPosition(0);
            WristClaw.setPosition(0);
        } else {
            // Stops motor
            Arm_Motor.setPower(0);
        }
    }

    // Switches states
    private void SwitchStates() {
        if (gamepad2.a) {
            if (!isGrabbing) {
                state = State.Grab;
                // Down
                MoveClaw.setPosition(0.7);
                // Wrist up
                WristClaw.setPosition(1);
                // Open
                RightClaw.setPosition(0.6);
                isGrabbing = true;
            } else if (isGrabbing) {
                // Close
                RightClaw.setPosition(1);
                sleep(150);
                //if (timer.milliseconds() < 150) {
                state = State.idle;
                idleMode();
                isGrabbing = false;
                //}
            }
        }
        if (gamepad2.y) {
            state = State.Bucket;
            // Up
            MoveClaw.setPosition(0);
            // Wrist up
            WristClaw.setPosition(1);
            sleep(300);
            // Open
            RightClaw.setPosition(0.6);
        }
        if (gamepad2.x) {
            state = State.idle;
            idleMode();
        }
    }

    private void idleMode() {
        if (state == State.idle) {
            // Up
            MoveClaw.setPosition(0);
            // Wrist up
            WristClaw.setPosition(1);
        }
    }

    /*
    Moves the claw pos
    private void MoveClawMovement() {
        if (gamepad2.a) {
            // Down
            MoveClaw.setPosition(1);
        } else if (gamepad2.y) {
            // Up
            MoveClaw.setPosition(0.5);
        }
    } */


    // Main telemetry for robot
    private void Telemetry2() {
        telemetry.addData("Right Motor", Right_Motor.getPower());
        telemetry.addData("Left Motor", Left_Motor.getPower());
        telemetry.addData("Arm Motor", Arm_Motor.getPower());
        telemetry.addData("Servo Wrist", WristClaw.getPosition());
        telemetry.addData("Servo Move", MoveClaw.getPosition());
    }

    /*
    Controls claw grabbing
    private void ClawGrab() {
        if (gamepad2.b) {
            // Close
            RightClaw.setPosition(1);
        } else if (gamepad2.x) {
            // Open
            RightClaw.setPosition(0);
        }
    }   */
}