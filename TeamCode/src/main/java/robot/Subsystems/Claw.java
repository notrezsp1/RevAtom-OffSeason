package robot.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.arcrobotics.ftclib.command.Subsystem;

public class Claw implements Subsystem {

    public static Servo claw;

    private static final ElapsedTime timer = new ElapsedTime();


    public Claw(HardwareMap hardwareMap){
        claw = hardwareMap.get(Servo.class, "garra");
    }



    public static void open(){
        claw.setPosition(1.0);
    }

    public static void close(){
        claw.setPosition(-1.0);
    }

    public static void brake(){
        claw.setPosition(0.0);
    }

    public static boolean atualizarClaw() {
        return timer.seconds() >= 0.5;
    }



}
