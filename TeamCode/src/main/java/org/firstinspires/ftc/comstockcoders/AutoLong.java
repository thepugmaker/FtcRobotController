package org.firstinspires.ftc.comstockcoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "AutoLong")
public class AutoLong extends LinearOpMode {

    private DcMotor Left_Motor;
    private Servo MoveClaw;
    private DcMotor Right_Motor;

    @Override
    public void runOpMode() {
        Left_Motor = hardwareMap.get(DcMotor.class, "Left_Motor");
        MoveClaw = hardwareMap.get(Servo.class, "MoveClaw");
        Right_Motor = hardwareMap.get(DcMotor.class, "Right_Motor");

        // Put initialization blocks here
        Left_Motor.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        if (opModeIsActive()) {
            // Put run code
            MoveClaw.setPosition(0);
            sleep(20);
            Left_Motor.setPower(0.37);
            Right_Motor.setPower(0.37);
            sleep(300);
            Left_Motor.setPower(0.49);
            Right_Motor.setPower(0);
            sleep(1000);
            Left_Motor.setPower(-1);
            Right_Motor.setPower(-1);
            sleep(1225);
            Left_Motor.setPower(-0.63);
            Right_Motor.setPower(0);
            sleep(500);
            Left_Motor.setPower(-1);
            Right_Motor.setPower(-1);
            sleep(335);
            Left_Motor.setPower(0);
            Right_Motor.setPower(0);
            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.update();
            }
        }
    }
}
