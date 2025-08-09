package robot.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Initialize {
    public final Angle angle;
    public final Claw claw;
    public final Arm arm;
    public final Extend extend;
    public final Uplift uplift;




    private static Initialize instance;

    public Initialize(HardwareMap hardwareMap) {
        angle = new Angle(hardwareMap);
        claw = new Claw(hardwareMap);
        arm = new Arm(hardwareMap);
        extend = new Extend(hardwareMap);
        uplift = new Uplift(hardwareMap);
        instance = this;
    }

    public static Initialize get() {
        return instance;
    }
}