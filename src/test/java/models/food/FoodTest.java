package models.food;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
class FoodTest {

    @org.junit.jupiter.api.Test
    void isVegetableTest() {
        assertTrue(Food.isVegetable(Food.CARROT));
        assertFalse(Food.isVegetable(Food.ROCKOIL));
    }

    @org.junit.jupiter.api.Test
    void isBad() {
        assertTrue(Food.isBad(Food.NOTFRESHFISH));
        assertFalse(Food.isBad(Food.ROCKOIL));
    }
}