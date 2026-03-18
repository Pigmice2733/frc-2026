package frc.robot.subsystems.drivetrain;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;

public class SwerveModule {

    private final SparkMax drive;
    private final SparkMax angle;
    private final CANcoder cancoder;

    private final CANcoderConfiguration cancoderConfig;

    public SwerveModule(SwerveModuleConstants constants) {
        drive = new SparkMax(constants.driveMotorID, MotorType.kBrushless);
        angle = new SparkMax(constants.angleMotorID, MotorType.kBrushless);
        cancoder = new CANcoder(constants.encoderID);

        cancoderConfig = new CANcoderConfiguration();
        cancoderConfig.MagnetSensor.MagnetOffset = constants.encoderOffset.abs(Degrees);
        cancoderConfig.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;
        cancoderConfig.MagnetSensor.AbsoluteSensorDiscontinuityPoint = 0.5;

        SparkMaxConfig driveConfig = new SparkMaxConfig();
        driveConfig.idleMode(IdleMode.kBrake);
        driveConfig.inverted(constants.driveMotorInverted);
        driveConfig.encoder.positionConversionFactor(constants.driveMotorGearRatio);

        SparkMaxConfig angleConfig = new SparkMaxConfig();
        angleConfig.idleMode(IdleMode.kBrake);
        angleConfig.inverted(constants.angleMotorInverted);
        angleConfig.encoder.positionConversionFactor(constants.angleMotorGearRatio);

        drive.configure(driveConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        angle.configure(angleConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        cancoder.getConfigurator().apply(cancoderConfig);
    }

    public void setTurnPosition(Rotation2d rotation) {
        
    }

    public void setDriveVoltage(double voltage) {
        drive.setVoltage(voltage);
    }
}
