package reserved;





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
import config.constants.FConstants;
import config.constants.LConstants;

;

@Config
@TeleOp(name = "TeleOpRevAtom", group = "TeleOp")

public class PlanoB extends OpMode {



        private Follower follower;
        private final Pose startPose = new Pose(0,0,0);


        /** This method is call once when init is played, it initializes the follower **/
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
            double velocidade = (gamepad2.right_trigger > 0) ? 1.0 : 0.5;
            follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true) ;
            follower.update();






            //CONTROLE DO BRAÇO
            if (gamepad2.dpad_up){
                Arm.toHigh();
            } else if (gamepad2.dpad_down) {
                Arm.toLow();
            }
            else{
                double potencia = -gamepad2.left_stick_y;
                Arm.controlManual(potencia);
            }


            //CONTROLE DO LINEAR
            if (gamepad2.dpad_right){
                Extend.retract();
            }else if (gamepad2.dpad_left){
                Extend.extend();
            }
            else{
                double power = gamepad2.right_stick_y;
                Extend.setManualPower(power);
            }

            // CONTROLE GARRA TOTAL
                if (gamepad2.right_trigger > 0.1) {
                    Claw.open();
                } else if (gamepad2.left_trigger > 0.1) {
                    Claw.close();}

                if (gamepad2.left_bumper){
                    Angle.up();
                } else if (gamepad2.right_bumper) {
                    Angle.down();
                Arm.auto(gamepad2.a);

                if (gamepad1.a){
                    Uplift.hang();
                }


            telemetry.addData("Target", Arm.arm.getCurrentPosition());
                telemetry.addData("poseExtend", Extend.extend.getCurrentPosition())     ;
            }
        }
    }

