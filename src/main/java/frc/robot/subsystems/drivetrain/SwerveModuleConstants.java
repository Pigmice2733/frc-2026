package frc.robot.subsystems.drivetrain;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;

public class SwerveModuleConstants {

    public int driveMotorID, angleMotorID, encoderID;
    public Angle encoderOffset;
    public boolean driveMotorInverted, angleMotorInverted, encoderInverted;
    public Distance xPosition, yPosition;
    public final double driveMotorGearRatio = 18.75;
    public final double angleMotorGearRatio = 5.9;

    public SwerveModuleConstants(int driveMotorID, int angleMotorID, int encoderID, Angle encoderOffset, boolean driveMotorInverted, boolean angleMotorInverted, boolean encoderInverted, Distance xPosition, Distance yPosition) {
        this.driveMotorID = driveMotorID;
        this.angleMotorID = angleMotorID;
        this.encoderOffset = encoderOffset;
        this.driveMotorInverted = driveMotorInverted;
        this.angleMotorInverted = angleMotorInverted;
        this.encoderInverted = encoderInverted;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}
