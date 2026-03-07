package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class PositionAndRotateAndScore extends SequentialCommandGroup{

    boolean blueAlliance = DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Blue;
    
    public PositionAndRotateAndScore(Drivetrain dvt, Shooter shtr, Indexer indx) {
        addCommands(
            dvt.driveToPose(new Pose2d(dvt.getPose().getX() + 1, dvt.getPose().getY(), new Rotation2d())),
            new RotateAndScore(dvt, shtr, indx));
    }
}
