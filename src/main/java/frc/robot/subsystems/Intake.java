package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Constants.CanConfig;
import frc.robot.Constants.IntakeConfig;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private SparkMax motor;
    private double targetSpeed;
    private boolean on;

    public Intake() {
        motor = new SparkMax(CanConfig.INTAKE_ID, MotorType.kBrushless);
    }

    @Override
    public void periodic() {
        setSpeed();
        updateEntries();
    }

    private void updateEntries() {
        Constants.sendNumberToElastic("Intake RPM", motor.getEncoder().getVelocity(), 2);
        Constants.sendNumberToElastic("Intake Target Speed", targetSpeed, 3);
        SmartDashboard.putBoolean("Intake On", on);
    }

    private void setSpeed() {
        if (DriverStation.isDisabled())
            stop();
        if (on)
            motor.set(targetSpeed);
        if (!on)
            stop();
    }

    public void stop() {
        targetSpeed = 0;
        motor.stopMotor();
    }

    public void set(double speed) {
        targetSpeed = speed;
    }

    public void toggle() {
        if (targetSpeed == 0) {
            on = true;
            targetSpeed = IntakeConfig.INTAKE_SPEED;
        } else {
            on = false;
            targetSpeed = 0;
        }
    }

    public void intake() {
        targetSpeed = Math.abs(targetSpeed);
    }

    public void outtake() {
        targetSpeed = -Math.abs(targetSpeed);
    }

    public Command jostle() {
        return runOnce(() -> outtake())
            .andThen(Commands.waitSeconds(0.125))
            .finallyDo(() -> intake());
    }
}
