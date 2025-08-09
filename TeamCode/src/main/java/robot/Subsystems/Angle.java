package robot.Subsystems;


import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Angle  {

    public static Servo angulo;


    private static final ElapsedTime timer = new ElapsedTime();

    public Angle(HardwareMap hardwareMap){
        angulo = hardwareMap.get(Servo.class, "angulo");
    }

    public static void cima(double valor){
        angulo.setPosition(valor);
    }

    public static void baixo(){
        angulo.setPosition(-1.0);
    }

    public static void parar(){
        angulo.setPosition(0.0);
    }

    public static boolean atualizarAngle() {
        return timer.seconds() >= 0.5;
    }
}
