package reserved;

import static robot.Subsystems.Arm.arm;
import static robot.Subsystems.Extend.extend;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robot.Subsystems.Angle;
import robot.Subsystems.Claw;
import robot.Subsystems.Initialize;
import robot.Subsystems.Arm;
import robot.Subsystems.Extend;
import robot.Subsystems.Uplift;
import pedro.constants.FConstants;
import pedro.constants.LConstants;

@Config
@TeleOp(name = "TeleOpRevAtom 2.0", group = "TeleOp")
public class RevAtom2 extends OpMode {


    private Follower follower;
    private final Pose startPose = new Pose(0,0, Math.toRadians(0));
    private boolean controleManualBraco = true;
    private boolean controleManualLinear = true;


    @Override
    public void init() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        Initialize robot = new Initialize(hardwareMap);


    }
    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        
        double velocidade = (gamepad1.right_trigger>0.1) ? 0.5 : 1.0;
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();
        follower.setMaxPower(velocidade);

        controlarBraco();
        controlarLinear();
        controlarGarra();
        controlarAngulo();

        if (gamepad1.right_bumper) {
            Uplift.hang();
        }else if (gamepad1.left_bumper){
            Uplift.unhang();
        }
        else{
            Uplift.upLift.setPower(0);
        }

        Arm.auto(gamepad2.a);

        atualizarTelemetria();

    }

    private void controlarBraco() {

        if (controleManualBraco) {
            double potencia = -gamepad2.left_stick_y;
            Arm.controlManual(potencia);

        }
    }

    private void controlarLinear() {

        if (controleManualLinear) {
            double potencia = -gamepad2.right_stick_y;
            Extend.setManualPower(potencia);
        } else {
            if (gamepad2.dpad_right) {
                Extend.retract();
            } else if (gamepad2.dpad_left) {
                Extend.extend();
            }
        }
    }

    private void controlarGarra() {
        if (gamepad2.right_trigger > 0.1) {
            Claw.open();
        } else if (gamepad2.left_trigger > 0.1) {
            Claw.close();
        }
    }

    private void controlarAngulo() {
        if (gamepad2.left_bumper) {
            Angle.up();
        } else if (gamepad2.right_bumper) {
            Angle.down();
        }
    }







    private void atualizarTelemetria() {
        telemetry.addData("Modo Braço", controleManualBraco ? "MANUAL" : "AUTOMÁTICO");
        telemetry.addData("Posição Braço", arm.getCurrentPosition());
        telemetry.addData("Posição Linear", extend.getCurrentPosition());
        telemetry.addData("Modo Linear", controleManualLinear ? "MANUAL" : "AUTOMÁTICO");
    }
}