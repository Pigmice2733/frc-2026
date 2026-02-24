package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Drivetrain;

public class DriveForward {
    public DriveForward(Drivetrain dvt, double sec) {
        Commands.runOnce(() -> dvt.drive(new Translation2d(1, 0), 0.0, false))
                .withTimeout(2);
    }
}
