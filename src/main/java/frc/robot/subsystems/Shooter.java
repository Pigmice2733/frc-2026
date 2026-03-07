package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CanConfig;
import frc.robot.Constants.ShooterConfig;

public class Shooter extends SubsystemBase {

    private TalonFX motor;
    private TalonFXConfiguration config;
    private double targetSpeed;
    private Slot0Configs slot0Configs;
    private VelocityVoltage velocityVoltage;

    public Shooter() {
        motor = new TalonFX(CanConfig.SHOOTER_ID);
        velocityVoltage = new VelocityVoltage(0).withSlot(0);

        config = new TalonFXConfiguration()
                .withMotorOutput(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Coast));
        slot0Configs = new Slot0Configs();
        slot0Configs.kS = 0.12;
        slot0Configs.kV = 0.11;
        slot0Configs.kP = 0.1;
        slot0Configs.kD = 0.01;
        motor.getConfigurator().apply(config);
        motor.getConfigurator().apply(slot0Configs);
    }

    @Override
    public void periodic() {
        updateEntries();
        // setSpeed();
    }

    public void updateEntries() {
        Constants.sendNumberToElastic("Shooter Shooting Speed", ShooterConfig.SHOOTING_SPEED, 2);
        Constants.sendNumberToElastic("Shooter Target Speed", targetSpeed, 3);
        Constants.sendNumberToElastic("Shooter RPS", motor.getVelocity().getValueAsDouble(), 2);
    }

    public void setSpeed() {
        motor.set(targetSpeed);
    }

    public void setTargetSpeed(double rps) {
        targetSpeed = rps;
        motor.setControl(velocityVoltage.withVelocity(targetSpeed));
    }
    
    public void stopMotor() {
        targetSpeed = 0;
        motor.setControl(new NeutralOut());
    }

    public Command incrementMotor(double speed) {
        return new InstantCommand(() -> setTargetSpeed(targetSpeed + speed));
    }
}
