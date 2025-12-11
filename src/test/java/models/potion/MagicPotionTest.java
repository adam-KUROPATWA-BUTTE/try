package models.potion;

import models.food.Food;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
class MagicPotionTest {
    private MagicPotion magicPotion;
    @BeforeEach
    void setUp() {
        magicPotion = new MagicPotion();
    }

    @org.junit.jupiter.api.Test
    void isKettleEmptyTest() {
        assertTrue(magicPotion.isKettleEmpty());
    }

    @org.junit.jupiter.api.Test
    void addToCurrentRecipesTest() {
        assertTrue(magicPotion.addToCurrentRecipes(Food.BEETJUICE));
        assertFalse(magicPotion.addToCurrentRecipes(Food.NOTFRESHFISH));
    }

    @org.junit.jupiter.api.Test
    void checkIfFullRecipeTest() {
        magicPotion.addToCurrentRecipes(Food.FAIRLYFRESHFISH);
        magicPotion.addToCurrentRecipes(Food.ROCKOIL);
        magicPotion.addToCurrentRecipes(Food.SALT);
        magicPotion.addToCurrentRecipes(Food.FRESHFOURLEAFCLOVER);
        magicPotion.addToCurrentRecipes(Food.CARROT);
        magicPotion.addToCurrentRecipes(Food.HONEY);
        magicPotion.addToCurrentRecipes(Food.MEAD);
        magicPotion.addToCurrentRecipes(Food.SECRETINGREDIENT);
        magicPotion.addToCurrentRecipes(Food.MISTLETOE);
        assertEquals(magicPotion.getKettleValue(),magicPotion.getPortionInKettle());
        assertEquals(magicPotion.missingIngredient, magicPotion.getRecipeIngredients());
    }

    @org.junit.jupiter.api.Test
    void takeAPotion() {
        magicPotion.addToCurrentRecipes(Food.FAIRLYFRESHFISH);
        magicPotion.addToCurrentRecipes(Food.ROCKOIL);
        magicPotion.addToCurrentRecipes(Food.SALT);
        magicPotion.addToCurrentRecipes(Food.FRESHFOURLEAFCLOVER);
        magicPotion.addToCurrentRecipes(Food.CARROT);
        magicPotion.addToCurrentRecipes(Food.HONEY);
        magicPotion.addToCurrentRecipes(Food.MEAD);
        magicPotion.addToCurrentRecipes(Food.SECRETINGREDIENT);
        magicPotion.addToCurrentRecipes(Food.MISTLETOE);
        assertTrue(magicPotion.takeAPotion());
    }
}