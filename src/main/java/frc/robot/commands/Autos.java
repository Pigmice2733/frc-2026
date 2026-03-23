package frc.robot.commands;

import java.time.Instant;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.FieldConstants;
import frc.robot.Constants.IndexerConfig;
import frc.robot.FieldConstants.Hub;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.util.DelayedCommand;
import frc.robot.util.SpacedCommandGroup;

public class Autos {

    public static Command scoreTrenchBlue(Shooter shtr, Indexer indx) {
        return new SpacedCommandGroup(1, 
                shtr.toggleOnConstantCommand(100),
                new DelayedCommand(() -> shtr.atSetpoint(), indx.setCommand(IndexerConfig.INDEXER_SPEED)));
    }

    public static Command scoreCenterBlue(Drivetrain dvt, Shooter shtr, Indexer indx) {
        Pose2d startingPose = new Pose2d(Hub.topCenterPoint.getX() - Units.inchesToMeters(43.75), FieldConstants.fieldWidth / 2, Rotation2d.fromDegrees(-90));
        Pose2d endingPose = new Pose2d(startingPose.getX() - 3, startingPose.getY(), startingPose.getRotation());
        // dvt.getSwerve().setGyro(new Rotation3d(0, 0, -90));
        // dvt.getSwerve().resetOdometry(startingPose);
        return new SequentialCommandGroup(new DriveToPose(dvt, new Transform2d(startingPose, endingPose)), 
                Commands.waitSeconds(1), 
                shtr.toggleOnCommand(), 
                Commands.waitSeconds(1), 
                new InstantCommand(() -> indx.setTargetSpeed(IndexerConfig.INDEXER_SPEED)));
    }

    public static Command scoreCenterRed(Drivetrain dvt, Shooter shtr, Indexer indx) {
        Pose2d startingPose = new Pose2d(Hub.oppTopCenterPoint.getX() + Units.inchesToMeters(43.75), FieldConstants.fieldWidth / 2, Rotation2d.fromDegrees(-90));
        Pose2d endingPose = new Pose2d(startingPose.getX() + 3, startingPose.getY(), startingPose.getRotation());
        return new SequentialCommandGroup(new InstantCommand(() -> dvt.getSwerve().setGyroOffset(new Rotation3d(0, 0, 90))), 
                // new InstantCommand(() -> dvt.getSwerve().resetOdometry(startingPose)), 
                dvt.driveRight(), 
                Commands.waitSeconds(1), 
                shtr.toggleOnCommand(), 
                Commands.waitSeconds(1), 
                new InstantCommand(() -> indx.setTargetSpeed(IndexerConfig.INDEXER_SPEED)));
    }

    public static Command rotateScore(Drivetrain dvt, Shooter shtr, Indexer indx) {
        boolean[] arr = {true, false};
        return new SpacedCommandGroup(1, arr,
                dvt.rotateToHub(),
                Autos.scoreTrenchBlue(shtr, indx));
    }

    public static Command positionRotateScore(Drivetrain dvt, Shooter shtr, Indexer indx) {
        boolean[] arr = {true, false};
        return new SpacedCommandGroup(1, arr, 
                dvt.positionXToHub(),
                Autos.rotateScore(dvt, shtr, indx));
    }
}
