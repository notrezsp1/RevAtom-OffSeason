package org.firstinspires.ftc.teamcode.pedroPathing.OpModes.Auto;





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

import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Angle;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.Extend;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
@Autonomous (name = "AutoOff")

public class AutoOff extends OpMode{

    private Timer pathTimer, actionTimer, opmodeTimer;
    public int pathState;



    public Follower follower;
    private final Pose startPose = new Pose(8, 104, Math.toRadians(270));
    private final Pose botarSample1 = new Pose(17, 125.5, Math.toRadians(-45));
    private final Pose pegarSample2 = new Pose(17, 125.5, Math.toRadians(-11));
    private final Pose botarSample2 = new Pose(17, 125.5, Math.toRadians(-45));
    private final Pose pegarSample3 = new Pose(17, 125.5, Math.toRadians(18));
    private final Pose botarSample3 = new Pose(17, 125.5, Math.toRadians(-45));
    private final Pose pegarSample4 = new Pose(17,125, Math.toRadians(38));
    private final Pose botarSample4 = new Pose(17, 125, Math.toRadians(-45));





    private Path poseInicial;
    private PathChain segundoSample, segundoSampleB, terceiroSample, terceiroSampleB, quartoSample, quartoSampleB;
    public void buildPaths(){
        poseInicial = new Path(new BezierLine(new Point(startPose), new Point(botarSample1)));
        poseInicial.setLinearHeadingInterpolation(startPose.getHeading(), botarSample1.getHeading());

        segundoSample = follower.pathBuilder()
                .addPath(new BezierLine(new Point(botarSample1), new Point(pegarSample2)))
                .setLinearHeadingInterpolation(botarSample1.getHeading(), pegarSample2.getHeading())
                .build();

        segundoSampleB = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pegarSample2), new Point(botarSample2)))
                .setLinearHeadingInterpolation(pegarSample2.getHeading(), botarSample2.getHeading())
                .build();

        terceiroSample = follower.pathBuilder()
                .addPath(new BezierLine(new Point(botarSample2), new Point(pegarSample3)))
                .setLinearHeadingInterpolation(botarSample2.getHeading(), pegarSample3.getHeading())
                .build();

        terceiroSampleB = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pegarSample3), new Point(botarSample3)))
                .setLinearHeadingInterpolation(pegarSample3.getHeading(), botarSample3.getHeading())
                .build();

        quartoSample = follower.pathBuilder()
                .addPath(new BezierLine(new Point(botarSample3), new Point(pegarSample4)))
                .setLinearHeadingInterpolation(botarSample3.getHeading(), pegarSample4.getHeading())
                .build();

        quartoSampleB = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pegarSample4), new Point(botarSample4)))
                .setLinearHeadingInterpolation(pegarSample4.getHeading(), botarSample4.getHeading())
                .build();
    }

    public void autonomo(){
        switch (pathState){
            case 0:
                follower.followPath(poseInicial, true);
                setPathState(1);
                break;
            case 1:
                if(!follower.isBusy()){
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
                }
                break;
            default:
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
        opmodeTimer.resetTimer();

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
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
        pathState = 0;
    }

    @Override
    public void stop(){

    }




}