package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants.CanConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
    private SparkMax motorA;
    private SparkMax motorB;

    public Indexer() {
        motorA = new SparkMax(CanConfig.INDEXER_ID_A, MotorType.kBrushless);
        motorB = new SparkMax(CanConfig.INDEXER_ID_B, MotorType.kBrushless);
    }

    public void stop() {
        motorA.stopMotor();
        motorB.stopMotor();
    }

    public void setSpeed(double speed) {
        motorA.set(speed);
        motorB.set(speed);
    }


}
