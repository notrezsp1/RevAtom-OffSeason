package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;

import static org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.RConstants.Constantes.EXTEND_MAX;
import static org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.RConstants.Constantes.EXTEND_MIN;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class Extend {
    private static DcMotorEx extend;
    private static final ElapsedTime timer = new ElapsedTime();
    private static final ElapsedTime movementTimer = new ElapsedTime();
    private static final double TIMEOUT = 3.0;

    public enum AutoEstado {PARADO, ESTENDER, RETRAIR, ESPERANDO_ESTENDER, ESPERANDO_RETRAIR}
    public static AutoEstado estado = AutoEstado.PARADO;

    public Extend(HardwareMap hardwareMap) {
        extend = hardwareMap.get(DcMotorEx.class, "Linear");
        extend.setDirection(DcMotorEx.Direction.FORWARD);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static void controleManual(double power) {
        if (Math.abs(power) > 0.1) {
            extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            power = Range.clip(power, -1.0, 1.0);
            if ((power > 0 && extend.getCurrentPosition() < EXTEND_MAX) ||
                    (power < 0 && extend.getCurrentPosition() > EXTEND_MIN)) {
                extend.setPower(power);
            } else {
                extend.setPower(0);
            }
        } else {
            extend.setPower(0);
        }
    }

    public static void paraPosicao(int target) {
        target = Range.clip(target, EXTEND_MIN, EXTEND_MAX);
        extend.setTargetPosition(target);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setPower(1.0);
        movementTimer.reset();
    }

    public static void estender() {
        if (extend.getCurrentPosition() < EXTEND_MAX - 10) {
            paraPosicao(EXTEND_MAX);
        }
    }

    public static void retrair() {
        if (extend.getCurrentPosition() > EXTEND_MIN + 10) {
            paraPosicao(EXTEND_MIN);
        }
    }

    public static boolean atualizarLinear() {
        return !extend.isBusy() || movementTimer.seconds() >= TIMEOUT;
    }

    public static void auto(boolean botaoA, boolean botaoB) {
        switch (estado) {
            case PARADO:
                if (botaoA) {
                    estender();
                    estado = AutoEstado.ESTENDER;
                } else if (botaoB) {
                    retrair();
                    estado = AutoEstado.RETRAIR;
                }
                break;

            case ESTENDER:
                if (atualizarLinear()) {
                    extend.setPower(0);
                    timer.reset();
                    estado = AutoEstado.ESPERANDO_ESTENDER;
                }
                break;

            case ESPERANDO_ESTENDER:
                if (timer.seconds() >= 0.5) {
                    estado = AutoEstado.PARADO;
                }
                break;

            case RETRAIR:
                if (atualizarLinear()) {
                    extend.setPower(0);
                    timer.reset();
                    estado = AutoEstado.ESPERANDO_RETRAIR;
                }
                break;

            case ESPERANDO_RETRAIR:
                estado = AutoEstado.PARADO;
                break;
        }
    }
}