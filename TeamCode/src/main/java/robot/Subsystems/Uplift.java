package robot.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Uplift {

    public static DcMotorEx upLift;

    public Uplift(HardwareMap hardwareMap){
        upLift = hardwareMap.get(DcMotorEx.class, "Pindurar");
        upLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        upLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static void pindurar(){
        upLift.setPower(-1.0);
    }

    public static void desinrolar(){ upLift.setPower(1.0); }

}
