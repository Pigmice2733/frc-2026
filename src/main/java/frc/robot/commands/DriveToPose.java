package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

public class DriveToPose extends Command {
    private final Drivetrain drivetrain;
    private Pose2d endPose, currentPose;
    private Transform2d path;

    private PIDController xPID, yPID, rPID;

    public DriveToPose(Drivetrain dvt, Transform2d path) {
        drivetrain = dvt;
        this.path = path;
        xPID = yPID = DrivetrainConfig.DRIVE_PID;
        rPID = DrivetrainConfig.TURN_PID;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        currentPose = drivetrain.getPose();
        endPose = currentPose.transformBy(path);

        xPID.setSetpoint(endPose.getX());
        yPID.setSetpoint(endPose.getY());
        rPID.setSetpoint(endPose.getRotation().getRadians());

        System.out.println("End Pose: " + endPose.getX());
    }

    @Override
    public void execute() {
        currentPose = drivetrain.getPose();
        if (endPose.getX() != currentPose.getX() || endPose.getY() != currentPose.getY()
                || endPose.getRotation().getDegrees() != currentPose.getRotation().getDegrees()) {
            if (endPose.getX() != currentPose.getX()) {
                if (endPose.getX() < currentPose.getX()) {
                    drivetrain.driveFieldOriented(new ChassisSpeeds(-0.75, 0, 0));
                }
                if (endPose.getX() > currentPose.getX()) {
                    drivetrain.driveFieldOriented(new ChassisSpeeds(0.75, 0, 0));
                }
            }
            if (endPose.getY() != currentPose.getY()) {
                if (endPose.getY() < currentPose.getY()) {
                    drivetrain.driveFieldOriented(new ChassisSpeeds(0, -0.75, 0));
                }
                if (endPose.getY() > currentPose.getY()) {
                    drivetrain.driveFieldOriented(new ChassisSpeeds(0, 0.75, 0));
                }
            }
            if (endPose.getRotation().getDegrees() != currentPose.getRotation().getDegrees()) {
                if (endPose.getRotation().getDegrees() < currentPose.getRotation().getDegrees()) {
                    drivetrain.driveFieldOriented(new ChassisSpeeds(0, 0, 1.5));
                }
                if (endPose.getRotation().getDegrees() > currentPose.getRotation().getDegrees()) {
                    drivetrain.driveFieldOriented(new ChassisSpeeds(0, 0, -1.5));
                }
            }
        } else {
            drivetrain.driveFieldOriented(new ChassisSpeeds(0, 0, 0));
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.driveFieldOriented(new ChassisSpeeds(0, 0, 0));
    }

    @Override
    public boolean isFinished() {
        return xPID.atSetpoint() && yPID.atSetpoint() && rPID.atSetpoint();
    }
}
