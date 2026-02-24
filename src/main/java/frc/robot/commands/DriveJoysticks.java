package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DriveJoysticks extends Command {
  private Drivetrain dvt;
  private DoubleSupplier x, y, r;

  public DriveJoysticks(Drivetrain drivetrain, DoubleSupplier xSpeed, DoubleSupplier ySpeed, DoubleSupplier turnSpeed,
      BooleanSupplier robotOriented) {
    dvt = drivetrain;
    x = xSpeed;
    y = ySpeed;
    r = turnSpeed;
    
    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
      dvt.driveFieldOriented(new ChassisSpeeds(x.getAsDouble(), y.getAsDouble(), r.getAsDouble()));
  }
}