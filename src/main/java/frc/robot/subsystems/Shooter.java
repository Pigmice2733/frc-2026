package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CanConfig;
import frc.robot.Constants.ShooterConfig;
import frc.robot.util.DashboardNumber;
import frc.robot.util.PIDConfig;

public class Shooter extends SubsystemBase {

    private TalonFX motor;
    private TalonFXConfiguration config;

    private double targetSpeed;
    private VelocityVoltage velocityVoltageRequest;

    private PIDConfig pidConfig;

    private DashboardNumber shootingSpeed;

    /**
     * Create a new Shooter.
     */
    public Shooter() {
        pidConfig = new PIDConfig("Shooter PID", ShooterConfig.KP, ShooterConfig.KI, ShooterConfig.KD, ShooterConfig.KS,
                ShooterConfig.KV);

        motor = new TalonFX(CanConfig.SHOOTER_ID);
        config = new TalonFXConfiguration();
        velocityVoltageRequest = new VelocityVoltage(0).withSlot(0);

        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        pidConfig.configTalonFX(motor, config);

        shootingSpeed = new DashboardNumber("Hub Target RPS", ShooterConfig.SHOOTING_SPEED, 2);

        stopMotor();
    }

    @Override
    public void periodic() {
        updateEntries();
        if (targetSpeed != shootingSpeed.getAsDouble() && targetSpeed != 0) {
            setTargetSpeed(shootingSpeed.getAsDouble());
        }
    }

    /**
     * Update values being sent to elastic
     */
    public void updateEntries() {
        Constants.sendNumberToElastic("Shooter Target Speed", targetSpeed, 3);
        Constants.sendNumberToElastic("Shooter RPS", motor.getVelocity().getValueAsDouble(), 2);
        SmartDashboard.putBoolean("Shooter At Setpoint?", atSetpoint());

        if (Constants.TUNING_MODE) {
            pidConfig.updateValues();
            configPID(pidConfig.getValues());
        }

        shootingSpeed.updateValue();
    }

    /**
     * Set the speed of the shooter to a given amount of rotations per second.
     * 
     * @param rps Requested rotations per second
     */
    public void setTargetSpeed(double rps) {
        targetSpeed = rps;
        motor.setControl(velocityVoltageRequest.withVelocity(targetSpeed));
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
     * Sets the speed of the shooter to the hub target RPS
     */
    public Command shootCommand() {
        return setCommand(shootingSpeed.getAsDouble());
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

    public void configPID(double[] pidValues) {
        configPID(pidValues[0], pidValues[1], pidValues[2], pidValues[4], pidValues[5]);
    }

    public boolean atSetpoint() {
        return MathUtil.isNear(
                targetSpeed, motor.getVelocity().getValueAsDouble(), ShooterConfig.ERROR_TOLERANCE);
    }
}
