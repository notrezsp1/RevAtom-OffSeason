package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Angle {

    public CRServo angle;

    public Angle(HardwareMap hardwareMap){
        angle = hardwareMap.get(CRServo.class, "angle_claw");
    }

    public void cima(){
        angle.setPower(1.0);
    }

    public void baixo(){
        angle.setPower(-1.0);
    }

    public void parar(){
        angle.setPower(0.0);
    }
}
