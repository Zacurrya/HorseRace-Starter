package Part1;

/**
 * Write a description of class Horse here.
 * 
 * @author Zakariya Yusuf
 * @version 22/04/2025
 */
public class Horse {
    // Fields of class Horse
    String name;
    char symbol;
    double confidence;
    static double confidenceAdjustment = 0.1;
    int distanceTravelled;
    boolean hasFallen;

    // Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = horseConfidence;
    }

    // Other methods of class Horse
    public void fall() {
        this.hasFallen = true;
        this.setConfidence(this.confidence - Horse.confidenceAdjustment);
    }

    public double getConfidence() {
        return this.confidence;
    }

    public int getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public String getName() {
        return this.name;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public static void goBackToStart() {
        this.distanceTravelled = 0;
        this.hasFallen = false;
    }

    public boolean hasFallen() {
        return this.hasFallen;
    }

    public void moveForward() {
        this.distanceTravelled += 1;
    }

    public void setConfidence(double newConfidence) {
        if (newConfidence >= 0 && newConfidence <= 1) {
            this.confidence = newConfidence;
        }
    }

    public void setSymbol(char newSymbol) {
        this.symbol = newSymbol;
    }
}
