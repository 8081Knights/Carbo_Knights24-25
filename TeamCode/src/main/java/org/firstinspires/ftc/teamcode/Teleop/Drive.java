package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.HardwareSoftware;
@TeleOp(name="Drive")
public class Drive extends OpMode {

    HardwareSoftware hw = new HardwareSoftware();
    @Override
    public void init() {

        hw.init(hardwareMap);
       //  hw.Wrist().setPosition(0.0);

    }

    double y = 0;
    double rx = 0;
    double shoulder_y = 0;

    double x = 0;

    int ArmTargetPos = 1600;

    boolean Claw = false;
    int liftPosHigh = -3200;
    int liftPosLow = -10;
    int liftPosMid = -1000;
    double clawPosOpen = 0.2;
    double clawPosClosed = 0.4;
    double bucketPos1 = 0.5;
    double bucketPos2 = 0.7;
    double wristPos1 = 0.75;
    double wristPos2 = 0.1;

    public void MoveLiftUp()
    {
        hw.Lift().setTargetPosition(liftPosHigh);
        hw.Lift().setVelocity(1500);
        hw.Lift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void MoveLiftLow()
    {
        hw.Lift().setTargetPosition(liftPosLow);
        hw.Lift().setVelocity(1500);
        hw.Lift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void MoveLiftMid()
    {
        hw.Lift().setTargetPosition(liftPosMid);
        hw.Lift().setVelocity(1500);
        hw.Lift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void OpenClaw()
    {
        hw.Claw().setPosition(clawPosOpen);
    }
    public void CloseClaw()
    {
        hw.Claw().setPosition(clawPosClosed);
    }
    public void MoveBucket1()
    {
        hw.Bucket().setPosition(bucketPos1);
    }
    public void MoveBucket2()
    {
        hw.Bucket().setPosition(bucketPos2);
    }
    public void MoveWrist1()
    {
        hw.Bucket().setPosition(wristPos1);
    }
    public void MoveWrist2()
    {
        hw.Bucket().setPosition(wristPos2);
    }
    public void MoveArmHome()
    {
        hw.Arm().setTargetPosition(0);
        while(hw.Arm().getCurrentPosition() != 0) {
            hw.Arm().setVelocity(1000);
        }
    }
    public void PrimeArm()
    {
        hw.Arm().setTargetPosition(ArmTargetPos);
        while(hw.Arm().getCurrentPosition() != ArmTargetPos) {
            hw.Arm().setVelocity(1000);
        }
    }
    public void ResetShoulder()
    {
        while(hw.Shoulder().getCurrentPosition() > 0) {
            hw.Shoulder().setPower(-0.3);
        }
    }


    @Override
    public void loop() {


        y = gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;
        x = gamepad1.left_stick_x;

        shoulder_y = gamepad2.left_stick_y / 3;

        hw.FLdrive().setPower(-(y - rx - x));
        hw.FRdrive().setPower(-(y + rx + x));
        hw.BLdrive().setPower(-(y - rx + x));
        hw.BRdrive().setPower(-(y + rx - x));

        hw.Shoulder().setPower(shoulder_y);


// lift
        if (gamepad2.a) {
            MoveLiftLow();
        }

        if (gamepad2.b) {
            MoveLiftUp();
        }
        if (gamepad1.b) {
            MoveLiftMid();
        }

        // claw open/close
        if (gamepad2.x && !Claw) {
            Claw = true;
        } else if (gamepad2.x && Claw) {
            Claw = false;
        }

        if (Claw) {
            OpenClaw();
        }
        else {
            CloseClaw();
        }

        // arm down
        if(gamepad2.left_trigger > 0.5) {
            hw.Arm().setTargetPosition(0);
            if(hw.Arm().getCurrentPosition() > 0)
            hw.Arm().setVelocity(-1000);
            else
                hw.Arm().setVelocity(0);
        }

        // arm up
        else if(gamepad2.right_trigger > 0.5)
        {
            hw.Arm().setTargetPosition(ArmTargetPos);
            if(hw.Arm().getCurrentPosition() < ArmTargetPos)
                hw.Arm().setVelocity(1000);
            else{
                hw.Arm().setVelocity(0);
            }
        }
        else {
            hw.Arm().setVelocity(0);
        }

        // bucket
        if(gamepad2.right_bumper) {
            MoveBucket1();
        }
        if(gamepad2.left_bumper) {
            MoveBucket2();
        }

        // wrist
        if(gamepad2.y) {
            if (hw.Wrist().getPosition() == wristPos2) {
                MoveWrist1();
            }
            else {
                MoveWrist2();
            }
        }

        if (gamepad2.dpad_up) {
            MoveArmHome();
            ResetShoulder();
            OpenClaw();
        }

        if (gamepad2.dpad_down) {
            MoveBucket2();
            MoveLiftLow();
            MoveBucket1();
        }

        /*
        put specimen on bar

        while (hw.Shoulder().getCurrentPosition() != SpecPos) {
            hw.Shoulder().setPower(0.3);
        }
        hw.Arm().setVelocity(1000);

        shoulder rotate slightly/arm retract to secure hook on bar??
        claw release
        arm go down



        pick up a thing

        arm extend
        claw open
        shoulder rotate
        claw close
        shoulder rotate
         */



        telemetry.addData("Lodom", hw.FRdrive().getCurrentPosition());
        telemetry.addData("Rodom", hw.FLdrive().getCurrentPosition());
        telemetry.addData("Bodom", hw.BRdrive().getCurrentPosition());
        telemetry.addData("Lift", hw.Lift().getCurrentPosition());
        telemetry.addData("Claw", hw.Claw().getPosition());
        telemetry.addData("Arm", hw.Arm().getCurrentPosition());
        telemetry.addData("Wrist", hw.Wrist().getPosition());
        telemetry.addData("Lift Target", hw.Lift().getTargetPosition());
        telemetry.addData("Bucket Pos", hw.Bucket().getPosition());

        telemetry.update();

    }

}


