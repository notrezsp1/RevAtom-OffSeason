package config;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import pedro.constants.FConstants;
import pedro.constants.LConstants;
import robot.Subsystems.Angle;
import robot.Subsystems.Arm;
import robot.Subsystems.Claw;
import robot.Subsystems.Extend;
import robot.Subsystems.Initialize;

public class Robot {
    public Follower f;
    public HardwareMap h;
    public Telemetry t;
    public  Gamepad g1, g2;
    boolean controleManualBraco, controleManualLinear;
    public Initialize i;
    public Pose autoEndPose;

    public static Pose autoEnd = new Pose(60, 100, Math.toRadians(90));

    public Robot(HardwareMap h, Telemetry t, Gamepad g1, Gamepad g2, Pose autoEndPose) {
        this.h = h;
        this.t = t;
        this.g1 = g1;
        this.g2 = g2;
        this.autoEndPose = autoEndPose;

        f = new Follower(h, FConstants.class, LConstants.class);
        i = new Initialize(h);
    }

    public void rStart() {
        f.startTeleopDrive();
    }

    public void dualControls() {
        drive();
        controlarBraco();
        controlarLinear();
        controlarGarra();
        controlarAngulo();
        atualizarTelemetria();
    }

    private void controlarBraco() {
        if (controleManualBraco) {
            double potencia = -g2.left_stick_y;
            Arm.controlManual(potencia);
        }
    }

    private void controlarLinear() {
        if (controleManualLinear) {
            double potencia = -g2.right_stick_y;
            Extend.setManualPower(potencia);
        }

        if (g2.dpad_right) {
            Extend.retract();
        }
        if (g2.dpad_left) {
            Extend.extend();
        }
    }

    private void controlarGarra() {
        if (g2.right_trigger > 0.1) {
            Claw.open();
        }
        if (g2.left_trigger > 0.1) {
            Claw.close();
        }
    }

    private void controlarAngulo() {
        if (g2.left_bumper) {
            Angle.up();
        }
        if (g2.right_bumper) {
            Angle.down();
        }
    }

    private void atualizarTelemetria() {
        t.addData("Modo Braço", controleManualBraco ? "MANUAL" : "AUTOMÁTICO");
        t.addData("Posição Braço", Arm.arm.getCurrentPosition());
        t.addData("Posição Linear", Extend.extend.getCurrentPosition());
        t.addData("Modo Linear", controleManualLinear ? "MANUAL" : "AUTOMÁTICO");
        t.update();
    }

    public void drive() {
        double velocidade = (g1.right_trigger > 0.1) ? 0.5 : 1.0;
        f.setTeleOpMovementVectors(-g1.left_stick_y, -g1.left_stick_x, -g1.right_stick_x, true);
        f.update();
        f.setMaxPower(velocidade);
    }
}