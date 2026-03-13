package frc.robot.subsystems;

import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CanConfig;
import frc.robot.Constants.ShooterConfig;
import frc.robot.util.LoggedTunableNumber;

public class Shooter extends SubsystemBase {

    private TalonFX motor;
    private TalonFXConfiguration config;

    @AutoLogOutput(key = "Shooter Target Speed")
    private double targetSpeed;
    private VelocityVoltage velocityVoltage;

    private final LoggedTunableNumber kP = new LoggedTunableNumber("Shooter kP", ShooterConfig.KP);
    private final LoggedTunableNumber kI = new LoggedTunableNumber("Shooter kI", ShooterConfig.KI);
    private final LoggedTunableNumber kD = new LoggedTunableNumber("Shooter kD", ShooterConfig.KD);
    private final LoggedTunableNumber kS = new LoggedTunableNumber("Shooter kS", ShooterConfig.KS);
    private final LoggedTunableNumber kV = new LoggedTunableNumber("Shooter kV", ShooterConfig.KV);

    private final LoggedTunableNumber shootingSpeed = new LoggedTunableNumber("Shooting Speed", ShooterConfig.SHOOTING_SPEED);
    private double hubTarget;

    /**
     * Create a new Shooter.
     */
    public Shooter() {
        motor = new TalonFX(CanConfig.SHOOTER_ID);
        config = new TalonFXConfiguration();
        velocityVoltage = new VelocityVoltage(0).withSlot(0);

        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        config.Slot0.kP = ShooterConfig.KP;
        config.Slot0.kI = ShooterConfig.KI;
        config.Slot0.kD = ShooterConfig.KD;
        config.Slot0.kS = ShooterConfig.KS;
        config.Slot0.kV = ShooterConfig.KV;

        motor.getConfigurator().apply(config);
        stopMotor();
    }

    @Override
    public void periodic() {
        updateEntries();
    }

    public void updateEntries() {
        Constants.sendNumberToElastic("Shooter Target Speed", targetSpeed, 3);
        Constants.sendNumberToElastic("Shooter RPS", motor.getVelocity().getValueAsDouble(), 2);

        LoggedTunableNumber.ifChanged(
                hashCode(),
                () -> this.configPID(kP.get(), kI.get(), kD.get(), kV.get(), kS.get()),
                kP,
                kI,
                kD,
                kS,
                kV);

        LoggedTunableNumber.ifChanged(hashCode(), () -> hubTarget = shootingSpeed.get(), shootingSpeed);
    }

    /**
     * Set the speed of the shooter to a given amount of rotations per second.
     * 
     * @param rps Requested rotations per second
     */
    public void setTargetSpeed(double rps) {
        targetSpeed = rps;
        motor.setControl(velocityVoltage.withVelocity(targetSpeed));
    }

    /**
     * Set the speed of the shooter to a given amount of rotations per second.
     * 
     * @param rps Requested rotations per second
     */
    public Command setCommand(double rps) {
        return runOnce(() -> setTargetSpeed(rps));
    }

    /**
     * Set the shooter to neutral mode
     */
    public void stopMotor() {
        targetSpeed = 0;
        motor.setControl(new NeutralOut());
    }

    /**
     * Set the shooter to neutral mode
     */
    public Command stopCommand() {
        return runOnce(() -> stopMotor());
    }

    /**
     * Sets the speed of the shooter to the 
     */
    public Command shootCommand() {
        return setCommand(hubTarget);
    }

    public void configPID(double kP, double kI, double kD, double kS, double kV) {
        Slot0Configs pidConfigs = new Slot0Configs();
        motor.getConfigurator().refresh(pidConfigs);
        pidConfigs.kP = kP;
        pidConfigs.kI = kI;
        pidConfigs.kD = kD;
        pidConfigs.kS = kS;
        pidConfigs.kV = kV;
        motor.getConfigurator().apply(pidConfigs);
    }

    @AutoLogOutput(key = "Shooter At Setpoint?")
    public boolean atSetpoint() {
        return MathUtil.isNear(
                targetSpeed, motor.getVelocity().getValueAsDouble(), ShooterConfig.ERROR_TOLERANCE);
    }

}
