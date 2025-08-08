package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;


import static org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Extend.atualizarLinear;
import static org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Extend.extend;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.util.Timing;
import com.pedropathing.util.Timer;
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