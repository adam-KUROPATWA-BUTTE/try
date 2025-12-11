package models.potion;

import models.food.Food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MagicPotion {
    private static final Food alternativeIngredient = Food.BEETJUICE;
    private static final ArrayList<Food> nutritionIngredients = new ArrayList<>(Arrays.asList(Food.STRAWBERRY, Food.LOBSTER));
    private static final Food duplicationIngredient = Food.TWOHEADEDUNICORNMILK;
    private static final Food metamorphosisIngredient = Food.IDEFIXHAIR;
    private static final int kettleValue = 10;
    private static final int amountForPermanentEffect = 10;
    private static final int amountForStatueEffect = 20;
    public static final ArrayList<Food> recipeIngredients = new ArrayList<>(
            Arrays.asList(Food.MISTLETOE,
                    Food.CARROT,
                    Food.SALT,
                    Food.FRESHFOURLEAFCLOVER,
                    Food.FAIRLYFRESHFISH,
                    Food.ROCKOIL,
                    Food.HONEY,
                    Food.MEAD,
                    Food.SECRETINGREDIENT
            ));
    public ArrayList<Food> missingIngredient = recipeIngredients;
    public int kettlePortion = 0;
    public boolean metamorphosisEffect = false;
    public boolean duplicationEffect = false;
    public boolean nutritiousEffect = false;

    /**
     * Get the main needed recipe for the magic potion
     */
    public ArrayList<Food> getRecipeIngredients() {
        return recipeIngredients;
    }

    /**
     * Get the ingredient used in the kettle
     */
    public ArrayList<Food> getMissingIngredient() {
        return missingIngredient;
    }

    /**
     * Get the list of additional ingredient that add nutrition value to the magic potion
     */
    public ArrayList<Food> getNutritionIngredients() {
        return nutritionIngredients;
    }

    /**
     * Get the ingredient that can replace another ingredient
     */
    public Food getAlternativeIngredient() {
        return alternativeIngredient;
    }

    /**
     * Get the ingredient that add the duplication effect to the magic potion
     */
    public Food getDuplicationIngredient() {return duplicationIngredient;}

    /**
     * Get the ingredient that add the metamorphosis effect to the magic potion
     */
    public Food getMetamorphosisIngredient() {return metamorphosisIngredient;}

    /**
     * Get the amount of portion a kettle give
     */
    public int getKettleValue() {
        return kettleValue;
    }

    /**
     * Get the amount of kettle needed to have permanent effect
     */
    public int getAmountForPermanentEffect() {return amountForPermanentEffect;}

    /**
     * Get the amount of kettle needed to a granite statue
     */
    public int getAmountForStatueEffect() {return amountForStatueEffect;}

    /**
     * Get the amount of portion in the kettle
     */
    public int getPortionInKettle() {return kettlePortion;}

    /**
     * Return true if the kettle don't have any portion left
     */
    public boolean isKettleEmpty() {return kettlePortion == 0;}

    /**
     * Check if the given food can be added to the kettle, if able, add it
     * @return Return true if it can be added, return false if cannot
     * @param food the food to add
     */
    public boolean addToCurrentRecipes(Food food) {
        if (isKettleEmpty()) {
            missingIngredient.remove(food);
            checkIfFullRecipe();

            if (food == getAlternativeIngredient()) {
                missingIngredient.remove(Food.ROCKOIL);
                return true;
            }
            else if (food == duplicationIngredient) {
                if (duplicationEffect) {
                    return false;
                }
                else{
                    duplicationEffect = true;
                }
            }
            else if (food == metamorphosisIngredient) {
                if (metamorphosisEffect) {
                    return false;
                }
                else{
                    metamorphosisEffect = true;
                }
            }
            else if(nutritionIngredients.contains(food)) {
                if (nutritiousEffect) {
                    return false;
                }
                else{
                    nutritiousEffect = true;
                }
            }
            return missingIngredient.contains(food);
        }
        return false;
    }

    /**
     * Check if the recipe is done, by checking is there is no more missing ingredient, if the recipe is done, fill the kettle with potion and reset the recipe
     */
    public boolean checkIfFullRecipe() {
        if (missingIngredient.isEmpty()) {
            missingIngredient =  getRecipeIngredients();
            kettlePortion = getKettleValue();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * If the kettle isn't empty, allow to take a magic potion from the kettle. If it's the last portion, remove the effect
      * @return true if can take potion, false if can't
     */
    public boolean takeAPotion() {
        if (!isKettleEmpty()) {
            if (kettlePortion == 1) {
                metamorphosisEffect = false;
                duplicationEffect = false;
                nutritiousEffect = false;
            }
            kettlePortion--;
            return true;
        }
        return false;
    }

    public ArrayList<Food> sortFoods(ArrayList<Food> foods) {
        ArrayList<Food> sortedFoods = new ArrayList<>();
        for (Food food : foods) {
            if (getRecipeIngredients().contains(food)) {
                sortedFoods.add(food);
            }
        }
        return sortedFoods;
    }
}
