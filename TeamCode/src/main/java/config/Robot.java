package config;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;


import static robot.Subsystems.Arm.arm;
import static robot.Subsystems.Extend.extend;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import pedro.constants.FConstants;
import pedro.constants.LConstants;
import robot.Subsystems.Angle;
import robot.Subsystems.Arm;
import robot.Subsystems.Claw;
import robot.Subsystems.Extend;
import robot.Subsystems.Initialize;

public class Robot {
    public  Follower f;

    boolean controleManualBraco, controleManualLinear;
    private Initialize i;
    public Pose autoEndPose;

    public static Pose autoEnd = new Pose(60, 100, Math.toRadians(90));

    public Robot( Pose autoEndPose) {
        this.autoEndPose = autoEndPose;
        f = new Follower(hardwareMap, FConstants.class, LConstants.class);
        i = new Initialize (hardwareMap);

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
            double potencia = -gamepad2.left_stick_y;
            Arm.controlManual(potencia);

        }
    }

    private void controlarLinear() {

        if (controleManualLinear) {
            double potencia = -gamepad2.right_stick_y;
            Extend.setManualPower(potencia);
        }

            if (gamepad2.dpad_right) {
                Extend.retract();
            }
            if (gamepad2.dpad_left) {
                Extend.extend();
            }

    }

    private void controlarGarra() {
        if (gamepad2.right_trigger > 0.1) {
            Claw.open();
        } if (gamepad2.left_trigger > 0.1) {
            Claw.close();
        }
    }

    private void controlarAngulo() {
        if (gamepad2.left_bumper) {
            Angle.up();
        }if (gamepad2.right_bumper) {
            Angle.down();
        }
    }
    private void atualizarTelemetria() {
        telemetry.addData("Modo Braço", controleManualBraco ? "MANUAL" : "AUTOMÁTICO");
        telemetry.addData("Posição Braço", arm.getCurrentPosition());
        telemetry.addData("Posição Linear", extend.getCurrentPosition());
        telemetry.addData("Modo Linear", controleManualLinear ? "MANUAL" : "AUTOMÁTICO");
        telemetry.update();
    }
    public void drive (){
        double velocidade = (gamepad1.right_trigger>0.1) ? 0.5 : 1.0;
        f.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        f.update();
        f.setMaxPower(velocidade);
    }



}

