package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants.CanConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private SparkMax motor;

    public Intake() {
        motor = new SparkMax(CanConfig.INTAKE_ID, MotorType.kBrushless);
    }

    public void stop() {
        motor.stopMotor();
    }

    public void setSpeed(double speed) {
        motor.set(speed);
    }
}
