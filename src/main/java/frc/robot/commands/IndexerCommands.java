package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.IndexerConfig;
import frc.robot.subsystems.Indexer;

public class IndexerCommands {

    public static Command runIndexer(Indexer indx) {
        return indx.setCommand(IndexerConfig.INDEXER_SPEED);
    }

    public static Command stopIndexer(Indexer indx) {
        return indx.stopCommand();
    }
}
