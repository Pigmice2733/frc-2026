package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants.CanConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    }

    public void setToSpeed() {
        boolean upToSpeed = SmartDashboard.getBoolean("Up To Speed?", false);

        if (upToSpeed) {
            motorA.set(targetSpeed);
            motorB.set(targetSpeed);
        }
    }

    public void stop() {
        targetSpeed = 0;
    }

    public void setTargetSpeed(double speed) {
        targetSpeed = speed;
    }


}
