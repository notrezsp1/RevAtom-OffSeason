package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Claw {

    private CRServo claw;

    public Claw(HardwareMap hardwareMap){
        claw = hardwareMap.get(CRServo.class, "garra");
    }


    public void open(){
        claw.setPower(1.0);
    }

    public void close(){
        claw.setPower(-1.0);
    }

    public void brake(){
        claw.setPower(0.0);
    }
}
