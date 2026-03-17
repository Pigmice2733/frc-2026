package frc.robot.subsystems.drivetrain;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;

public final class SwerveConstants {

    public final class ModuleLocations {
        public static final Distance FRONT_RIGHT_X = Inches.of(11);
        public static final Distance FRONT_RIGHT_Y = Inches.of(-11.5);

        public static final Distance FRONT_LEFT_X = Inches.of(11);
        public static final Distance FRONT_LEFT_Y = Inches.of(11.5);

        public static final Distance BACK_RIGHT_X = Inches.of(-11);
        public static final Distance BACK_RIGHT_Y = Inches.of(-11.5);

        public static final Distance BACK_LEFT_X = Inches.of(-11);
        public static final Distance BACK_LEFT_Y = Inches.of(11.5);
    }

    public static Translation2d[] getModuleLocations() {
        return new Translation2d[] {
                new Translation2d(ModuleLocations.FRONT_RIGHT_X, ModuleLocations.FRONT_RIGHT_Y),
                new Translation2d(ModuleLocations.FRONT_LEFT_X, ModuleLocations.FRONT_LEFT_Y),
                new Translation2d(ModuleLocations.BACK_RIGHT_X, ModuleLocations.BACK_RIGHT_Y),
                new Translation2d(ModuleLocations.BACK_LEFT_X, ModuleLocations.BACK_LEFT_Y)
        };
    }

    public final class FrontRight {
        public static final int frontRightDriveMotorID = 6;
        public static final int frontRightAngleMotorID = 3;
        public static final int frontRightEncoderID = 11;
        public static final Angle frontRightEncoderOffset = Degrees.of(353);
        public static final boolean frontRightDriveMotorInverted = true;
        public static final boolean frontRightAngleMotorInverted = true;
        public static final boolean frontRightEncoderInverted = false;
        public static final Distance frontRightX = ModuleLocations.FRONT_RIGHT_X;
        public static final Distance frontRightY = ModuleLocations.FRONT_RIGHT_Y;
    }

    public final class FrontLeft {
        public static final int frontLeftDriveMotorID = 8;
        public static final int frontLeftAngleMotorID = 5;
        public static final int frontLeftEncoderID = 10;
        public static final Angle frontLeftEncoderOffset = Degrees.of(79.4);
        public static final boolean frontLeftDriveMotorInverted = true;
        public static final boolean frontLeftAngleMotorInverted = true;
        public static final boolean frontLeftEncoderInverted = false;
        public static final Distance frontLeftX = ModuleLocations.FRONT_LEFT_X;
        public static final Distance frontLeftY = ModuleLocations.FRONT_LEFT_Y;
    }

    public final class BackRight {
        public static final int backRightDriveMotorID = 4;
        public static final int backRightAngleMotorID = 7;
        public static final int backRightEncoderID = 12;
        public static final Angle backRightEncoderOffset = Degrees.of(308.35);
        public static final boolean backRightDriveMotorInverted = true;
        public static final boolean backRightAngleMotorInverted = true;
        public static final boolean backRightEncoderInverted = false;
        public static final Distance backRightX = ModuleLocations.BACK_RIGHT_X;
        public static final Distance backRightY = ModuleLocations.BACK_RIGHT_Y;
    }

    public final class BackLeft {
        public static final int backLeftDriveMotorID = 9;
        public static final int backLeftAngleMotorID = 2;
        public static final int backLeftEncoderID = 13;
        public static final Angle backLeftEncoderOffset = Degrees.of(140.5);
        public static final boolean backLeftDriveMotorInverted = true;
        public static final boolean backLeftAngleMotorInverted = true;
        public static final boolean backLeftEncoderInverted = false;
        public static final Distance backLeftX = ModuleLocations.BACK_LEFT_X;
        public static final Distance backLeftY = ModuleLocations.BACK_LEFT_Y;
    }

    public final SwerveModuleConstants frontLeftModule = new SwerveModuleConstants(FrontLeft.frontLeftDriveMotorID,
            FrontLeft.frontLeftAngleMotorID, FrontLeft.frontLeftEncoderID, FrontLeft.frontLeftEncoderOffset,
            FrontLeft.frontLeftDriveMotorInverted, FrontLeft.frontLeftAngleMotorInverted,
            FrontLeft.frontLeftEncoderInverted, FrontLeft.frontLeftX, FrontLeft.frontLeftY);
    public final SwerveModuleConstants frontRightModule = new SwerveModuleConstants(FrontRight.frontRightDriveMotorID,
            FrontRight.frontRightAngleMotorID, FrontRight.frontRightEncoderID, FrontRight.frontRightEncoderOffset,
            FrontRight.frontRightDriveMotorInverted, FrontRight.frontRightAngleMotorInverted,
            FrontRight.frontRightEncoderInverted, FrontRight.frontRightX, FrontRight.frontRightY);
    public final SwerveModuleConstants backLeftModule = new SwerveModuleConstants(BackLeft.backLeftDriveMotorID,
            BackLeft.backLeftAngleMotorID, BackLeft.backLeftEncoderID, BackLeft.backLeftEncoderOffset,
            BackLeft.backLeftDriveMotorInverted, BackLeft.backLeftAngleMotorInverted, BackLeft.backLeftEncoderInverted,
            BackLeft.backLeftX, BackLeft.backLeftY);
    public final SwerveModuleConstants backRightModule = new SwerveModuleConstants(BackRight.backRightDriveMotorID,
            BackRight.backRightAngleMotorID, BackRight.backRightEncoderID, BackRight.backRightEncoderOffset,
            BackRight.backRightDriveMotorInverted, BackRight.backRightAngleMotorInverted,
            BackRight.backRightEncoderInverted, BackRight.backRightX, BackRight.backRightY);
}
