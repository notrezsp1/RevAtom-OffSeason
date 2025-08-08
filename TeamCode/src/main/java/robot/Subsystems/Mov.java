package robot.Subsystems;


import com.qualcomm.robotcore.util.ElapsedTime;

public class Mov {


    private final ElapsedTime timer = new ElapsedTime();
    private int sState;



    public enum AutoEstado {IDLE, ARM_UP, ANGLE_UP, RETRACT}

    private AutoEstado estado = AutoEstado.IDLE;

    public void inicializarRecuo() {
        if (estado == AutoEstado.IDLE) {
            Initialize.get().arm.centro();
            timer.reset();
            estado = AutoEstado.ARM_UP;
        }
    }




}