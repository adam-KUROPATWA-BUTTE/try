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

    private final String name;
    private final double superficie;
    private final LocationType type;

    private Character clanLeader;
    private final List<Character> characters = new ArrayList<>();
    private final List<Food> foods = new ArrayList<>();
    private MagicPotion magicPotion = new MagicPotion();

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
            p.heal(amount);
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