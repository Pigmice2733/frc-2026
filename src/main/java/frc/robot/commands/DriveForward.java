package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Drivetrain;

public class DriveForward {
    public DriveForward(Drivetrain dvt, double sec) {
        Commands.runOnce(() -> dvt.driveRobot(1.0, 0.0, 0.0))
                .withTimeout(2);
    }
}
