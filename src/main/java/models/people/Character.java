package models.people;

import java.util.ArrayList;

import models.factory.CharacterFactory.CharacterType;
import models.food.Food;
import static models.people.Gaul.availableFoods;
import models.potion.MagicPotion;

public abstract class Character {
    private static final int STRENGTH_MULTIPLIER = 100; // Converts strength decimal to combat damage

    private final String name;
    private final char sex;
    private final double height;
    private final int age;
    private final double strength;
    private double endurance;
    private double stamina;
    private double health;
    private double maxHealth;
    private double hungerIndicator; // Added hunger indicator (0-100)
    private boolean hunger; // Kept for backward compatibility
    private boolean belligerence;
    private int magicPotionLevel;
    private boolean dead = false;

    public static final String[] CHARACTERSTYPE;
    static {
        CharacterType[] types = CharacterType.values();
        CHARACTERSTYPE = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            CHARACTERSTYPE[i] = types[i].toString();
        }
    }

    public static final String[] NAMES = {
            "Aelia", "Cassius", "Livia", "Marcus", "Tara", "Gwen",
            "Ulric", "Serena", "Hadrian", "Lucius", "Freya", "Nevot",
            "Allain", "Timothée", "Sikorsky UH-60 Black Hawk", "Eurocopter EC135",
            "Bob", "Alice"
    };

    public Character(String name, char sex, double height, int age, double strength) {
        this.name = name;
        this.sex = sex;
        this.height = height;
        this.age = age;
        this.strength = strength;
        this.endurance = 100;
        this.stamina = 100;
        this.health = 100;
        this.maxHealth = 100;
        this.hungerIndicator = 100; // Start fully satiated
        this.hunger = false;
        this.belligerence = false;
        this.magicPotionLevel = 0;
        this.dead = false;
    }

    /**
     * Makes this character fight another character.
     * @param opponent The opponent
     */
    public void fight(Character opponent) {
        if (isDead() || opponent.isDead()) {
            return;
        }
        if (opponent == null || opponent.getHealth() <= 0) {
            return;
        }

        // Calculate damage: Attacker's Strength - (Defender's Endurance / 2) + Character Bonus
        int damage = (int) (this.strength * STRENGTH_MULTIPLIER - (opponent.getEndurance() / 2.0) + this.getCombatBonus());
        damage = Math.max(1, damage); // Minimum 1 damage
        opponent.takeDamage(damage);
    }

    /**
     * Take damage
     * @param damage The amount of damage to take
     */
    public void takeDamage(double damage) {
        if (isDead()) {
            return;
        }
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            die();
        }
    }

    /**
     * Reduces health by the specified amount, clamping at 0.
     * @param damage The amount of damage to take
     * @deprecated Use takeDamage() instead
     */
    @Deprecated
    public void reduceHealth(double damage) {
        takeDamage(damage);
    }

    /**
     * Heals the character a certain amount.
     * @param amount How much health to gain
     */
    public void heal(double amount) {
        if (isDead()) {
            throw new IllegalStateException("Cannot heal dead character!");
        }
        this.health += amount;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    /**
     * Restores the hunger of the character.
     * @param food Food item being eaten.
     */
    public void eat(Food food) {
        if (food != null) {
            this.hungerIndicator += 20; // Augmente la satiété
            if (this.hungerIndicator > 100) {
                this.hungerIndicator = 100;
            }
            // Update boolean hunger flag based on indicator
            this.hunger = (hungerIndicator < 50);
        }
    }

    /**
     * Makes this character hungry.
     */
    public void makeHungry() {
        hungerIndicator = Math.max(0, hungerIndicator - 30);
        hunger = (hungerIndicator < 50);
    }

    /**
     * Check if character is hungry
     * @return true if hunger indicator is below 50
     */
    public boolean isHungry() {
        return hungerIndicator < 50;
    }

    /**
     * Get the hunger indicator value (0-100)
     * @return The current hunger indicator level
     */
    public double getHungerIndicator() {
        return hungerIndicator;
    }

    /**
     * Makes this character drink a potion.
     */
    public void drinkPotion(MagicPotion magicPotion) {
        if (magicPotion.takeAPotion()) {
            ++magicPotionLevel;
        }
    }

    /**
     * Makes this character die.
     */
    public void die() {
        this.health = 0;
        this.dead = true;
    }

    /**
     * Makes this character perish.
     * @deprecated Use die() instead
     */
    @Deprecated
    public void passAway() {
        die();
    }

    /**
     * Get combat bonus for this character type.
     * Each subclass must implement this to provide their unique combat bonus.
     * @return The combat bonus value
     */
    protected abstract int getCombatBonus();

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getName() {
        return name;
    }

    public char getSex() {
        return sex;
    }

    public double getHeight() {
        return height;
    }

    public int getAge() {
        return age;
    }

    public double getStrength() {
        return strength;
    }

    public double getEndurance() {
        return endurance;
    }

    public void setEndurance(double endurance) {
        this.endurance = endurance;
    }

    public double getStamina() {
        return stamina;
    }

    /**
     * Get health
     * @return The current health level
     */
    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public boolean isBelligerence() {
        return belligerence;
    }

    public double getMagicPotionLevel() {
        return magicPotionLevel;
    }

    public ArrayList<Food> getAvailableFoods() {return availableFoods;}

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", height=" + height +
                ", age=" + age +
                ", strength=" + strength +
                ", stamina=" + stamina +
                ", health=" + health +
                ", hungerIndicator=" + hungerIndicator +
                ", hunger=" + hunger +
                ", belligerence=" + belligerence +
                ", magicPotionLevel=" + magicPotionLevel +
                ", dead=" + dead +
                '}';
    }
}