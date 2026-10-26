package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutoCommands {
    
    public static Command shoot(Intake intk, Indexer indx, Shooter shtr) {
        return new SequentialCommandGroup(ShooterCommands.shootWaitIndex(indx, shtr), 
                IntakeCommands.intakeThenJostle(intk));
    }

    public static Command shootAfterMove(Intake intk, Indexer indx, Shooter shtr) {
        return new SequentialCommandGroup(Commands.waitSeconds(0.25), 
                shoot(intk, indx, shtr));
    }
}
