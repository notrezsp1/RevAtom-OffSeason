package robot.OpModes.Auto;



import static path.fourSamples.path1;
import static path.fourSamples.path2;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import config.constants.FConstants;
import config.constants.LConstants;
import path.fourSamples;
import robot.Subsystems.Angle;
import robot.Subsystems.Arm;
import robot.Subsystems.Claw;
import robot.Subsystems.Extend;
import robot.Subsystems.Initialize;

@Config
@Autonomous (name = "AutoOff", group = "FourHighBasket")

public class AutoOff extends OpMode{
    private Follower follower;

    private Timer pathTimer, opmodeTimer;
    private final boolean poseSet = false;
    private int pathState;
    private final boolean clips = false;


    private final ElapsedTime timer = new ElapsedTime();





    public void autonomousPathUpdate(){
        switch (pathState){
            case 0:
                follower.followPath(path1, true );
                Claw.close();
                Angle.angulo.setPosition(-1);
               Arm.toPosition(2800);
                Extend.toPosition(3300);
                timer.reset();
                setPathState(1);
                break;
           case 1:


                if(!follower.isBusy() && timer.seconds() >= 2) {
                    Angle.angulo.setPosition(0.65);
                    timer.reset();
                    setPathState(2);
                }
                break;

            case 2:
                if (!follower.isBusy() && timer.seconds() >= 1.5 ){
                    Claw.open();
                    timer.reset();
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy() && timer.seconds() >= 1){
                    Angle.angulo.setPosition(-1);
                    timer.reset();
                    setPathState(4);
                }
                break;

           case 4:
                if(!follower.isBusy() && timer.seconds() >= 1){
                    Extend.toPosition(0);
                    timer.reset();
                    setPathState(5);
                }

                break;

            case 5:
                if (!follower.isBusy() && timer.seconds() >= 0.8){
                    Arm.toPosition(700);
                    follower.followPath(path2, true);
                    timer.reset();
                    setPathState(6);
                }
                break;

           /*case 4:
                if (!follower.isBusy()){
                    follower.followPath(path3, true);
                    follower.followPath(path4, true);
                    setPathState(4);
                }

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
                break;*/

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
        follower.setStartingPose(fourSamples.start);
        fourSamples.buildPaths(follower);
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