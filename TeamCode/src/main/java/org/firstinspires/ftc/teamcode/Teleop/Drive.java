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

    }

    double y = 0;
    double rx = 0;

    double x = 0;

    int ArmTargetPos = 500;
    int LiftLow = -100;
    int LiftHigh = -2000;
    int LiftMid = -1000;



    @Override
    public void loop() {


        y = gamepad1.left_stick_y;
        rx = gamepad1.right_stick_x;
        x = gamepad1.left_stick_x;

        hw.FLdrive().setPower(-(y-rx-x));
        hw.FRdrive().setPower(-(y+rx+x));
        hw.BLdrive().setPower(-(y-rx+x));
        hw.BRdrive().setPower(-(y+rx-x));

        // lift high/low
        if(gamepad2.a)
        {
            if(hw.Lift().getCurrentPosition() < LiftLow) {
                hw.Lift().setTargetPosition(LiftLow);
            }
            else{
                hw.Lift().setTargetPosition(LiftHigh);
            }
            hw.Lift().setVelocity(2000);
            hw.Lift().setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        // lift mid
        if(gamepad2.b)
        {
            hw.Lift().setTargetPosition(LiftMid);
            hw.Lift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.Lift().setVelocity(2000);
        }

        // claw open/close
        if(gamepad2.x)
        {
            if(hw.Claw().getPosition() != 0) {
                hw.Claw().setPosition(0);
            }
            else {
                hw.Claw().setPosition(0.1);
            }
        }

        // arm down
        if(gamepad2.left_trigger > 0.5) {
            hw.Arm().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
            hw.Bucket().setPosition(1);}
        if(gamepad2.left_bumper) {
            hw.Bucket().setPosition(0);}

        //shoulder
        // if (gamepad2.y) {
        //     if (hw.Shoulder().getCurrentPosition() > 0) {
        //          hw.Shoulder().setPosition(0)



        telemetry.addData("Lodom", hw.FRdrive().getCurrentPosition());
        telemetry.addData("Rodom", hw.FLdrive().getCurrentPosition());
        telemetry.addData("Bodom", hw.BRdrive().getCurrentPosition());
        telemetry.addData("Lift", hw.Lift().getCurrentPosition());
        telemetry.addData("Claw", hw.Claw().getPosition());
        telemetry.addData("Arm", hw.Arm().getCurrentPosition());
        telemetry.update();

    }

}


