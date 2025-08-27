package path;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

import robot.Subsystems.Extend;

public class fourSamples {

    private static Follower follower;

    public static Pose start = new Pose(8, 103.900, Math.toRadians(0));

    public static PathChain  path1, path2, path3, path4, path5, path6, path7, path8;

    //21.800 , 121.000, 22, 22

    public static void buildPaths(Follower f){
        follower = f;

        path1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(8.000, 103.900, Point.CARTESIAN),
                                new Point(16.500, 123.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
                .build();

        path2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(17, 121.800, Point.CARTESIAN),
                                new Point(22, 120.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(2.7))
                .build();

        path3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(22, 120, Point.CARTESIAN),
                                new Point(17, 121.800, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(2.7), Math.toRadians(-45))
                .build();

        path4 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(17.500, 119.300, Point.CARTESIAN),
                                new Point(16.500, 120, Point.CARTESIAN)
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
                                new Point(17.500, 121.200, Point.CARTESIAN),
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
                                new Point(17, 121.800, Point.CARTESIAN),
                                new Point(63.406720741599074, 133.65469293163383, Point.CARTESIAN),
                                new Point(60, 100, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(90))
                .build();
    }


}
