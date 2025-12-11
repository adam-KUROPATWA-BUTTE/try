package models.people;

import models.food.Food;
import models.potion.MagicPotion;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static models.people.Gaul.availableFoods;

public abstract class Character {
    private final String name;
    private final char sex;
    private final double height;
    private final int age;
    private final double strength;
    private double stamina;
    private double health;
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
        this.stamina = 100;
        this.health = 100;
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
        opponent.heal(-10);
    }

    /**
     * Heals the character a certain amount.
     * @param amount How much health to gain
     */
    public void heal(double amount) {
        this.health += amount;
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
     * Makes this character perish.
     */
    public void passAway() {
        // TODO: I mean... Is that enough? Probably tbh, we'll see.
        dead = true;
    }

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

    public double getStamina() {
        return stamina;
    }

    public double getHealth() {
        return health;
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
