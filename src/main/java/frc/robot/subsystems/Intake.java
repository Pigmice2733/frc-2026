package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Constants.CanConfig;
import frc.robot.Constants.IntakeConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private SparkMax motor;
    private double targetSpeed;

    public Intake() {
        motor = new SparkMax(CanConfig.INTAKE_ID, MotorType.kBrushless);
    }

    @Override
    public void periodic() {
        setSpeed();
        updateEntries();
    }

    private void updateEntries() {
        Constants.sendNumberToElastic("Intake Speed", motor.get(), 2);
    }

    private void setSpeed() {
        motor.set(targetSpeed);
    }

    public void stop() {
        targetSpeed = 0;
    }

    public void set(double speed) {
        targetSpeed = speed;
    }

    public void toggle() {
        if (targetSpeed != 0) {
            targetSpeed = IntakeConfig.INTAKE_SPEED;
        } else {
            targetSpeed = 0;
        }
    }

    public void intake() {
        targetSpeed = Math.abs(targetSpeed);
    }

    public void outtake() {
        targetSpeed = -Math.abs(targetSpeed);
    }
}
