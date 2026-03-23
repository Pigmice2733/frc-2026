// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.IndexerConfig;
import frc.robot.commands.DriveJoysticks;
import frc.robot.commands.Autos;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
    private final Drivetrain drivetrain;
    @SuppressWarnings("unused")
    private final Vision vision;
    private final Shooter shooter;
    private final Intake intake;
    private final Indexer indexer;

    private final CommandXboxController driver;
    private final CommandXboxController operator;
    private final Controls controls;

    private boolean robotOriented;

    private final SendableChooser<Command> autoChooser = new SendableChooser<Command>();

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

    private void setRobotOriented(boolean value) {
        this.robotOriented = value;
    }

    private void configureBindings() {
        // DRIVER
        driver.a().onTrue(new InstantCommand(() -> drivetrain.zeroGyroWithAlliance()));

        driver.rightBumper().onTrue( new InstantCommand(() -> setRobotOriented(true)));
        driver.rightBumper().onFalse( new InstantCommand(() -> setRobotOriented(false)));

        // driver.x().onTrue(drivetrain.rotateToHub());

        // OPERATOR
        operator.leftBumper().onTrue(shooter.toggleOnCommand());
        operator.leftTrigger().onTrue(shooter.toggleOffCommand());

        operator.rightBumper().whileTrue(indexer.setCommand(IndexerConfig.INDEXER_SPEED)); // forward
        operator.rightBumper().whileFalse(indexer.stopCommand());
        operator.rightTrigger().whileTrue(indexer.setCommand(-IndexerConfig.INDEXER_SPEED)); // backward
        operator.rightTrigger().whileFalse(indexer.stopCommand());

        operator.y().whileTrue(new InstantCommand(() -> intake.toggle()));
        operator.b().onTrue(new InstantCommand(() -> intake.intake()));
        operator.a().onTrue(new InstantCommand(() -> intake.outtake()));
        operator.x().onTrue(intake.jostle());
    }

    private void buildAutoChooser() {
        autoChooser.addOption("None", Commands.none());
        autoChooser.addOption("Score Trench - Blue", Autos.scoreTrenchBlue(shooter, indexer));
        autoChooser.addOption("Score Center - Blue", Autos.scoreCenterBlue(drivetrain, shooter, indexer));
        autoChooser.addOption("Score Center - Red", Autos.scoreCenterRed(drivetrain, shooter, indexer));
        // autoChooser.addOption("Rotate, Score", Autos.rotateScore(drivetrain, shooter, indexer));
        // autoChooser.addOption("Position, Rotate, Score", Autos.positionRotateScore(drivetrain, shooter, indexer));

        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    public void autoInit() {

    }
}
