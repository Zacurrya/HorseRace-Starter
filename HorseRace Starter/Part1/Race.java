package Part1;

import java.util.ArrayList;
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
    private ArrayList<Horse> lanes;

    /**
     * Constructor for objects of class Race
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, int numberOfLanes) {
        // initialise instance variables
        raceLength = distance;
        lanes = new ArrayList<Horse>(numberOfLanes);
        for (int i = 0; i < numberOfLanes; i++) {
            lanes.add(i, null); // empty lane
        }
    }

    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse   the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        lanes.set(laneNumber - 1, theHorse);
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */
    public void startRace() {
        // declare a local variable to tell us when the race is finished
        boolean finished = false;

        // reset all the lanes (all horses not fallen and back to 0).
        for (Horse horse : lanes) {
            if (horse == null) {
                continue; // skip empty lanes
            }
            horse.goBackToStart();
            horse.hasFallen = false;
        }

        while (!finished) {
            // move each horse
            for (int i = 0; i < lanes.size(); i++) {
                if (lanes.get(i) == null) {
                    continue; // skip empty lanes
                }
                moveHorse(lanes.get(i));
            }

            // print the race positions
            printRace();

            // if any horse has won, finish the race
            if (returnWinners().size() == 1) {
                finished = true;
                winnerConfidenceBooster(returnWinners());
                System.out.println("Winner: " + returnWinners().get(0));
            } else {
                if (allHorsesFallen()) {
                    finished = true;
                    System.out.println("All horses have fallen. No winner!");
                }
                // Else, continue
            }
            // Waits an amount of ms before the next turn
            turnInterval(300);
        }
    }

    /**
     * Returns number of horses that have won
     * 
     * @return
     */

    public ArrayList<Horse> returnWinners() {
        // Initialises an empty list of winners
        // to be filled with the horses that have won
        ArrayList<Horse> winners = new ArrayList<>(0);
        // Loops through all lanes, finding the horses that have won
        for (Horse horse : lanes) {
            if (horse == null) {
                continue; // skip empty lanes
            }
            // A winner is identified by having travelled the full distance
            if (raceWonBy(horse)) {
                winners.add(horse);
            }
        }
        return winners;
    }

    // Increases the confidence of the winning horse(s)
    public void winnerConfidenceBooster(ArrayList<Horse> winners) {
        for (Horse horse : winners) {
            horse.setConfidence(horse.getConfidence() + Horse.confidenceAdjustment);
        }
    }

    public void printWinners(ArrayList<Horse> winners) {
        // If there is one winner
        if (winners.size() == 1) {
            System.out.println(winners.get(0) + " is the winner!");
        }
        // If there are multiple winners
        else if (winners.size() > 1) {
            System.out.print("Winners: ");
            for (int i = 0; i < winners.size(); i++) {
                System.out.print(winners.get(i));
                if (i < winners.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public boolean allHorsesFallen() {
        boolean allHorsesFallen = false;

        for (Horse horse : lanes) {
            if (horse == null)
                continue; // skip empty lanes

            //
            if (horse.hasFallen())
                allHorsesFallen = true;
            else {
                allHorsesFallen = false;
                break; // exit the loop if at least one horse is still running
            }
        }
        return allHorsesFallen;
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    public void moveHorse(Horse theHorse) {
        double num = Math.random();
        if (!theHorse.hasFallen()) {
            // check if the horse has fallen
            if (num < (0.1 * theHorse.getConfidence())) {
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
        if (theHorse == null) {
            return false; // empty lane
        }
        // check if the horse has won
        return theHorse.getDistanceTravelled() == raceLength;
    }

    // Print the race on the terminal
    private void printRace() {
        System.out.print('\u000C'); // clear the terminal window

        multiplePrint('=', raceLength + 3); // top edge of track
        System.out.println();

        // Print the lanes
        for (int i = 0; i < lanes.size(); i++) {
            printLane(lanes.get(i));
            printConfidence(lanes.get(i));
            System.out.println();
        }
        multiplePrint('=', raceLength + 3); // bottom edge of track
        System.out.println();

    }

    private void printLane(Horse theHorse) {
        System.out.print('|'); // Lane beginning

        // Print empty lane
        if (theHorse == null) {
            multiplePrint(' ', raceLength + 1);
        }
        // Print horse lane
        else {
            // calculate how many spaces are needed before
            // and after the horse
            int spacesBefore = theHorse.getDistanceTravelled();
            int spacesAfter = raceLength - theHorse.getDistanceTravelled();
            // Print the spaces before the horse
            multiplePrint(' ', spacesBefore);
            // If the horse has fallen then print dead
            if (theHorse.hasFallen()) {
                System.out.print('\u274C');
            }
            // Horse is alive, print symbol
            else {
                System.out.print(theHorse.getSymbol());
            }

            // Print the spaces after the horse
            multiplePrint(' ', spacesAfter);
        }
        // Print the | for the end of the track
        System.out.print('|');
    }

    public void printConfidence(Horse theHorse) {
        if (theHorse == null) {
            return; // empty lane
        }
        System.out.print(" " + theHorse.getName() + " (Current confidence "
                + Math.round(theHorse.getConfidence() * 10f) / 10f + ")");
    }

    // Prints a character multiple times
    private void multiplePrint(char aChar, int times) {
        int i = 0;
        while (i < times) {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    void turnInterval(int interval) {
        try {
            TimeUnit.MILLISECONDS.sleep(interval);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
