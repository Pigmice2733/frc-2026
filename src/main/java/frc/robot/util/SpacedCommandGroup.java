package frc.robot.util;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SpacedCommandGroup extends SequentialCommandGroup {

    /**
     * Create a new command group in which every command is executed after a given
     * amount of time
     * 
     * @param spacing  Delay before execution of each command
     * @param commands Commands to include in the command group
     */
    public SpacedCommandGroup(double spacing, Command... commands) {
        for (int i = 0; i < commands.length; i++) {
            CommandScheduler.getInstance().schedule(Commands.waitSeconds(spacing));
            addCommands(commands[i]);
        }
    }

    /**
     * Create a new command group in which every command is executed after a given
     * amount of time. Spacing can be disabled for individual commands.
     *
     * @param spacing  Delay before execution of each spaced command
     * @param spaced   Array stating whether each command is spaced or not spaced,
     *                 determined via true or false
     * @param commands Commands to include in the command group
     */
    public SpacedCommandGroup(double spacing, boolean[] spaced, Command... commands) {
        for (int i = 0; i < commands.length; i++) {
            if (spaced[i]) {
                addCommands(new DelayedCommand(spacing, commands[i]));
            } else {
                addCommands(commands[i]);
            }
        }
    }

    /**
     * Create a new command group in which every command is executed after a given
     * amount of time. Spacing can be different for each command.
     *
     * @param spacing Array stating the delay before execution of each command in seconds
     * @param commands
     */
    public SpacedCommandGroup(double[] spacing, Command... commands) {
        for (int i = 0; i < commands.length; i++) {
            addCommands(new DelayedCommand(spacing[i], commands[i]));
        }
    }
}