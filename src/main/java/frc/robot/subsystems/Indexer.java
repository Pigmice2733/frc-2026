package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Constants.CanConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
    private SparkMax motorA;
    private SparkMax motorB;
    private double targetSpeed;

    public Indexer() {
        motorA = new SparkMax(CanConfig.INDEXER_ID_A, MotorType.kBrushless);
        motorB = new SparkMax(CanConfig.INDEXER_ID_B, MotorType.kBrushless);
    }

    @Override
    public void periodic() {
        setToSpeed();
        updateEntries();
    }

    public void updateEntries() {
        Constants.sendNumberToElastic("Indexer Motor 1 RPM", motorA.getEncoder().getVelocity(), 2);
        Constants.sendNumberToElastic("Indexer Motor 2 RPM", motorB.getEncoder().getVelocity(), 2);
        Constants.sendNumberToElastic("Indexer Target Speed", targetSpeed, 3);
    }

    public void setToSpeed() {
            motorA.set(targetSpeed);
            motorB.set(targetSpeed);
    }

    public void setTargetSpeed(double speed) {
        targetSpeed = speed;
    }

    public void stop() {
        targetSpeed = 0;
    }

    public Command setCommand(double speed) {
        return runOnce(() -> setTargetSpeed(speed));
    }

    public Command stopCommand() {
        return runOnce(() -> stop());
    }
}
