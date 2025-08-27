package robot.OpModes.Auto;



import static path.fourSamples.path1;
import static path.fourSamples.path2;
import static path.fourSamples.path3;
import static path.fourSamples.path8;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import pedro.constants.FConstants;
import pedro.constants.LConstants;
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

    private int pathState;
    private final ElapsedTime timer = new ElapsedTime();





    public void autonomousPathUpdate(){
        switch (pathState){
            case 0:
                follower.followPath(path1, true );
                Claw.close();
                setPathState(1);
                break;

            case 1:
                if (!follower.isBusy() && timer.seconds() > 1.5){
                    Arm.toPosition(2500, 1);
                    Extend.toPosition(3600);
                    Angle.angulo.setPosition(0.3);
                    setPathState(2);
                }
                break;

            case 2:
                if(!follower.isBusy() && timer.seconds() > 2.5){
                    Angle.angulo.setPosition(1);
                    setPathState(3);
                }
                break;

            case 3:
                if (!follower.isBusy() && timer.seconds() > 1.5){
                    Claw.open();

                    setPathState(4);
                }
                break;

            case 4:
                if (!follower.isBusy() && timer.seconds() > 0.5){
                    Angle.angulo.setPosition(0.3);
                    setPathState(5);
                }
                break;

            case 5:
                if (!follower.isBusy() && timer.seconds() > 0.5){
                    Extend.toPosition(0);
                    setPathState(6);
                }
                break;

            case 6:
                if (!follower.isBusy() && timer.seconds() > 1.5){
                    Arm.toPosition(800, 0.6);
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy() && timer.seconds() > 1.6){
                    follower.followPath(path2, true);
                    setPathState(8);
                }
                break;


            case 8:
                if (!follower.isBusy() && timer.seconds() > 2.5){
                    Extend.toPosition(3600);
                    setPathState(9);
                }
                break;
            case 9:
                if (!follower.isBusy() && timer.seconds() > 2.6){
                    Arm.toPosition(0,1);
                    setPathState(10);
                }
            case 10:
                if (!follower.isBusy() && timer.seconds() > 1.5){
                    Claw.close();
                    setPathState(-1);
                }
                break;

            case 11:
                if(!follower.isBusy() && timer.seconds()> 1.0){
                    follower.followPath(path3, true);
                    setPathState(-1);
                }
                break;
        }
    }


    public void setPathState(int pState){
        pathState = pState;
        timer.reset();
    }

    @Override
    public void init() {
        Initialize robot = new Initialize(hardwareMap);

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(fourSamples.start);
        fourSamples.buildPaths(follower);
        telemetry();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
    }

    @Override
    public void init_loop(){}

    @Override
    public void start(){
        timer.reset();
        setPathState(0);
    }

    @Override
    public void stop(){

    }
    public void telemetry(){
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("isBusy", follower.isBusy());
        telemetry.addData("Current Target", follower.getCurrentPath());
        telemetry.addData("Current Target of Extend", Extend.extend.getCurrentPosition());
        telemetry.addData("Current Target of Arm", Arm.arm.getCurrentPosition());
        telemetry.update();
    }
}