package frc.robot.util;

import choreo.auto.AutoChooser;
import choreo.auto.AutoRoutine;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.Supplier;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

public class LoggedAutoChooser extends AutoChooser {
    private final LoggedDashboardChooser<String> dashboardChooser;

    private final AutoChooser autoChooser = new AutoChooser();

    public LoggedAutoChooser(String key) {
        dashboardChooser = new LoggedDashboardChooser<>(key);
        dashboardChooser.addDefaultOption("Nothing", "Nothing");
    }

    public void update() {
        autoChooser.select(dashboardChooser.get());
    }

    public AutoChooser addRoutine(String name, Supplier<AutoRoutine> routine) {
        dashboardChooser.addOption(name, name);
        autoChooser.addRoutine(name, routine);
        return this;
    }

    public AutoChooser addCmd(String name, Supplier<Command> command) {
        dashboardChooser.addOption(name, name);
        autoChooser.addCmd(name, command);
        return this;
    }

    public Command selectedCommand() {
        return autoChooser.selectedCommand();
    }

    public Command selectedCommandScheduler() {
        return autoChooser.selectedCommandScheduler();
    }
}
