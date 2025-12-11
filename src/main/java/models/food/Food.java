package models.food;

import java.util.ArrayList;
import java.util.Arrays;

public enum Food {
    BEETJUICE,
    CARROT,
    FAIRLYFRESHFISH,
    FRESHFOURLEAFCLOVER,
    HONEY,
    IDEFIXHAIR,
    LOBSTER,
    MEAD,
    MISTLETOE,
    NOTFRESHFISH,
    NOTFRESHFOURLEAFCLOVER,
    ROCKOIL,
    SALT,
    SECRETINGREDIENT,
    STRAWBERRY,
    TWOHEADEDUNICORNMILK,
    WILDBOAR,
    WINE;

    //private static final ArrayList<Food> meats = new ArrayList<>(Arrays.asList(FAIRLYFRESHFISH, NOTFRESHFISH, LOBSTER, WILDBOAR));
    private static final ArrayList<Food> vegetables = new ArrayList<>(Arrays.asList(CARROT, STRAWBERRY, BEETJUICE));
    private static final ArrayList<Food> drinks = new ArrayList<>(Arrays.asList(HONEY, ROCKOIL, WINE, MEAD, TWOHEADEDUNICORNMILK));
    //private static final ArrayList<Food> others = new ArrayList<>(Arrays.asList(FRESHFOURLEAFCLOVER, IDEFIXHAIR, NOTFRESHFOURLEAFCLOVER, MISTLETOE, SECRETINGREDIENT, SALT));
    private static final ArrayList<Food> bads = new ArrayList<>(Arrays.asList(NOTFRESHFOURLEAFCLOVER, NOTFRESHFISH));

    /**
     * Check if the given food is a vegetable
     * @param food the food to check
     */
    public static boolean isVegetable(Food food) {
        return vegetables.contains(food);
    }

    /**
     * Check if the given food is a drink
     * @param food the food to check
     */
    public static boolean isDrink(Food food) {
        return drinks.contains(food);
    }

    /**
     * Check if the given food is bad
     * @param food the food to check
     */
    public static boolean isBad(Food food) {
        return bads.contains(food);
    }
}
