package Part1;

public class run {
    public static void main(String[] args) {
        // Creates a race
        Race race = new Race(20, 3);

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
