package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;


import static org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Extend.atualizarLinear;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Mov {

    private final Extend extend;
    private final Arm arm;
    private final Claw claw;
    private final Angle angle;


    public Mov(Extend extend, Arm arm, Claw claw, Angle angle) {
        this.extend = extend;
        this.arm = arm;
        this.claw = claw;
        this.angle = angle;
    }

    private static final ElapsedTime timer = new ElapsedTime();
    public enum AutoEstado { parado, closeClaw, angulou, retrair,}

    public static AutoEstado estado = AutoEstado.parado;


    public void recuoBraco(boolean buttonA){
        switch (estado){
            case parado:
                if (buttonA){

                    claw.close();
                    timer.reset();
                    estado = AutoEstado.closeClaw;

                }
                break;

            case closeClaw:
                if (timer.seconds() > 0.5){

                    angle.cima();
                    timer.reset();
                    estado = AutoEstado.angulou;

                }
                break;

            case angulou:
                if (timer.seconds() > 0.5){

                    extend.retrair();

                    estado = AutoEstado.retrair;

                }
                break;

            case retrair:
                if (atualizarLinear()){

                    arm.centro();

                    estado = AutoEstado.parado;

                }
                break;

            default:
                break;
        }
    }
}
