package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.IndexerConfig;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class Shoot extends SequentialCommandGroup{
    
    public Shoot(Indexer indx, Shooter shtr) {
        addCommands(Commands.waitSeconds(1),
                shtr.toggleOnCommand(), 
                Commands.waitUntil(() -> shtr.atSetpoint()), 
                Commands.waitSeconds(0.5),
                new InstantCommand(() -> indx.setTargetSpeed(IndexerConfig.INDEXER_SPEED)));
    }
}
