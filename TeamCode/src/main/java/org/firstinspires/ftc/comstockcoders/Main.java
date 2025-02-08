package org.firstinspires.ftc.comstockcoders;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Main")
public class Main extends LinearOpMode {

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
            // Run code
            MoveClaw.setPosition(0);
            WristClaw.setPosition(1);

            // Main while loop for all op mode action
            while (opModeIsActive()) {
                // Calls all functions in a loop
                Movement();
                GamepadArmMotor();
                SwitchStates();
                Telemetry2();
                telemetry.update();
            }
        }
    }

    // Controlls at movement
    private void Movement() {
        Left_Motor.setPower((gamepad1.left_stick_y + -gamepad1.right_stick_x) / 2);
        Right_Motor.setPower((gamepad1.left_stick_y - -gamepad1.right_stick_x) / 2);
    }

    // Controlls arm motor for gamepad 2
    private void GamepadArmMotor() {
        if (gamepad2.right_bumper) {
            // Down
            Arm_Motor.setPower(1);
        } else if (gamepad2.left_bumper) {
            // Up
            Arm_Motor.setPower(-1);
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
                // down
                MoveClaw.setPosition(0.7);
                // Wrist up
                WristClaw.setPosition(0);
                sleep(300);
                // Open
                RightClaw.setPosition(0.6);
                isGrabbing = true;
            } else if (isGrabbing) {
                // Close
                RightClaw.setPosition(1);
                sleep(150);
                isGrabbing = false;
                state = State.idle;
                idleMode();
            }
        }
        if (gamepad2.y) {
            state = State.Bucket;
            // Up
            MoveClaw.setPosition(0);
            // Wrist up
            WristClaw.setPosition(0);
            sleep(300);
            // Open
            RightClaw.setPosition(0.6);
        }
        if (gamepad2.x) {
            state = State.idle;
            idleMode();
        }
    }

    // Idle mode for setting it to idle mode for driving or moving
    private void idleMode() {
        if (state == State.idle) {
            // Up
            MoveClaw.setPosition(0);
            // Wrist up
            WristClaw.setPosition(1);
        }
    }

    // Main telemetry for robot
    private void Telemetry2() {
        telemetry.addData("Right Motor", Right_Motor.getPower());
        telemetry.addData("Left Motor", Left_Motor.getPower());
        telemetry.addData("Arm Motor", Arm_Motor.getPower());
        telemetry.addData("Servo Wrist", WristClaw.getPosition());
        telemetry.addData("Servo Move", MoveClaw.getPosition());
    }
}