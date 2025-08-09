package robot.OpModes.Auto;



import static robot.Subsystems.Extend.extend;
import static robot.Subsystems.RConstants.Constantes.EXTEND_MAX;
import static robot.Subsystems.RConstants.Constantes.MAXPOSE;

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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import config.constants.FConstants;
import config.constants.LConstants;
import robot.Subsystems.Angle;
import robot.Subsystems.Arm;
import robot.Subsystems.Claw;
import robot.Subsystems.Extend;

@Autonomous(name = "RevAuto", group = "Autonomo")
public class AutoRev extends OpMode {





    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private final double xBasket = 17.7;
    private final double yBasket = 127.3;
    private final double xSample = 25.5;
    private final Pose poseInicial = new Pose(9, 104, Math.toRadians(-90));
    private final Pose botarSample = new Pose(18.5, 113.5, Math.toRadians(-45));
    private final Pose pegarSample2 = new Pose(xSample, 120, Math.toRadians(0));
    private final Pose botarSample2 = new Pose(xBasket, yBasket, Math.toRadians(-45));
    private final Pose pegarSample3 = new Pose(xSample, 129, Math.toRadians(0));
    private final Pose botarSample3 = new Pose(xBasket, yBasket, Math.toRadians(-45));
    private final Pose pegarSample4 = new Pose(32.5, 124.5, Math.toRadians(45));
    private final Pose botarSample4 = new Pose( xBasket, yBasket, Math.toRadians(-45));
    private final Pose park = new Pose(60, 102, Math.toRadians(90));
    private final Pose controlPark = new Pose (50, 120);



    private Path scorePreload, parking;
    private PathChain depositarSampleUm, pegarSampleDois, depositarSampleDois,
            pegarSampleTres, depositarSampleTres, pegarSampleQuatro;

    public void buildPaths() {
        scorePreload = new Path(new BezierLine(new Point(poseInicial), new Point(botarSample)));
        scorePreload.setLinearHeadingInterpolation(poseInicial.getHeading(), botarSample.getHeading());

        depositarSampleUm = follower.pathBuilder()
                .addPath(new BezierLine(new Point(botarSample), new Point(pegarSample2)))
                .setLinearHeadingInterpolation(botarSample.getHeading(),pegarSample2.getHeading())
                .build();

        pegarSampleDois = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pegarSample2), new Point(botarSample2)))
                .setLinearHeadingInterpolation(pegarSample2.getHeading(),botarSample2.getHeading())
                .build();

        depositarSampleDois = follower.pathBuilder()
                .addPath(new BezierLine(new Point(botarSample2), new Point(pegarSample3)))
                .setLinearHeadingInterpolation(botarSample2.getHeading(),pegarSample3.getHeading())
                .build();

        pegarSampleTres = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pegarSample3), new Point(botarSample3)))
                .setLinearHeadingInterpolation(pegarSample3.getHeading(),botarSample3.getHeading())
                .build();

        depositarSampleTres = follower.pathBuilder()
                .addPath(new BezierLine(new Point(botarSample3), new Point(pegarSample4)))
                .setLinearHeadingInterpolation(botarSample3.getHeading(),pegarSample4.getHeading())
                .build();

        pegarSampleQuatro = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pegarSample4), new Point(botarSample4)))
                .setLinearHeadingInterpolation(pegarSample4.getHeading(),botarSample4.getHeading())
                .build();

        parking = new Path(new BezierCurve(new Point(botarSample4),new Point(controlPark), new Point(park)));
        parking.setLinearHeadingInterpolation(botarSample4.getHeading(), park.getHeading());

    }


    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(depositarSampleUm);

                extend.setTargetPosition(2100);



                setPathState(1);

                break;

            case 1:
                new WaitCommand(10000000);
                if(!follower.isBusy()) {

                    if(pathTimer.getElapsedTimeSeconds() > 0.5) {


                        follower.followPath(pegarSampleDois, true);


                    }
                }
                setPathState(2);
                break;

            case 2:
                if (!follower.isBusy()){
                    if(pathTimer.getElapsedTimeSeconds() > 0.5) {

                    follower.followPath(depositarSampleDois, true);
                    setPathState(3);
                    }
                }
                break;
            case 3:
                if (!follower.isBusy()){
                    if(pathTimer.getElapsedTimeSeconds() > 0.5) {
                    follower.followPath(pegarSampleTres, true);
                    setPathState(4);
                    }
                }
                break;
            case 4:

                if (!follower.isBusy()){
                    if(pathTimer.getElapsedTimeSeconds() > 0.5) {

                    follower.followPath(depositarSampleTres, true);
                    setPathState(5);
                    }
                }
                break;
            case 5:

                if (!follower.isBusy()){
                    if(pathTimer.getElapsedTimeSeconds() > 0.5) {
                    follower.followPath(pegarSampleQuatro, true);

                    setPathState(6);
                    }
                }
                break;

            case 6:

                if (!follower.isBusy()){
                    if(pathTimer.getElapsedTimeSeconds() > 0.5){
                        follower.followPath(parking, true);
                    }
                }
                break;
        }
    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
    @Override
    public void init() {
        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(poseInicial);
        buildPaths();
    }





    @Override
    public void init_loop(){}

    @Override
    public void start(){
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void stop(){}

}