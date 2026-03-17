package frc.robot.util;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.pathplanner.lib.config.PIDConstants;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Utility class for adding PID control to motors
 */
public class PIDConfig {

    /**
     * Proportion
     */
    private double kP = 0;
    /**
     * Integral
     */
    private double kI = 0;
    /**
     * Derivative
     */
    private double kD = 0;
    /**
     * Acceleration
     */
    private double kA = 0;
    /**
     * Speed compensation
     */
    private double kS = 0;
    /**
     * Velocity
     */
    private double kV = 0;
    /**
     * Gravity compensation
     */
    private double kG = 0;

    /**
     * Name of the PID values as a whole in the dashboard
     */
    private String key;

    /**
     * Create a new PIDConfig object. Sends values to Smart Dashboard
     * 
     * @param key Name of the values as a whole in Smart Dashboard
     * @param kP P
     * @param kI I
     * @param kD D
     * @param kA A
     * @param kS S
     * @param kV V
     * @param kG G
     */
    public PIDConfig(String key, double kP, double kI, double kD, double kA, double kS, double kV, double kG) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kA = kA;
        this.kS = kS;
        this.kV = kV;
        this.kG = kG;

        this.key = key;

        double[] values = { kP, kI, kD, kA, kS, kV, kG };

        SmartDashboard.putNumberArray(key, values);
    }

    public PIDConfig(String key, PIDConstants pid) {
        this(key, pid.kP, pid.kI, pid.kD);
    }

    public PIDConfig(String key, PIDConstants pid, FeedForwardConstants feedForward) {
        this(key, pid.kP, pid.kI, pid.kD, feedForward.kA, feedForward.kS, feedForward.kV, feedForward.kG);
    }

    public PIDConfig(String key, double kP) {
        this(key, kP, 0, 0, 0, 0, 0, 0);
    }

    public PIDConfig(String key, double kP, double kD) {
        this(key, kP, 0, kD, 0, 0, 0, 0);
    }

    public PIDConfig(String key, double kP, double kI, double kD) {
        this(key, kP, kI, kD, 0, 0, 0, 0);
    }

    public PIDConfig(String key, double kP, double kI, double kD, double kS) {
        this(key, kP, kI, kD, 0, kS, 0, 0);
    }

    public PIDConfig(String key, double kP, double kI, double kD, double kS, double kV) {
        this(key, kP, kI, kD, 0, kS, kV, 0);
    }

    public PIDConfig(String key, double kP, double kI, double kD, double kS, double kV, double kG) {
        this(key, kP, kI, kD, 0, kS, kV, kG);
    }

    public void configTalonFX(TalonFX motor) {
        Slot0Configs config = new Slot0Configs();
        config.kP = getKP();
        config.kI = getKI();
        config.kD = getKD();
        config.kA = getKA();
        config.kS = getKS();
        config.kV = getKV();
        config.kG = getKG();
        motor.getConfigurator().apply(config);
    }

    public void configTalonFX(TalonFX motor, TalonFXConfiguration config) {
        configTalonFXPID(config);
        motor.getConfigurator().apply(config);
    }

    public TalonFXConfiguration configTalonFXPID(TalonFXConfiguration config) {
        config.Slot0.kP = getKP();
        config.Slot0.kI = getKI();
        config.Slot0.kD = getKD();
        config.Slot0.kA = getKA();
        config.Slot0.kS = getKS();
        config.Slot0.kV = getKV();
        config.Slot0.kG = getKG();
        return config;
    }

    public void configSparkMax(SparkMax motor) {
        SparkMaxConfig config = new SparkMaxConfig();
        config.closedLoop.p(kP).i(kI).d(kD).feedForward.kA(kA).kS(kS).kV(kV).kG(kG);
        motor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public void configSparkMax(SparkMax motor, SparkMaxConfig config) {
        configSparkMaxPID(config);
        motor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public SparkMaxConfig configSparkMaxPID(SparkMaxConfig config) {
        config.closedLoop.p(kP).i(kI).d(kD).feedForward.kA(kA).kS(kS).kV(kV).kG(kG);
        return config;
    }

    public void updateValues() {
        kP = SmartDashboard.getNumberArray(key, getValues())[0];
        kI = SmartDashboard.getNumberArray(key, getValues())[1];
        kD = SmartDashboard.getNumberArray(key, getValues())[2];
        kA = SmartDashboard.getNumberArray(key, getValues())[3];
        kS = SmartDashboard.getNumberArray(key, getValues())[4];
        kV = SmartDashboard.getNumberArray(key, getValues())[5];
        kG = SmartDashboard.getNumberArray(key, getValues())[6];
    }

    public double[] refreshValues() {
        updateValues();
        return getValues();
    }

    public void setKP(double kP) {
        this.kP = kP;
    }

    public void setKI(double kI) {
        this.kI = kI;
    }

    public void setKD(double kD) {
        this.kD = kD;
    }

    public void setKA(double kA) {
        this.kA = kA;
    }

    public void setKS(double kS) {
        this.kS = kS;
    }

    public void setKV(double kV) {
        this.kV = kV;
    }

    public void setKG(double kG) {
        this.kG = kG;
    }

    public double getKP() {
        updateValues();
        return kP;
    }

    public double getKI() {
        updateValues();
        return kI;
    }

    public double getKD() {
        updateValues();
        return kD;
    }

    public double getKA() {
        updateValues();
        return kA;
    }

    public double getKS() {
        updateValues();
        return kS;
    }

    public double getKV() {
        updateValues();
        return kV;
    }

    public double getKG() {
        updateValues();
        return kG;
    }

    public double[] getValues() {
        double[] values = { kP, kI, kD, kA, kS, kV, kG };
        return values;
    }
}
