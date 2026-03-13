package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.IndexerConfig;
import frc.robot.subsystems.Indexer;

public class JostleFuel extends SequentialCommandGroup {
    public JostleFuel(Indexer indx) {
        addCommands(indx.setCommand(IndexerConfig.INDEXER_SPEED), Commands.waitSeconds(0.25),
                indx.setCommand(-IndexerConfig.INDEXER_SPEED), Commands.waitSeconds(0.25));
    }

    public Command jostleFuelRepeatedly(Indexer indx, int length) {
        return new JostleFuel(indx).repeatedly().withTimeout(length).andThen(Commands.none());
    }
}
