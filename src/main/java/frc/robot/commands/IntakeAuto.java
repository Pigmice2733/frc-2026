package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;

public class IntakeAuto extends SequentialCommandGroup {
    public IntakeAuto(Intake intake) {
        addCommands(intake.toggleCommand(), 
                Commands.waitSeconds(3),
                intake.jostle(),
                Commands.waitSeconds(2), 
                intake.jostle(),
                Commands.waitSeconds(2), 
                intake.jostle(),
                Commands.waitSeconds(2), 
                intake.jostle());
    }
}
