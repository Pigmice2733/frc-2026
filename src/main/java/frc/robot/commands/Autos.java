package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.IndexerConfig;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.util.DelayedCommand;
import frc.robot.util.SpacedCommandGroup;

public class Autos {

    public static Command score(Shooter shtr, Indexer indx) {
        return new SpacedCommandGroup(1, 
                shtr.shootCommand(),
                new DelayedCommand(() -> shtr.atSetpoint(), indx.setCommand(IndexerConfig.INDEXER_SPEED)));
    }

    public static Command rotateScore(Drivetrain dvt, Shooter shtr, Indexer indx) {
        boolean[] arr = {true, false};
        return new SpacedCommandGroup(1, arr,
                dvt.rotateToHub(),
                Autos.score(shtr, indx));
    }

    public static Command positionRotateScore(Drivetrain dvt, Shooter shtr, Indexer indx) {
        boolean[] arr = {true, false};
        return new SpacedCommandGroup(1, arr, 
                dvt.positionXToHub(),
                Autos.rotateScore(dvt, shtr, indx));
    }
}
