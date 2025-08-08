package org.firstinspires.ftc.teamcode.pedroPathing.OpModes.TeleOp;

import static org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Arm.arm;
import static org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Extend.extend;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    public Timer sTimer;
    private int sState;
    private final ElapsedTime timer = new ElapsedTime();
    public enum AutoEstado { IDLE, ARM_UP, ANGLE_UP, RETRACT}
    private AutoEstado estado = AutoEstado.IDLE;

    @Override
    public void init() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        Initialize robot = new Initialize(hardwareMap);

        sTimer = new Timer();
    }
    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true) ;
        follower.update();

        controlarBraco();
        controlarLinear();
        controlarGarra();
        controlarAngulo();

        if (gamepad1.right_bumper) {
            Uplift.pindurar();
        }else if (gamepad1.left_bumper){
            Uplift.desinrolar();
        }

        if (gamepad2.a){
            submersible();
        }


    }

    private void controlarBraco() {

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
    private void submersible() {


        switch (sState) {
            case 0:
                Extend.retrair();
                setSubmersibleState(1);
                break;
            case 1:
                if (sTimer.getElapsedTimeSeconds() > 0.1) {
                    Angle.cima();
                    setSubmersibleState(2);
                }
                break;
            case 2:
                if (sTimer.getElapsedTimeSeconds() > 0.25) {
                    Arm.paraCima();
                    setSubmersibleState(-1);
                }
                break;
        }
    }
    public void setSubmersibleState(int x) {
        sState = x;
        sTimer.resetTimer();
    }





    private void atualizarTelemetria() {
        telemetry.addData("Modo Braço", controleManualBraco ? "MANUAL" : "AUTOMÁTICO");
        telemetry.addData("Posição Braço", arm.getCurrentPosition());
        telemetry.addData("Posição Linear", extend.getCurrentPosition());
        telemetry.addData("Modo Linear", controleManualLinear ? "MANUAL" : "AUTOMÁTICO");
        telemetry.addData("Estado Braço", Arm.estado.toString());
    }
}