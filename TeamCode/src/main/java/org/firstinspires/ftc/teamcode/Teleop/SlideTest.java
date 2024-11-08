package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.HardwareSoftware;

@TeleOp(name="SlideTest")
public class SlideTest extends OpMode {


    HardwareSoftware hw = new HardwareSoftware();
    boolean isAtBottom = true;
    @Override
    public void init() {

        hw.init(hardwareMap);


    }

  double Slide;
    @Override
    public void loop() {
        if(gamepad2.a)
        {
            if(isAtBottom == false) {
                hw.Lift().setTargetPosition(0);
                isAtBottom = true;
            }
            else{
                hw.Lift().setTargetPosition(-3000);
                isAtBottom = false;
            }
            hw.Lift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.Lift().setVelocity(2000);
        }
        if(gamepad2.b)
        {
            hw.Lift().setTargetPosition(-1500);
            isAtBottom = false;
            hw.Lift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hw.Lift().setVelocity(2000);

        }








        telemetry.addData("Lift data:", hw.Lift().getCurrentPosition());
        telemetry.update();

    }

}

