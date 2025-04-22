import java.util.concurrent.TimeUnit;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Zakariya Yusuf
 * @version V.1
 */
public class Race {
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;
    String winner;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance) {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        switch (laneNumber) {
            case 1 -> lane1Horse = theHorse;
            case 2 -> lane2Horse = theHorse;
            case 3 -> lane3Horse = theHorse;
            default -> System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace() {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();
                      
        while (!finished)
        {
            //move each horse
            moveHorse(lane1Horse);
            moveHorse(lane2Horse);
            moveHorse(lane3Horse);
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won the race is finished
            if ( raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse) )
            {
                finished = true;

                //print the winner
                if ( raceWonBy(lane1Horse) ) {
                    winner = lane1Horse.getName();
                    lane1Horse.setConfidence(lane1Horse.getConfidence()+Horse.confidenceAdjustment);
                } else if ( raceWonBy(lane2Horse) ) {
                    winner = lane2Horse.getName();
                    lane2Horse.setConfidence(lane2Horse.getConfidence()+Horse.confidenceAdjustment);
                } else if ( raceWonBy(lane3Horse) ) {
                    winner = lane3Horse.getName();
                    lane3Horse.setConfidence(lane3Horse.getConfidence()+Horse.confidenceAdjustment);
                }
                System.out.println("And the winner is... "+winner+"!");

            } else if (lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen()) {
                //if all horses have fallen 
                System.out.println("All horses have fallen. No winner!");
                finished = true;
            }

            //wait for a short time before moving the horses again
            try{ 
                TimeUnit.MILLISECONDS.sleep(200);
            }catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
        double num = Math.random();
        if  (!theHorse.hasFallen()) {

            // check if the horse has fallen
            if (num < (0.1 * theHorse.getConfidence())) {
                System.out.println(num);
                theHorse.fall();
            } 
            // if the horse has not fallen, move it forward
            else if (!theHorse.hasFallen()) {
                theHorse.moveForward();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse) {
        return theHorse.getDistanceTravelled() == raceLength;
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace() {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        printLane(lane1Horse);
        System.out.print(" " + lane1Horse.getName() + " (Current confidence " + Math.round(lane1Horse.getConfidence()*10f) / 10f + ")");
        System.out.println();
        
        printLane(lane2Horse);
        System.out.print(" " + lane2Horse.getName() + " (Current confidence " + Math.round(lane2Horse.getConfidence()*10f) / 10f + ")");
        System.out.println();
        
        printLane(lane3Horse);
        System.out.print(" " + lane3Horse.getName() + " (Current confidence " + Math.round(lane3Horse.getConfidence()*10f) / 10f + ")");
        System.out.println();
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse) {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        System.out.print('|'); // Lane beginning

        // Print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        // If the horse has fallen then print dead
        if(theHorse.hasFallen()) {
            System.out.print('\u274C');
        }
        // Horse is alive, print symbol
        else {
            System.out.print(theHorse.getSymbol());
        }
        
        // Print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        // Print the | for the end of the track
        System.out.print('|');
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times) {
        int i = 0;
        while (i < times) {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    public static void main(String[] args) {
        // Creates a race 
        Race race = new Race(20);
    
        // Create three horses with different confidence levels and symbols
        Horse horse1 = new Horse('S', "Sabrina", 0.6);
        Horse horse2 = new Horse('N', "Nigel", 0.5);
        Horse horse3 = new Horse('A', "Andy", 0.4);

        // Add the horses to the race
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);
    
        // Start the race
        race.startRace();
    }
}
