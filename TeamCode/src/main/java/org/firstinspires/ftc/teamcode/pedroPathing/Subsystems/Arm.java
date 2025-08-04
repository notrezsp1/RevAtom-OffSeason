package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;



import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.RConstants.Constantes;

public class Arm {
     public static DcMotorEx arm;
    private static final ElapsedTime timer = new ElapsedTime();

    public enum AutoEstado { parado, paraCima, paraBaixo, esperaDepoisCima, esperaDepoisBaixo, medio }
    public static AutoEstado estado = AutoEstado.parado;
    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.get(DcMotorEx.class, "Angulo");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public static void controleManual(double power){
        power = Range.clip(power , -1.0, 1.0);
        arm.setPower(power);
    }
    public static void paraPosicao(int target){
        arm.setTargetPosition(target);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1.0);
    }
    public static void paraCima() {
        arm.setTargetPosition(Constantes.MAXPOSE);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1.0);
        estado = AutoEstado.paraCima;
    }
    public static void paraBaixo() {
        arm.setTargetPosition(Constantes.MINPOSE);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1.0);
        estado = AutoEstado.paraBaixo;
    }

    public static void centro() {
        arm.setTargetPosition(Constantes.MEDPOSE);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1.0);
        estado = AutoEstado.medio;
    }

    public static boolean atualizar(){
        return !arm.isBusy();
    }




    public static void auto(boolean botaoA) {
            switch (estado) {
                case parado:
                    if (botaoA) {
                        paraCima();
                        estado = AutoEstado.paraCima;
                    }
                    break;

                case paraCima:
                    if (atualizar()) {
                        arm.setPower(0);
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
                        arm.setPower(0);
                        timer.reset();
                        estado = AutoEstado.esperaDepoisBaixo;
                    }
                    break;

                case esperaDepoisBaixo:
                    if (timer.seconds() >= 0.5) {
                        estado = AutoEstado.parado;

                    break;

                    }
                default:
                    break;
                }
            }
    }


