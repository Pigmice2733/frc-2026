package frc.robot.util;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class DashboardNumber implements DoubleSupplier{
    private String key;
    private double value;
    private double places;

    public DashboardNumber(String key, double value, double places) {
        this.key = key;
        this.value = value;
        Constants.sendNumberToElastic(key, value, places);
    }

    public void updateValue() {
        value = getAsDouble();
        Constants.sendNumberToElastic(key, value, places);
    }

    public double get() {
        return SmartDashboard.getNumber(key, value);
    }

    public double getAsDouble() {
        return get();
    }
}
