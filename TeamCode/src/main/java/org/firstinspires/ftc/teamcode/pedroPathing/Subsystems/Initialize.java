package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Initialize {
    public final Angle angle;
    public final Claw claw;
    public final Arm arm;
    public final Extend extend;

    public Initialize(HardwareMap hardwareMap) {
        angle = new Angle(hardwareMap);
        claw = new Claw(hardwareMap);
        arm = new Arm(hardwareMap);
        extend = new Extend(hardwareMap);
    }
}