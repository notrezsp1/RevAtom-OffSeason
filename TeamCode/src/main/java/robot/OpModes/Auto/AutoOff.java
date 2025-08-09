package robot.OpModes.Auto;






import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;

import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;



import config.constants.FConstants;
import config.constants.LConstants;
import robot.Subsystems.Actions;
import robot.Subsystems.Angle;
import robot.Subsystems.Arm;
import robot.Subsystems.Claw;
import robot.Subsystems.Extend;
import robot.Subsystems.Initialize;

@Autonomous (name = "AutoOff")

public class AutoOff extends OpMode{
    private Follower follower;

    private Timer pathTimer, opmodeTimer;
    private boolean poseSet = false;
    private int pathState;
    private boolean clips = false;
    private PathChain path1, path2, path3, path4, path5, path6, path7, path8;

    public static Pose start = new Pose(8, 101, Math.toRadians(270));

    public enum AutoEstado { parado, paraCima, paraBaixo, esperaDepoisCima, esperaDepoisBaixo, medio }
    public static Arm.AutoEstado estado = Arm.AutoEstado.parado;
    private ElapsedTime timer = new ElapsedTime();

    public void buildPaths(){

        path1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(8.000, 103.5, Point.CARTESIAN),
                                new Point(17, 125.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(-45))
                .build();

        path2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(17.000, 125.000, Point.CARTESIAN),
                                new Point(19.000, 121.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))
                .build();

        path3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(19.000, 121.000, Point.CARTESIAN),
                                new Point(17.000, 125.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
                .build();

        path4 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(17.000, 125.000, Point.CARTESIAN),
                                new Point(19.000, 131.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))
                .build();

        path5 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(19.000, 131.500, Point.CARTESIAN),
                                new Point(17.000, 125.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
                .build();

        path6 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(17.000, 125.000, Point.CARTESIAN),
                                new Point(19.000, 132.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(20))
                .build();

        path7 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(19.000, 135.000, Point.CARTESIAN),
                                new Point(17.000, 125.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(15), Math.toRadians(-45))
                .build();

        path8 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(17.000, 125.000, Point.CARTESIAN),
                                new Point(50.000, 150.000, Point.CARTESIAN),
                                new Point(67.000, 95.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(90))
                .build();
    }



    public void autonomousPathUpdate(){
        switch (pathState){
            case 0:
                follower.followPath(path1);
                Claw.close();
                Arm.paraPosicao(2200);
                Extend.paraPosicao(3750);
                Angle.angulo.setPosition(-1);
                timer.reset();
                setPathState(1);
                break;
            case 1:


                if(!follower.isBusy() && timer.seconds() >= 1.5) {
                    Angle.angulo.setPosition(0.65);


                    timer.reset();
                    setPathState(2);
                }

                break;

            case 2:
                if (!follower.isBusy() && timer.seconds() >= 0.5){
                    Claw.open();
                    timer.reset();
                    setPathState(3);
                }
            /*case 2:
                if(!follower.isBusy()){
                    follower.followPath(path2, true);

                    setPathState(3);
                }
                break;
            case 3:

                if (!follower.isBusy()){
                    follower.followPath(path3, true);
                    follower.followPath(path4, true);
                    setPathState(4);
                }
                break;
            case 4:

                if (!follower.isBusy()){
                    follower.followPath(path5, true);
                    setPathState(5);
                }
                break;
            case 5:

                if (!follower.isBusy()){
                    follower.followPath(path6, true);
                    setPathState(6);
                }
                break;
            case 6:

                if (!follower.isBusy()){
                    follower.followPath(path7,true);
                    setPathState(7);

                }
                break;
            case 7:

                if (!follower.isBusy()){
                    follower.followPath(path8);
                    setPathState(-1);
                }
                break;**/

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
        Initialize robot = new Initialize(hardwareMap);

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(start);
        buildPaths();
    }

    @Override
    public void loop() {


        follower.update();
        autonomousPathUpdate();

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