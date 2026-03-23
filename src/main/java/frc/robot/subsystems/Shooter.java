package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CanConfig;
import frc.robot.Constants.ShooterConfig;
import frc.robot.util.PIDConfig;

public class Shooter extends SubsystemBase {

    private TalonFX motor;
    private TalonFXConfiguration config;

    private double targetSpeed;
    private VelocityVoltage velocityVoltageRequest;

    private PIDConfig pidConfig;

    private double hubSpeed = ShooterConfig.SHOOTING_SPEED;
    private double setpoint = 0;

    private boolean hubRelative = false;

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
        config.CurrentLimits.SupplyCurrentLimit = 60;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        pidConfig.configTalonFX(motor, config);
    }

    @Override
    public void periodic() {
        updateEntries();
        if (DriverStation.isDisabled() || setpoint == 0) stopMotor();
        if (setpoint != 0 && hubRelative) {
            setTargetSpeed(hubSpeed);
        } else if (setpoint != 0) {
            setTargetSpeed(setpoint);
        }
    }

    /**
     * Update values being sent to elastic
     */
    public void updateEntries() {
        Constants.sendNumberToElastic("Shooter Target Speed", setpoint, 3);
        Constants.sendNumberToElastic("Shooter RPS", motor.getVelocity().getValueAsDouble(), 2);
        SmartDashboard.putBoolean("Shooter At Setpoint?", atSetpoint());

        if (Constants.TUNING_MODE) {
            pidConfig.updateValues();
            configPID(pidConfig.getValues());
        }

        hubSpeed = SmartDashboard.getNumber("Flywheel RPS Calculation", ShooterConfig.SHOOTING_SPEED);
        Constants.sendNumberToElastic("Hub Speed", hubSpeed, 2);

        SmartDashboard.putBoolean("Hub Relative", hubRelative);
    }

    /**
     * Set the speed of the shooter to a given amount of rotations per second.
     * 
     * @param rps Requested rotations per second
     */
    public void setTargetSpeed(double rps) {
        if (Math.abs(rps) > 100) {
            rps = 100;
        }
        motor.setControl(velocityVoltageRequest.withVelocity(rps));
    }

    /**
     * Set the shooter to neutral mode
     */
    public void stopMotor() {
        targetSpeed = 0;
        motor.setControl(new NeutralOut());
    }

    public void toggleOn() {
        hubRelative = true;
        setpoint = hubSpeed;
    }

    public void toggleOff() {
        hubRelative = false;
        setpoint = 0;
    }

    public void toggleOnConstant(double rps) {
        setpoint = rps;
    }

    public Command toggleOnCommand() {
        return runOnce(() -> toggleOn());
    }

    public Command toggleOffCommand() {
        return runOnce(() -> toggleOff());
    }

    public Command toggleOnConstantCommand(double rps) {
        return runOnce(() -> toggleOnConstant(rps));
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
