package org.firstinspires.ftc.teamcode.pedroPathing.OpModes.TeleOp;



import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.RConstants.Constantes;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Extend;

@Config
@TeleOp(name = "TeleOpRevAtom", group = "TeleOp")

public class RevAtom extends OpMode {


        private Extend extend;
        private Lift lift;
        private Follower follower;
        private final Pose startPose = new Pose(0,0,0);

        /** This method is call once when init is played, it initializes the follower **/
        @Override
        public void init() {
            lift = new Lift(hardwareMap);
            extend = new Extend(hardwareMap);
            follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
            follower.setStartingPose(startPose);

        }
        @Override
        public void start() {
            follower.startTeleopDrive();
        }
        @Override
        public void loop() {
            follower.setTeleOpMovementVectors(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, true);
            follower.update();


            lift.auto(gamepad2.a);


            if (gamepad2.dpad_up){
                lift.paraPosicao(Constantes.MAXPOSE);
            } else if (gamepad2.dpad_down) {
                lift.paraPosicao(Constantes.MINPOSE);
            }
            else{
                double potencia = -gamepad2.left_stick_y;
                lift.controleManual(potencia);
            }


            if (gamepad2.dpad_right){
                extend.paraPose(Constantes.EXTEND_MAX);
            }else if (gamepad2.dpad_left){
                extend.paraPose(Constantes.EXTEND_MIN);
            }
            else{
                double power = gamepad2.right_stick_y;
                extend.manual(power);
            }
        }
    }
