package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;

public class IntakeCommands {

    public static Command intakeThenJostle(Intake intake) {
        return new SequentialCommandGroup(Commands.waitSeconds(0.5),
                intake.toggleCommand(), 
                Commands.waitSeconds(1),
                intake.jostle());
    }
}
