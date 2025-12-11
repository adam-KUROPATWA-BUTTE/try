package models.location;

import models.food.Food;
import models.people.Character;
import models.potion.MagicPotion;

import java. util.ArrayList;
import java. util.List;
import java. util.Objects;

/**
 * The location class representing a place in the simulation.
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

    // ==================== GETTERS ET SETTERS ====================

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
            if (type == LocationType.GAUL_TOWN || type == LocationType.ROMAIN_TOWN ||
                    type == LocationType.GAUL_ROMAIN_VILLAGE) {
                healingHerbs += HERB_SPAWN_AMOUNT;
                turnsSinceLastHerbSpawn = 0;
            }
        }
    }

    // ==================== GESTION DES PERSONNAGES ====================

    public boolean addCharacter(Character p) {
        if (p == null) return false;
        if (!LocationRestriction. isAllowed(type, p)) return false;
        return characters. add(p);
    }

    public boolean removeCharacter(Character p) {
        if (p == null) return false;
        return characters.remove(p);
    }

    // ==================== GESTION DE LA NOURRITURE ====================

    public boolean addFood(Food a) {
        if (a == null) return false;
        return foods.add(a);
    }

    public boolean removeFood(Food a) {
        if (a == null) return false;
        return foods.remove(a);
    }

    // ==================== MÉTHODES D'ACTION ====================

    public void healCharacters() {
        if (healingHerbs > 0) {
            for (Character character : characters) {
                character.heal(HERB_HEALING_AMOUNT);
                healingHerbs--;
                if (healingHerbs <= 0) break;
            }
        }
    }

    public void feedCharacters() {
        for (Character character : characters) {
            if (character.isHungry() && ! foods.isEmpty()) {
                Food food = foods.get(0);
                character.eat(food);
                foods.remove(0);
            }
        }
    }

    // ==================== AFFICHAGE ====================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Location: ").append(name).append("\n");
        sb.append("Type: ").append(type).append("\n");
        sb.append("Area: ").append(superficie).append("\n");
        sb.append("Characters: ").append(characters.size()).append("\n");
        sb.append("Foods: ").append(foods.size()).append("\n");
        sb.append("Healing Herbs: ").append(healingHerbs).append("\n");

        if (clanLeader != null) {
            sb.append("Clan Leader: ").append(clanLeader.getName()).append("\n");
        }

        return sb.toString();
    }
}