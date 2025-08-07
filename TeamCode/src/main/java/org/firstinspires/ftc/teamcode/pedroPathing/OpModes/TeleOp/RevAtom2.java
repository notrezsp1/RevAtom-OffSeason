package org.firstinspires.ftc.teamcode.pedroPathing.OpModes.TeleOp;

import static org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Arm.arm;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Angle;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Initialize;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Extend;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Uplift;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Config
@TeleOp(name = "TeleOpRevAtom 2.0", group = "TeleOp")
public class RevAtom2 extends OpMode {
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);
    private boolean controleManualBraco = true;
    private boolean controleManualLinear = true;
    public double velocidade = (gamepad2.right_trigger > 0) ? 1.0 : 0.5;
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
        double forward = -gamepad1.left_stick_y * velocidade;
        double strafe = -gamepad1.left_stick_x * velocidade;
        double rotate = -gamepad1.right_stick_x * velocidade;
        follower.setTeleOpMovementVectors(forward, strafe, rotate, true);
        follower.update();

        controlarBraco();
        controlarLinear();
        controlarGarra();
        controlarAngulo();

        if (gamepad1.a) {
            Uplift.pindurar();
        }

        Arm.auto(gamepad2.a);

        atualizarTelemetria();
    }

    private void controlarBraco() {
        if (gamepad2.back) {
            controleManualBraco = !controleManualBraco;
            while (gamepad2.back) {}
        }

        if (controleManualBraco) {
            double potencia = -gamepad2.left_stick_y;
            Arm.controleManual(potencia);
        } else {
            if (gamepad2.dpad_up) {
                Arm.paraCima();
            } else if (gamepad2.dpad_down) {
                Arm.paraBaixo();
            } else if (gamepad2.x) {
                Arm.centro();
            }
        }
    }

    private void controlarLinear() {
        if (gamepad2.start) {
            controleManualLinear = !controleManualLinear;
            while (gamepad2.start) {}
        }

        if (controleManualLinear) {
            double potencia = -gamepad2.right_stick_y;
            Extend.controleManual(potencia);
        } else {
            if (gamepad2.dpad_right) {
                Extend.retrair();
            } else if (gamepad2.dpad_left) {
                Extend.estender();
            }
        }
    }

    private void controlarGarra() {
        if (gamepad2.right_trigger > 0.1) {
            Claw.open();
        } else if (gamepad2.left_trigger > 0.1) {
            Claw.close();
        } else {
            Claw.brake();
        }
    }

    private void controlarAngulo() {
        if (gamepad2.left_bumper) {
            Angle.cima();
        } else if (gamepad2.right_bumper) {
            Angle.baixo();
        } else {
            Angle.parar();
        }
    }

    private void atualizarTelemetria() {
        telemetry.addData("Modo Braço", controleManualBraco ? "MANUAL" : "AUTOMÁTICO");
        telemetry.addData("Posição Braço", arm.getCurrentPosition());
        telemetry.addData("Modo Linear", controleManualLinear ? "MANUAL" : "AUTOMÁTICO");
        telemetry.addData("Estado Braço", Arm.estado.toString());
    }
}