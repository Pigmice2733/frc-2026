package frc.robot.util;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SpacedCommandGroup extends SequentialCommandGroup{
    public SpacedCommandGroup(double spacing, Command... commands) {
        for (int i = 0; i < commands.length; i++) {
            addCommands(new DelayedCommand(spacing, commands[i]));
        }
    }
}