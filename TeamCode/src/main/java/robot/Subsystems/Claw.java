package robot.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Claw {

    private static CRServo claw;

    private static final ElapsedTime timer = new ElapsedTime();


    public Claw(HardwareMap hardwareMap){
        claw = hardwareMap.get(CRServo.class, "garra");
    }


    public static void open(){
        claw.setPower(1.0);
    }

    public static void close(){
        claw.setPower(-1.0);
    }

    public static void brake(){
        claw.setPower(0.0);
    }

    public static boolean atualizarClaw() {
        return timer.seconds() >= 0.5;
    }



}
