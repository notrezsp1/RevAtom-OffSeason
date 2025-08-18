package robot.Subsystems;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class Angle {

    public static Servo angulo;


    private static final ElapsedTime timer = new ElapsedTime();

    public Angle(HardwareMap hardwareMap) {
        angulo = hardwareMap.get(Servo.class, "angulo");
    }


    public static void setPosition(double valor) {
        angulo.setPosition(valor);
    }

    public static void down() {
        angulo.setPosition(1);
    }

    public static void up() {
        angulo.setPosition(0.3);


    }
}
