// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final double AXIS_THRESHOLD = 0.1;

    public static final class DrivetrainConfig {
        public static final double MAX_DRIVE_SPEED = 6.25; // m/s
        public static final double MAX_TURN_SPEED = 2.0 * Math.PI; // rad/s

        public static final PIDController DRIVE_PID = new PIDController(3.5, 0, 1.3);
        public static final double DRIVE_P = 2.9;

        public static final PIDController TURN_PID = new PIDController(3.5, 0, 0.1);
        public static final double TURN_P = 3.5;
    }

    public static final class FieldConstants {
        public static final AprilTagFieldLayout APRIL_TAG_LAYOUT = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

        public static final double HUB_WIDTH = 1.1938; // meters

        public static final double BLUE_HUB_CENTER_X = APRIL_TAG_LAYOUT.getTagPose(26).get().getX() + HUB_WIDTH / 2;
        public static final double BLUE_HUB_CENTER_Y = APRIL_TAG_LAYOUT.getTagPose(21).get().getY() + HUB_WIDTH / 2;

        public static final double RED_HUB_CENTER_X = APRIL_TAG_LAYOUT.getTagPose(10).get().getX() + HUB_WIDTH / 2;
        public static final double RED_HUB_CENTER_Y = APRIL_TAG_LAYOUT.getTagPose(5).get().getY() + HUB_WIDTH / 2;
    }

    public static void sendNumberToElastic(String name, double num, double places) {
        double newNum = Math.round(num * Math.pow(10, places)) / Math.pow(10, places);
        SmartDashboard.putNumber(name, newNum);
    }
}
