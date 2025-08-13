package robot.Subsystems;



import static robot.Subsystems.Angle.setPosition;
import static robot.Subsystems.RConstants.Constantes.MAXPOSE;
import static robot.Subsystems.RConstants.Constantes.MINPOSE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.arcrobotics.ftclib.command.Subsystem;

import robot.Subsystems.RConstants.Constantes;

public class Arm implements Subsystem {
    public static DcMotorEx arm;
    private static final ElapsedTime timer = new ElapsedTime();;
    public static int state;

    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.get(DcMotorEx.class, "Angulo");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static void controlManual(double power) {
        if (Math.abs(power) > 0.1) { // deadzone
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            arm.setPower(Range.clip(power, -1.0, 1.0));
        } else {
            arm.setPower(0);
        }
    }
    public static void toPosition(int target, double power) {
        arm.setTargetPosition(target);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(power);
    }

    public static void toHigh() {
            setPosition(MAXPOSE);

    }

    public static void toLow() {
            setPosition(MINPOSE);
    }

    public static void toMid() {
        setPosition(Constantes.MEDPOSE);

    }

    public static boolean update() {
        return !arm.isBusy();
    }

    public static void auto(boolean botaoA) {
        switch (state) {
            case 0:
                if (botaoA) {
                    toHigh();
                    setState(1);
                }
                break;

            case 1:
                if (update()) {
                    arm.setPower(0);
                    setState(2);
                }
                break;

            case 2:
                if (update() && timer.seconds() >= 0.5) {
                    toLow();
                    setState(3);
                }
                break;

            case 3:
                if (update()) {
                    arm.setPower(0);

                    setState(4);
                }
                break;

            case 4:
                if (timer.seconds() >= 0.5) {
                    setState(-1);
                }
                break;


            }


        }
        public static void setState(int pState){
            state = pState;
            timer.reset();
        }
    }
