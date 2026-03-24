// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.IndexerConfig;
import frc.robot.commands.AutoCommands;
import frc.robot.commands.DriveJoysticks;
import frc.robot.commands.IndexerCommands;
import frc.robot.commands.IntakeCommands;
import frc.robot.commands.ShooterCommands;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
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
        NamedCommands.registerCommand("Shoot Then Index", ShooterCommands.shootWaitIndex(indexer, shooter));
        NamedCommands.registerCommand("Spin Up Shooter", ShooterCommands.spinUpShooter(shooter));
        NamedCommands.registerCommand("Run Indexer", IndexerCommands.runIndexer(indexer));
        NamedCommands.registerCommand("Stop Indexer", IndexerCommands.stopIndexer(indexer));
        NamedCommands.registerCommand("Intake w/ Jostle", IntakeCommands.intakeThenJostle(intake));
        NamedCommands.registerCommand("Shoot", AutoCommands.shoot(intake, indexer, shooter));
        NamedCommands.registerCommand("Shoot After Moving", AutoCommands.shootAfterMove(intake, indexer, shooter));
        autoChooser = AutoBuilder.buildAutoChooser("None");
        // autoChooser.addOption("None", Commands.none());
        // autoChooser.addOption("Score Trench - Blue", Autos.scoreTrenchBlue(shooter, indexer));
        // autoChooser.addOption("Score Center - Blue", Autos.scoreCenterBlue(drivetrain, shooter, indexer));
        // autoChooser.addOption("Score Center - Red", Autos.scoreCenterRed(drivetrain, shooter, indexer));
        // autoChooser.addOption("Rotate, Score", Autos.rotateScore(drivetrain, shooter, indexer));
        // autoChooser.addOption("Position, Rotate, Score", Autos.positionRotateScore(drivetrain, shooter, indexer));

        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    public void autoInit() {
        // indexer.setAutonomous(true);
        shooter.toggleOff();
        indexer.stop();
        intake.stop();
    }

    public void teleopInit() {
        indexer.setAutonomous(false);
        shooter.toggleOff();
        indexer.stop();
        intake.stop();
    }
}
