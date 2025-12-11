package models.people;

import models.food.Food;
import models.potion.MagicPotion;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static models.people.Gaul.availableFoods;

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
    private boolean hunger;
    private boolean belligerence;
    private int magicPotionLevel;
    private boolean dead = false;

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
        // Calculate damage: Attacker's Strength - (Defender's Endurance / 2) + Character Bonus
        int damage = (int) (this.strength * STRENGTH_MULTIPLIER - (opponent.getEndurance() / 2.0) + this.getCombatBonus());
        damage = Math.max(1, damage); // Minimum 1 damage
        opponent.reduceHealth(damage);
    }
    
    /**
     * Reduces health by the specified amount, clamping at 0.
     * @param damage The amount of damage to take
     */
    public void reduceHealth(double damage) {
        if (isDead()) {
            return;
        }
        health = Math.max(0, health - damage);
        if (health == 0) {
            die();
        }
    }

    /**
     * Heals the character a certain amount.
     * @param amount How much health to gain
     */
    public void heal(double amount) {
        if (isDead()) {
            throw new IllegalStateException("Cannot heal dead character!");
        }
        this.health = Math.min(health + amount, maxHealth);
    }

    /**
     * Restores the hunger of the character.
     * @param food Food item being eaten.
     */
    public void eat(Food food) {
        hunger = false;
    }

    /**
     * Makes this character hungry.
     */
    public void makeHungry() {
        hunger = true;
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

    public double getHealth() {
        return health;
    }
    
    public double getMaxHealth() {
        return maxHealth;
    }
    
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public boolean isHungry() {
        return hunger;
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
                ", hunger=" + hunger +
                ", belligerence=" + belligerence +
                ", magicPotionLevel=" + magicPotionLevel +
                ", dead=" + dead +
                '}';
    }
}
