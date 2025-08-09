package robot.Subsystems;

import static robot.Subsystems.RConstants.Constantes.EXTEND_MAX;
import static robot.Subsystems.RConstants.Constantes.EXTEND_MIN;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.arcrobotics.ftclib.command.Subsystem;

public class Extend implements Subsystem{
    public static DcMotorEx extend;
    private static final ElapsedTime timer = new ElapsedTime();
    private static final ElapsedTime movementTimer = new ElapsedTime();
    private static final double TIMEOUT = 3.0;



    public Extend(HardwareMap hardwareMap) {
        extend = hardwareMap.get(DcMotorEx.class, "Linear");
        extend.setDirection(DcMotorEx.Direction.REVERSE);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static void controleManual(double power) {
        if (Math.abs(power) > 0.1) { // deadzone
            extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            extend.setPower(Range.clip(power, -1.0, 1.0));
        } else {
            extend.setPower(0);
        }
    }

    public static void paraPosicao(int target) {
        extend.setTargetPosition(target);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setPower(1.0);
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
}
