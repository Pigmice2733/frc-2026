package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.DrivetrainConfig;

@SuppressWarnings("unused")
public class Controls {
    private CommandXboxController driver, operator;

    /** If a value from a joystick is less than this, it will return 0. */
    private double threshold = Constants.AXIS_THRESHOLD;

    public Controls(CommandXboxController driver, CommandXboxController operator) {
        this.driver = driver;
        this.operator = operator;
    }

    /**
     * Returns the left joystick's Y-axis value, corresponding to the robot's
     * X-direction speed. Inverted because positive joystick axis is down but
     * positive X-movement is forward.
     */
    public double getDriveSpeedX() {
        double joystickY = MathUtil.applyDeadband(driver.getLeftY(), threshold);

        return -joystickY * DrivetrainConfig.MAX_DRIVE_SPEED;
    }

    /**
     * Returns the left joystick's X-axis value, corresponding to the robot's
     * Y-direction speed. Inverted because positive joystick axis is right but
     * positive Y-movement is left.
     */
    public double getDriveSpeedY() {
        double joystickX = MathUtil.applyDeadband(driver.getLeftX(), threshold);

        return -joystickX * DrivetrainConfig.MAX_DRIVE_SPEED;
    }

    /**
     * Returns the right joystick's X-axis value, corresponding to the robot's
     * rotational speed. Inverted because positive joystick axis is right but
     * positive rotation is left.
     */
    public double getTurnSpeed() {
        double joystickTurn = MathUtil.applyDeadband(driver.getRightX(), threshold);

        return -joystickTurn * DrivetrainConfig.MAX_TURN_SPEED;
    }

    /**
     * Returns whether the right bumper is pressed, corresponding to the driving
     * mode (robot-oriented versus field-oriented).
     */
    public boolean getRobotOrientedMode() {
        return driver.rightBumper().getAsBoolean();
    }
}