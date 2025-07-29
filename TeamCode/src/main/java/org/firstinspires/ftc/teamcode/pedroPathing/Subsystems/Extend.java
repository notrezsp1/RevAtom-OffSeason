package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;

import static org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.RConstants.Constantes.EXTEND_MAX;
import static org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.RConstants.Constantes.EXTEND_MIN;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


public class Extend {
 private static DcMotorEx extend;
 private static final ElapsedTime timer = new ElapsedTime();
    public enum AutoEstado {parado, estender, retrair,}
    public static AutoEstado estado = AutoEstado.parado;

    public Extend(HardwareMap hardwareMap) {
        extend = hardwareMap.get(DcMotorEx.class, "Linear");
        extend.setDirection(DcMotorEx.Direction.FORWARD);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static  void manual(double power){
        power = Range.clip(power, -1.0, 1.0);
        extend.setPower(power);
    }

    public static void paraPose(int target){
        extend.setTargetPosition(target);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setPower(1.0);
    }

    public static void estender(){
        extend.setTargetPosition(EXTEND_MAX);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setPower(1.0);
        estado = AutoEstado.estender;
    }

    public static void retrair(){
        extend.setTargetPosition(EXTEND_MIN);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setPower(-1.0);
        estado = AutoEstado.retrair;
    }

    public static boolean atualizar(){return !extend.isBusy();}

    public void Automatico(boolean botaoB){
        switch (estado){
            case parado:
                if (botaoB){
                    retrair();
                    estado = AutoEstado.retrair;
                }
                break;

            case retrair:
        }
    }



}
