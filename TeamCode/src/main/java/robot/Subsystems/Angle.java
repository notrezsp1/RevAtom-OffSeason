package robot.Subsystems;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Angle {

    public static CRServo angulo;

    private static final ElapsedTime timer = new ElapsedTime();

    public Angle(HardwareMap hardwareMap){
        angulo = hardwareMap.get(CRServo.class, "angulo");
    }

    public static void cima(){
        angulo.setPower(1.0);
    }

    public static void baixo(){
        angulo.setPower(-1.0);
    }

    public static void parar(){
        angulo.setPower(0.0);
    }

    public static boolean atualizarAngle() {
        return timer.seconds() >= 0.5;
    }
}
