package models.people;

import models.food.Food;

import java.util.ArrayList;
import java.util.Arrays;

public interface Roman {
    ArrayList<Food> availableFood = new ArrayList<>(Arrays.asList(Food.WILDBOAR, Food.HONEY, Food.WINE, Food.MEAD));
}
