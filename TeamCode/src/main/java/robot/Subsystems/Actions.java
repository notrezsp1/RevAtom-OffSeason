package robot.Subsystems;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

public class Actions extends SequentialCommandGroup {

    public Actions() {

        addCommands(
                new InstantCommand(Arm::paraCima),
                new InstantCommand(Extend::estender),
                new InstantCommand(Angle::cima),
                new InstantCommand(Claw::open)
        );
    }
}
