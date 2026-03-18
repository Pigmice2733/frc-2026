// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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

    public static final boolean TUNING_MODE = false;

    public static final double AXIS_THRESHOLD = 0.1;

    public static final class CanConfig {
        public static final int SHOOTER_ID = 20;
        public static final int INTAKE_ID = 21;
        public static final int INDEXER_ID_A = 22;
        public static final int INDEXER_ID_B = 23;
    }

    public static final class DrivetrainConfig {
        public static final double MAX_DRIVE_SPEED = 6.25; // m/s
        public static final double MAX_TURN_SPEED = 2.0 * Math.PI; // rad/s

        public static final PIDController DRIVE_PID = new PIDController(3.5, 0, 1.3);
        public static final double DRIVE_P = 2.9;

        public static final PIDController TURN_PID = new PIDController(3.5, 0, 0.1);
        public static final double TURN_P = 3.5;
    }

    public static final class ShooterConfig {
        public static final double SHOOTING_SPEED = 75; // default: 13'0" at 100, 6'2" + data says at 75,
                                                        // straight up: 0' at 75 85 100,
                                                        // point 3: 13'9" + 6' at 100, 12'2" + data at 85, 9'2" + data
                                                        // at 75
        public static final double ERROR_TOLERANCE = 5;

        public static final double KP = 0.1;
        public static final double KI = 0.0;
        public static final double KD = 0.01;
        public static final double KS = 0.12;
        public static final double KV = 0.11;
    }

    public static final class IndexerConfig {
        public static final double INDEXER_SPEED = -0.6;
    }

    public static final class IntakeConfig {
        public static final double INTAKE_SPEED = 0.6;
    }

    public static void sendNumberToElastic(String name, double num, double places) {
        double newNum = Math.round(num * Math.pow(10, places)) / Math.pow(10, places);
        SmartDashboard.putNumber(name, newNum);
    }
}
