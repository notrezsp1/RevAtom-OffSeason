package robot.OpModes.TeleOp;



import static config.Robot.autoEnd;

import config.Robot;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Dual Drive", group = "...RevAtom")
public class DualDrive extends OpMode {
    Robot r;

    @Override
    public void init() {
         r = new Robot( autoEnd);
    }

    @Override
    public void start(){
        r.rStart();
    }
    @Override
    public void loop() {
        r.dualControls();
    }
}
