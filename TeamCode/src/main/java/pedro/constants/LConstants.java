package pedro.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class LConstants {


    static {
        ThreeWheelIMUConstants.forwardTicksToInches = 0.003;
        ThreeWheelIMUConstants.strafeTicksToInches =  0.00291049487428112;
        ThreeWheelIMUConstants.turnTicksToInches = 0.003;
        ThreeWheelIMUConstants.leftY = 6.2;
        ThreeWheelIMUConstants.rightY = -6;
        ThreeWheelIMUConstants.strafeX = -2;
        ThreeWheelIMUConstants.leftEncoder_HardwareMapName = "BLmotor";
        ThreeWheelIMUConstants.rightEncoder_HardwareMapName = "BRmotor";
        ThreeWheelIMUConstants.strafeEncoder_HardwareMapName = "FRmotor";
        ThreeWheelIMUConstants.leftEncoderDirection = Encoder.FORWARD;
        ThreeWheelIMUConstants.rightEncoderDirection = Encoder.REVERSE;
        ThreeWheelIMUConstants.strafeEncoderDirection = Encoder.FORWARD;
        ThreeWheelIMUConstants.IMU_HardwareMapName = "imu";
        ThreeWheelIMUConstants.IMU_Orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.DOWN, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
    }
}

//Left port : 2
//Right port : 1
//Srafe port : 0



