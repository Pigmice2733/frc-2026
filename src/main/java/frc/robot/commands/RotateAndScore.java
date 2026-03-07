package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.IndexerConfig;
import frc.robot.Constants.ShooterConfig;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class RotateAndScore extends SequentialCommandGroup {

    public RotateAndScore(Drivetrain dvt, Shooter shtr, Indexer indx) {
        addCommands(
            Commands.waitSeconds(1),
            dvt.rotateToHub(),
            Commands.waitSeconds(1),
            new InstantCommand(() -> shtr.setTargetSpeed(ShooterConfig.SHOOTING_SPEED)),
            Commands.waitSeconds(1),
            new InstantCommand(() -> indx.setTargetSpeed(IndexerConfig.INDEXER_SPEED))
        );
    }
}
