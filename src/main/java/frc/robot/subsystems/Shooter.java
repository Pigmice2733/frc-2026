package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    TalonFX motor;
    TalonFXConfiguration config;
    double targetSpeed;

    public Shooter() {
        motor = new TalonFX(0);

        config = new TalonFXConfiguration()
                .withMotorOutput(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Coast));
        motor.getConfigurator().apply(config);
    }

    @Override
    public void periodic() {
        setSpeed();
    }

    public void updateEntries() {
        Constants.sendNumberToElastic("Shooter Target Speed", targetSpeed, 3);
        Constants.sendNumberToElastic("Shooter RPM", motor.getVelocity().getValueAsDouble() * 60, 2);
    }

    public void setSpeed() {
        motor.set(targetSpeed);
    }

    public void setMotor(double speed) {
        targetSpeed = speed;
    }

    public void stopMotor() {
        targetSpeed = 0;
    }

}
