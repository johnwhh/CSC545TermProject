// This project has no license.
package mealplanner.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class ShoppingList {

    public final String name;
    public final HashMap<Food, Integer> foodQuantities;

    public ShoppingList(String name, MealPlan[] mealPlans, Fridge fridge) {
        this.name = name;
        this.foodQuantities = getMissingFood(mealPlans, fridge);
    }

    private HashMap<Food, Integer> getMissingFood(MealPlan[] mealPlans, Fridge fridge) {
        HashMap<Food, Integer> missingFoodQuantities = new HashMap<>();

        for (MealPlan mealPlan : mealPlans) {
            for (Recipe recipe : mealPlan.getRecipes().values()) {
                for (Object[] recipeFoodQuantities : recipe.getFoods().values()) {
                    Food food = (Food) recipeFoodQuantities[0];
                    int quantity = (int) recipeFoodQuantities[1];

                    int existingQuantity = 0;
                    if (missingFoodQuantities.get(food) != null) {
                        existingQuantity = (int) missingFoodQuantities.get(food);
                    }

                    missingFoodQuantities.put(food, existingQuantity + quantity);
                }
            }
        }

        for (Object[] fridgeFoodQuantities : fridge.getFoods().values()) {
            Food food = (Food) fridgeFoodQuantities[0];
            int quantity = (int) fridgeFoodQuantities[1];

            if (missingFoodQuantities.get(food) != null) {
                int neededQuantity = (int) missingFoodQuantities.get(food);
                
                int missingQuantity = neededQuantity - quantity;
                if (missingQuantity > 0) {
                    missingFoodQuantities.put(food, missingQuantity);
                } else {
                    missingFoodQuantities.remove(food);
                }
            }
        }
        
        return missingFoodQuantities;
    }
}
