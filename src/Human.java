import java.util.List;
import java.util.Random;

/**
 * A simple model of a human.
 * Humans age, move, breed, and die.
 */
public class Human extends Entity
{

    private static final int BREEDING_AGE = 18;
    private static final int MAX_AGE = 55;
    private static final double BREEDING_PROBABILITY = 0.05;
    private static final int MAX_LITTER_SIZE = 2;
    private static final Random rand = Randomizer.getRandom();

    private int age;

    /**
     * Create a new human. A human may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the human will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Human(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    /**
     * This is what the human does most of the time - they run
     * around. Sometimes they will breed or die of old age.
     * @param newHumans A list to return newly born humans.
     */
    public void act(List<Entity> newHumans)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newHumans);
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Increase the age.
     * This could result in the human's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Check whether or not this human is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newHumans A list to return newly born humans.
     */
    private void giveBirth(List<Entity> newHumans)
    {
        // New humans are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Human young = new Human(false, field, loc);
            newHumans.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A human can breed if it has reached the breeding age.
     * @return true if the human can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
