package robot.OpModes.Auto;





import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import config.constants.FConstants;
import config.constants.LConstants;
import robot.Subsystems.Angle;
import robot.Subsystems.Arm;
import robot.Subsystems.Claw;
import robot.Subsystems.Extend;

@Autonomous (name = "AutoOff")

public class AutoOff extends OpMode{
    private Timer pathTimer, actionTimer, opmodeTimer;
    public int pathState;



    public Follower follower;
    public static Pose start = new Pose(8, 101, Math.toRadians(270));
    public static Pose score = new Pose(17, 125, Math.toRadians(-45));
    public static Pose second = new Pose(19, 121, Math.toRadians(0));
    public static Pose third = new Pose(19, 131.5, Math.toRadians(0));
    public static Pose four = new Pose(19, 135, Math.toRadians(15));
    public static Pose park = new Pose(67, 95, Math.toRadians(90));
    public static Pose contolPark = new Pose(50, 150);





    private Path poseInicial, parking;
    private PathChain segundoSample, segundoSampleB, terceiroSample, terceiroSampleB, quartoSample, quartoSampleB;
    public void buildPaths(){
        poseInicial = new Path(new BezierLine(new Point(start), new Point(score)));
        poseInicial.setLinearHeadingInterpolation(start.getHeading(), score.getHeading());

        segundoSample = follower.pathBuilder()
                .addPath(new BezierLine(new Point(score), new Point(second)))
                .setLinearHeadingInterpolation(score.getHeading(), second.getHeading())
                .build();

        segundoSampleB = follower.pathBuilder()
                .addPath(new BezierLine(new Point(second), new Point(score)))
                .setLinearHeadingInterpolation(second.getHeading(), score.getHeading())
                .build();

        terceiroSample = follower.pathBuilder()
                .addPath(new BezierLine(new Point(score), new Point(third)))
                .setLinearHeadingInterpolation(score.getHeading(), third.getHeading())
                .build();

        terceiroSampleB = follower.pathBuilder()
                .addPath(new BezierLine(new Point(third), new Point(score)))
                .setLinearHeadingInterpolation(third.getHeading(), score.getHeading())
                .build();

        quartoSample = follower.pathBuilder()
                .addPath(new BezierLine(new Point(score), new Point(four)))
                .setLinearHeadingInterpolation(score.getHeading(), four.getHeading())
                .build();

        quartoSampleB = follower.pathBuilder()
                .addPath(new BezierLine(new Point(four), new Point(score)))
                .setLinearHeadingInterpolation(four.getHeading(), score.getHeading())
                .build();

        parking = new Path(new BezierCurve(new Point(score), new Point(contolPark), new Point(park)));
        parking.setLinearHeadingInterpolation(score.getHeading(), park.getHeading());
    }

    public void autonomo(){
        switch (pathState){
            case 0:
                follower.followPath(poseInicial);
                Claw.open();

                setPathState(1);
                break;
            case 1:

                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 0.45){
                    follower.followPath(segundoSample, true);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()){
                    follower.followPath(segundoSampleB, true);
                    setPathState(3);
                }
                break;
            case 3:

                if (!follower.isBusy()){
                    follower.followPath(terceiroSample, true);
                    setPathState(4);
                }
                break;
            case 4:

                if (!follower.isBusy()){
                    follower.followPath(terceiroSampleB, true);
                    setPathState(5);
                }
                break;
            case 5:

                if (!follower.isBusy()){
                    follower.followPath(quartoSample, true);
                    setPathState(6);
                }
                break;
            case 6:

                if (!follower.isBusy()){
                    follower.followPath(quartoSampleB,true);
                    setPathState(-1);

                }
                break;
        }
    }


    public void setPathState(int pState){
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        actionTimer = new Timer();

        opmodeTimer.resetTimer();

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(start);
        buildPaths();
    }

    @Override
    public void loop() {


        follower.update();
        autonomo();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("isBusy", follower.isBusy());
        telemetry.addData("Current Target", follower.getCurrentPath());
        telemetry.update();
    }

    @Override
    public void init_loop(){}

    @Override
    public void start(){
        opmodeTimer.resetTimer();
        setPathState (0);
    }

    @Override
    public void stop(){

    }




}