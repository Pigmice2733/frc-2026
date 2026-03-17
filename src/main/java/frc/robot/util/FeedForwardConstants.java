package frc.robot.util;

/**
 * Feed Forward constants used to create Feed Forward controllers
 */
public class FeedForwardConstants {

    /**
     * Acceleration
     */
    public final double kA;
    /**
     * Speed
     */
    public final double kS;
    /**
     * Velocity
     */
    public final double kV;
    /**
     * Gravity
     */
    public final double kG;

    /**
     * Create a new FeedForwardConstants object
     * 
     * @param kA A
     * @param kS S
     * @param kV V
     * @param kG G
     */
    public FeedForwardConstants(double kA, double kS, double kV, double kG) {
        this.kA = kA;
        this.kS = kS;
        this.kV = kV;
        this.kG = kG;
    }

    /**
     * Create a new FeedForwardConstants object
     * 
     * @param kS S
     * @param kV V
     * @param kG G
     */
    public FeedForwardConstants(double kS, double kV, double kG) {
        this(0, kS, kV, kG);
    }

    /**
     * Create a new FeedForwardConstants object
     * 
     * @param kS S
     * @param kV V
     */
    public FeedForwardConstants(double kS, double kV) {
        this(0, kS, kV, 0);
    }

    /**
     * Create a new FeedForwardConstants object
     * 
     * @param kS S
     */
    public FeedForwardConstants(double kS) {
        this(0, kS, 0, 0);
    }
}
