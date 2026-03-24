package frc.robot.util;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;

public class DelayedCommand extends Command {
    /**
     * Creates a command that runs after a certain amount of time
     * @param spacing Delay in seconds before running the command
     * @param command Command to run after the delay
     */
    public DelayedCommand(double spacing, Command command) {
        // addCommands(Commands.waitSeconds(spacing), command);
        CommandScheduler.getInstance().schedule(Commands.waitSeconds(spacing).andThen(command));
    }

    /**
     * Creates a command that runs after a certain condition has been fulfilled
     * @param condition What condition to wait for before the command is executed
     * @param command Command to run after the delay
     */
    public DelayedCommand(BooleanSupplier condition, Command command) {
        CommandScheduler.getInstance().schedule(Commands.waitUntil(condition).andThen(command));
    }
}
