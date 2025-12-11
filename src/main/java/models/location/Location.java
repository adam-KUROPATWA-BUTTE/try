package models.location;


/**
 * The location class representing a place in the simulation.
 * @author Mada
 */
import models.food.Food;
import models.people.Character;
import models.potion.MagicPotion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 
 * All the attributes and methods of a Location that can be used in the simulation.
 * @author Mada
 */

public class Location {
    
    private static final int HERB_HEALING_AMOUNT = 50;
    private static final int FOOD_HEALING_AMOUNT = 30;
    private static final int HERB_SPAWN_AMOUNT = 2;
    private static final int HERB_SPAWN_INTERVAL = 3;

    private final String name;
    private final double superficie;
    private final LocationType type;

    private Character clanLeader;
    private final List<Character> characters = new ArrayList<>();
    private final List<Food> foods = new ArrayList<>();
    private MagicPotion magicPotion = new MagicPotion();
    private int healingHerbs = 0;
    private int turnsSinceLastHerbSpawn = 0;

    public Location(String name, double superficie, LocationType type) {
        this.name = Objects.requireNonNull(name);
        this.superficie = superficie;
        this.type = Objects.requireNonNull(type);
    }

/**  
 * 
 * All the getters and setters for the Location class.
 * 
 **/

    public String getName() {
        return name; 
    }

    public double getSuperficie() {
        return superficie; 
    }

    public LocationType getType() { 
        return type; 
    }

    public Character getChefDeClan() { 
        return clanLeader; 
    }

    public void setChefDeClan(Character clanLeader) { 
        this.clanLeader = clanLeader; 
    }

    public List<Character> getCharacters() { 
        return new ArrayList<>(characters); 
    }

    public List<Food> getFoods() { 
        return new ArrayList<>(foods); 
    }

    public int getCharactersNbr() { 
        return characters.size(); 
    }

    public MagicPotion getMagicPotion() {
        return magicPotion;
    }
    
    public int getHealingHerbs() {
        return healingHerbs;
    }
    
    public void addHealingHerbs(int amount) {
        this.healingHerbs += amount;
    }
    
    public void spawnHerbsIfNeeded() {
        turnsSinceLastHerbSpawn++;
        if (turnsSinceLastHerbSpawn >= HERB_SPAWN_INTERVAL) {
            // Healing herbs spawn every 3 turns in villages
            if (type == LocationType.GAUL_TOWN || type == LocationType.ROMAIN_TOWN || 
                type == LocationType.GAUL_ROMAIN_VILLAGE) {
                healingHerbs += HERB_SPAWN_AMOUNT;
                turnsSinceLastHerbSpawn = 0;
            }
        }
    }

    public boolean addCharacter(Character p) {
        if (p == null) return false;
        if (!LocationRestriction.isAllowed(type, p)) return false;
        return characters.add(p);
    }

    public boolean removeCharacter(Character p) {
        if (p == null) return false;
        return characters.remove(p);
    }

    public boolean addFood(Food a) {
        if (a == null) return false;
        return foods.add(a);
    }

    public boolean removeFood(Food a) {
        if (a == null) return false;
        return foods.remove(a);
    }

    public void healCharacters(double amount) {
        for (Character p : characters) {
            if (!p.isDead()) {
                p.heal(amount);
            }
        }
    }
    
    /**
     * Heal a specific character using healing herbs or food.
     * @param c The character to heal
     * @throws IllegalStateException if character is dead or no healing resources available
     */
    public void healCharacter(Character c) {
        if (c.isDead()) {
            throw new IllegalStateException("Cannot heal dead character!");
        }
        
        // Try to use healing herbs first
        if (healingHerbs > 0) {
            healingHerbs--;
            c.heal(HERB_HEALING_AMOUNT);
        } else if (!foods.isEmpty()) {
            // Use food as alternative
            Food food = foods.remove(0);
            c.heal(FOOD_HEALING_AMOUNT);
        } else {
            throw new IllegalStateException("No healing resources available!");
        }
    }

    public void feedCharacters() {
        if (characters.isEmpty() || foods.isEmpty()) return;

        Iterator<Character> itP = characters.iterator();
        while (itP.hasNext()) {
            Character p = itP.next();
            if (foods.isEmpty()) { p.makeHungry(); continue; }
            Food aliment = foods.remove(0);
            p.eat(aliment);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Location: ").append(name).append(" (").append(type).append(")\n");
        sb.append("Superficie: ").append(superficie).append("\n");
        sb.append("Chef: ").append(clanLeader == null ? "aucun" : clanLeader.getName()).append("\n");
        sb.append("Personnages (").append(characters.size()).append("):\n");
        for (Character p : characters) {
            sb.append(" - ").append(p.getName())
                    .append(" | santé=").append(p.getHealth())
                    .append(" | faim=").append(p.isHungry())
                    .append("\n");
        }
        sb.append("Aliments (").append(foods.size()).append("):\n");
        for (Food a : foods) {
            sb.append(" - ").append(a.name()).append("\n");
        }
        return sb.toString();
    }
}