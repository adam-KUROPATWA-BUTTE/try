package models.people;

import models.food.Food;
import models.potion.MagicPotion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    static class TestCharacter extends Character {
        public TestCharacter(String name, char sex, double height, int age, double strength) {
            super(name, sex, height, age, strength);
        }
    }

    @Test
    void fight() {
        Character attacker = new TestCharacter("Asterix", 'M', 1.5, 35, 50);
        Character victim = new TestCharacter("Obelix", 'M', 2, 40, 80);

        double initialHealth = victim.getHealth();
        attacker.fight(victim);

        assertEquals(initialHealth - 10, victim.getHealth());
    }

    @Test
    void heal() {
        Character character = new TestCharacter("Asterix", 'M', 1.5, 35, 50);
        double initial = character.getHealth();

        character.heal(20);

        assertEquals(initial + 20, character.getHealth());
    }

    @Test
    void eat() {
        Character character = new TestCharacter("Asterix", 'M', 1.5, 35, 50);
        //Food food = new Food("Boar", 50);
        character.makeHungry();
        assertTrue(character.isHungry());

        character.eat(Food.WILDBOAR);

        assertFalse(character.isHungry());
    }

    @Test
    void makeHungry() {
        Character c = new TestCharacter("Asterix", 'M', 1.5, 35, 50);
        assertFalse(c.isHungry());

        c.makeHungry();

        assertTrue(c.isHungry());
    }

    @Test
    void drinkPotion() {
        Character c = new TestCharacter("Asterix", 'M', 1.5, 35, 50);

        MagicPotion potion = new MagicPotion();
        potion.setKettlePortion(1);
        int initial = (int) c.getMagicPotionLevel();

        c.drinkPotion(potion);

        assertEquals(initial + 1, c.getMagicPotionLevel());
    }

    @Test
    void passAway() {
        Character c = new TestCharacter("Asterix", 'M', 1.5, 35, 50);
        assertFalse(c.isDead());

        c.passAway();

        assertTrue(c.isDead());
    }

    @Test
    void isDead() {
        Character c = new TestCharacter("Asterix", 'M', 1.5, 35, 50);
        c.setDead(true);

        assertTrue(c.isDead());
    }

    @Test
    void testToString() {
        Character c = new TestCharacter("Asterix", 'M', 1.5, 35, 50);

        String str = c.toString();
        assertTrue(str.contains("Asterix"));
        assertTrue(str.contains("strength"));
        assertTrue(str.contains("health"));
        assertTrue(str.contains("dead"));
    }
}
