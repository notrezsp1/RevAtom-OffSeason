package pedro.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.THREE_WHEEL;

        FollowerConstants.leftFrontMotorName = "FLmotor";
        FollowerConstants.leftRearMotorName = "BLmotor";
        FollowerConstants.rightFrontMotorName = "FRmotor";
        FollowerConstants.rightRearMotorName = "BRmotor";

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.REVERSE;

        FollowerConstants.mass = 10.5;

        FollowerConstants.xMovement = (54.71856302885552 + 53.911457926509435 + 53.19606524142703)/ 3;
        FollowerConstants.yMovement = (32.39116568603613+38.398982876912484 + 36.57152160820453)/3;
        ;

        FollowerConstants.forwardZeroPowerAcceleration = (-38.36594256449782 -  -36.54677691157878 - -36.074312185378936)/3;
        FollowerConstants.lateralZeroPowerAcceleration = (- 52.832773017773546732628701 - -64.09170051001064 - -70.81132127072327)/3;
        FollowerConstants.translationalPIDFCoefficients.setCoefficients(
                0.1,
                0,
                0.01,
                0);
        FollowerConstants.translationalIntegral.setCoefficients(
                0,
                0,
                0,
                0);
        FollowerConstants.translationalPIDFFeedForward = 0.01;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(
                0.075,
                0,
                0.05,
                0);
        FollowerConstants.secondaryTranslationalIntegral.setCoefficients(
                0,
                0,
                0,
                0);
        FollowerConstants.secondaryTranslationalPIDFFeedForward = 0.0005;

        FollowerConstants.headingPIDFCoefficients.setCoefficients(
                5,
                0,
                0.5,
                0);
        FollowerConstants.headingPIDFFeedForward = 0.01;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(
                1.5,
                0,
                0.1,
                0);
        FollowerConstants.secondaryHeadingPIDFFeedForward = 0.0005;

        FollowerConstants.drivePIDFCoefficients.setCoefficients(
                0.01,
                0,
                0.0001,
                0.6,
                0);
        FollowerConstants.drivePIDFFeedForward = 0.01;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(
                0.02,
                0,
                0.0005,
                0.6,
                0);
        FollowerConstants.secondaryDrivePIDFFeedForward = 0.01;

        FollowerConstants.zeroPowerAccelerationMultiplier = 4;
        FollowerConstants.centripetalScaling = 0.0005;
        FollowerConstants.pathEndTimeoutConstraint = 50;
        FollowerConstants.pathEndTValueConstraint = 0.95;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.2;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
        FollowerConstants.useSecondaryTranslationalPID = true;
        FollowerConstants.useSecondaryHeadingPID = true;
        FollowerConstants.useSecondaryDrivePID = true;
    }
}
