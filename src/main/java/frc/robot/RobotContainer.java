// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DriveJoysticks;
import frc.robot.commands.DriveToPose;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

@SuppressWarnings("unused")
public class RobotContainer {
    private final Drivetrain drivetrain;
    private final Vision vision;
    private final Shooter shooter;
    private final Intake intake;
    private final Indexer indexer;

    private final CommandXboxController driver;
    private final CommandXboxController operator;
    private final Controls controls;

    private boolean robotOriented;

    private SendableChooser<Command> autoChooser;

    public RobotContainer() {
        driver = new CommandXboxController(0);
        operator = new CommandXboxController(1);
        controls = new Controls(driver, operator);

        drivetrain = new Drivetrain();
        vision = new Vision();
        shooter = new Shooter();
        intake = new Intake();
        indexer = new Indexer();

        robotOriented = false;

        autoChooser = new SendableChooser<Command>();

        configureBindings();
        configureDefaultCommands();
        buildAutoChooser();
    }

    public void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new DriveJoysticks(
                drivetrain,
                controls::getDriveSpeedX,
                controls::getDriveSpeedY,
                controls::getTurnSpeed,
                () -> robotOriented));
    }

    private void configureBindings() {
        // DRIVER
        driver.a().onTrue(drivetrain.resetOdometryAlliance());
        
        driver.x().whileTrue(drivetrain.rotateToHub());
        
        // OPERATOR
        operator.leftBumper().onTrue(new InstantCommand(() -> shooter.setTargetSpeed(shooter.shootingSpeed)));
        operator.leftTrigger().onTrue(new InstantCommand(() -> shooter.stopMotor()));

        operator.rightBumper().whileTrue(new InstantCommand(() -> indexer.setTargetSpeed(0.2)));
        operator.rightBumper().whileFalse(new InstantCommand(() -> indexer.setTargetSpeed(0)));

        operator.y().whileTrue(new InstantCommand(() -> intake.toggle()));
        operator.b().onTrue(new InstantCommand(() -> intake.intake()));
        operator.a().onTrue(new InstantCommand(() -> intake.outtake()));
    }

    private void buildAutoChooser() {
        autoChooser.setDefaultOption("None", Commands.none());
        autoChooser.addOption("Drive Forward",
                new DriveToPose(drivetrain, new Transform2d(6.5, 0, new Rotation2d(0))));
        autoChooser.addOption("Drive Right", new DriveToPose(drivetrain, new Transform2d(0, 2, new Rotation2d(0))));
        autoChooser.addOption("Drive Diagonal", drivetrain.driveToPose(new Pose2d(2, 3, new Rotation2d(Math.toRadians(90)))));

        SmartDashboard.putData("Auto Chooser", autoChooser);

    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    public void autoInit() {

    }
}
