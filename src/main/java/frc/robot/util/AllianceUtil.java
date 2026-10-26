package frc.robot.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class AllianceUtil {
    public static boolean isBlueAlliance() {
        return DriverStation.getAlliance().get() == Alliance.Blue;
    }
}
