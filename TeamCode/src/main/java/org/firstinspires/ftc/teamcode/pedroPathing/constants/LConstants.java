package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = 0.0029;
        ThreeWheelConstants.strafeTicksToInches = 0.0029;
        ThreeWheelConstants.turnTicksToInches = 0.003;
        ThreeWheelConstants.leftY = 6.2;
        ThreeWheelConstants.rightY = -6;
        ThreeWheelConstants.strafeX = -2;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "BLmotor";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "BRmotor";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "FRmotor";
        ThreeWheelConstants.leftEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.rightEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
    }
}

//Left port : 2n
//Right port : 1
//Srafe port : 0



