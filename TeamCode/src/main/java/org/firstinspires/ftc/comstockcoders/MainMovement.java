package org.firstinspires.ftc.comstockcoders;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MainMovement")
public class MainMovement extends LinearOpMode {

    private DcMotor Arm_Motor;
    private Servo MoveClaw;
    private DcMotor Right_Motor;
    private DcMotor Left_Motor;
    private Servo RightClaw;
    private Servo WristClaw;

    /**
     * Describe this function...
     */
    private void GamepadArmMotor() {
        if (gamepad2.right_bumper) {
            Arm_Motor.setPower(1 / 2);
        } else if (false) {
            Arm_Motor.setPower(-1 / 2);
            MoveClaw.setPosition(0);
        } else {
            Arm_Motor.setPower(0);
        }
    }

    /**
     * Describe this function...
     */
    private void MoveClawMovement() {
        if (gamepad2.a) {
            MoveClaw.setPosition(1);
        } else if (gamepad2.y) {
            MoveClaw.setPosition(0);
        }
    }

    /**
     * Describe this function...
     */
    private void Telemetry2() {
        telemetry.addData("Right Motor", Right_Motor.getPower());
        telemetry.addData("Left Motor", Left_Motor.getPower());
        telemetry.addData("Arm Motor", Arm_Motor.getPower());
        telemetry.addData("Arm CurrentPos", Arm_Motor.getCurrentPosition());
    }

    /**
     * Describe this function...
     */
    private void ClawGrab() {
        if (gamepad2.b) {
            RightClaw.setPosition(0.6);
        } else if (gamepad2.x) {
            RightClaw.setPosition(1);
        }
    }

    private void WristMove() {
        if (gamepad2.right_trigger >= 0.1) {
            WristClaw.setPosition(1);
        } else if (gamepad2.left_trigger >= 0.1) {
            WristClaw.setPosition(0);
        }
    }

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        Arm_Motor = hardwareMap.get(DcMotor.class, "Arm_Motor");
        MoveClaw = hardwareMap.get(Servo.class, "MoveClaw");
        Right_Motor = hardwareMap.get(DcMotor.class, "Right_Motor");
        Left_Motor = hardwareMap.get(DcMotor.class, "Left_Motor");
        RightClaw = hardwareMap.get(Servo.class, "RightClaw");
        WristClaw = hardwareMap.get(Servo.class, "WristClaw");

        // Put initialization blocks here.
        Arm_Motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Left_Motor.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        if (opModeIsActive()) {
            MoveClaw.setPosition(0);
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                Movement();
                GamepadArmMotor();
                MoveClawMovement();
                ClawGrab();
                ArmSlowPower();
                Telemetry2();
                telemetry.update();
            }
        }
    }

    /**
     * Describe this function...
     */
    private void Movement() {
        Left_Motor.setPower((gamepad1.left_stick_y + -gamepad1.right_stick_x) / 1);
        Right_Motor.setPower((gamepad1.left_stick_y - -gamepad1.right_stick_x) / 1);
    }

    /**
     * Describe this function...
     */
    private void ArmSlowPower() {
        int ArmSlowPower2;

        ArmSlowPower2 = 1;
    }
}