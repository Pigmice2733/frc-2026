package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.IndexerConfig;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class ShooterCommands {

    public static Command spinUpShooter(Shooter shtr) {
        return shtr.toggleOnCommand();
    }
    
    public static Command shootWaitIndex(Indexer indx, Shooter shtr) {
        return new SequentialCommandGroup(Commands.waitSeconds(1),
                shtr.toggleOnCommand(), 
                Commands.waitUntil(() -> shtr.atSetpoint()), 
                Commands.waitSeconds(0.5),
                new InstantCommand(() -> indx.setTargetSpeed(IndexerConfig.INDEXER_SPEED)));
    }
}
