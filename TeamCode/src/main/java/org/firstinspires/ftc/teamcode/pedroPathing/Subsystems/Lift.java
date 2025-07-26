package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;



import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.RConstants.Constantes;

public class Lift {
    private static DcMotorEx lift;
    private static final ElapsedTime timer = new ElapsedTime();

    public enum AutoEstado { parado, paraCima, paraBaixo, esperaDepoisCima, esperaDepoisBaixo }
    public static AutoEstado estado = AutoEstado.parado;
    public Lift(HardwareMap hardwareMap) {
        lift = hardwareMap.get(DcMotorEx.class, "Angulo");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public static void controleManual(double power){
        power = Range.clip(power , -1.0, 1.0);
        lift.setPower(power);
    }
    public static void paraPosicao(int target){
        lift.setTargetPosition(target);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1.0);
    }
    public static void paraCima() {
        lift.setTargetPosition(Constantes.MAXPOSE);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1.0);
        estado = AutoEstado.paraCima;
    }
    public static void paraBaixo() {
        lift.setTargetPosition(Constantes.MINPOSE);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1.0);
        estado = AutoEstado.paraBaixo;
    }

    public static boolean atualizar(){
        return !lift.isBusy();
    }




    public void auto(boolean botaoA) {
        switch (estado) {
            case parado:
                if (botaoA) {
                    paraCima();
                    estado = AutoEstado.paraCima;
                }
                break;

            case paraCima:
                if (atualizar()) {
                    lift.setPower(0);
                    timer.reset();
                    estado = AutoEstado.esperaDepoisCima;
                }
                break;

            case esperaDepoisCima:
                if (timer.seconds() >= 0.5) {
                    paraBaixo();
                    estado = AutoEstado.paraBaixo;
                }
                break;

            case paraBaixo:
                if (atualizar()) {
                    lift.setPower(0);
                    timer.reset();
                    estado = AutoEstado.esperaDepoisBaixo;
                }
                break;

            case esperaDepoisBaixo:
                if (timer.seconds() >= 0.5) {
                    estado = AutoEstado.parado;

                break;
                }
            }
        }
    }


