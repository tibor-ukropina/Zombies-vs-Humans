import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

/**
 * A simple model of a zombie.
 * Zombies age, move, eat humans, and die.
 *
 */
public class Zombie extends Entity
{
    // Characteristics shared by all zombies (class variables).

    // The age at which a zombie can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a zombie can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a zombie breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_BIRTH_SIZE = 2;
    // The food value of a single human. In effect, this is the
    // number of steps a zombie can go before it has to eat again.
    private static final int HUMAN_FOOD_VALUE = 9;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The zombie's age.
    private int age;
    // The zombie's food level, which is increased by eating humans.
    private int foodLevel;

    /**
     * Create a zombie. A zombie can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the zombie will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Zombie(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(HUMAN_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = HUMAN_FOOD_VALUE;
        }
    }

    /**
     * Simulates the zombie hunting for humans. The Zombie may
     * breed, die or hunger, or die of old age.
     */
    public void act(List<Entity> newZombies)
    {
        incrementHunger();
        incrementAge();
        if(isAlive()) {
            giveBirth(newZombies);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    private void incrementHunger()
    {
        --foodLevel;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for humans adjacent to the current location.
     * Only the first live human is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Human) {
                Human human = (Human) animal;
                if(human.isAlive()) {
                    human.setDead();

                    foodLevel = HUMAN_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Check whether or not this zombie is to give birth at this step.
     * New births will be made into free adjacent locations.
     */
    private void giveBirth(List<Entity> newZombies)
    {
        // New zombies are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Zombie young = new Zombie(false, field, loc);
            newZombies.add(young);
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
            births = rand.nextInt(MAX_BIRTH_SIZE) + 1;
        }
        return births;
    }

    /**
     * A Zombie can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
