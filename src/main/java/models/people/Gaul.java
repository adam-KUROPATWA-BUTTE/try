package models.people;

import models.food.Food;

import java.util.ArrayList;
import java.util.Arrays;

public interface Gaul {
    ArrayList<Food> availableFoods = new ArrayList<>(Arrays.asList(Food.WILDBOAR, Food.FAIRLYFRESHFISH, Food.WINE));
}
