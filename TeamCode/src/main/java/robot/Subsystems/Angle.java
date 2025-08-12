package robot.Subsystems;


import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class Angle  {

    public static Servo angulo;


    private static final ElapsedTime timer = new ElapsedTime();

    public Angle(HardwareMap hardwareMap){
        angulo = hardwareMap.get(Servo.class, "angulo");
    }


    public static void setPosition(double valor){
        angulo.setPosition(valor);
    }
    public static void up(){
        angulo.setPosition(1);
    }
    public static void upPlus(){
        double novaPos = angulo.getPosition() + 0.25;
        angulo.setPosition(Range.clip(novaPos, 0.0, 1.0)); ;
    }
    public static void downMinus() {
        double novaPos = angulo.getPosition() - 0.25;
        angulo.setPosition(Range.clip(novaPos, 0.0, 1.0));
    }
    public static void downmid(){
        angulo.setPosition(0.5);
    }

    public static void down(){
        angulo.setPosition(0);
    }


}
