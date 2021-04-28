// This project has no license.
package mealplanner.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class ShoppingList {

    public final String name;
    public final HashMap<Integer, FoodQuantity> foodQuantities;

    public ShoppingList(String name, MealPlan[] mealPlans, Fridge fridge) {
        this.name = name;
        this.foodQuantities = getMissingFood(mealPlans, fridge);
    }

    private HashMap<Integer, FoodQuantity> getMissingFood(MealPlan[] mealPlans, Fridge fridge) {
        HashMap<Integer, FoodQuantity> missingFoodQuantities = new HashMap<>();

        for (MealPlan mealPlan : mealPlans) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String today = formatter.format(new Date());
            String mealPlanDate = formatter.format(mealPlan.getDate());
            if (mealPlan.getDate().before(new Date()) && !(today.equals(mealPlanDate))) {
                continue;
            }

            for (Recipe recipe : mealPlan.getRecipes().values()) {
                for (Entry<Integer, FoodQuantity> foodQuantity : recipe.getFoods().entrySet()) {
                    FoodQuantity existingFoodQuantity = new FoodQuantity(foodQuantity.getValue().food, 0);
                    if (missingFoodQuantities.containsKey(foodQuantity.getKey())) {
                        existingFoodQuantity.quantity = (int) missingFoodQuantities.get(foodQuantity.getKey()).quantity;
                    }

                    existingFoodQuantity.quantity += foodQuantity.getValue().quantity;
                    missingFoodQuantities.put(foodQuantity.getKey(), existingFoodQuantity);
                }
            }
        }

        for (Entry<Integer, FoodQuantity> fridgeFoodQuantity : fridge.getFoods().entrySet()) {
            if (missingFoodQuantities.containsKey(fridgeFoodQuantity.getKey())) {
                FoodQuantity neededFoodQuantity = missingFoodQuantities.get(fridgeFoodQuantity.getKey());

                FoodQuantity missingFoodQuantity = neededFoodQuantity;
                missingFoodQuantity.quantity -= fridgeFoodQuantity.getValue().quantity;
                if (missingFoodQuantity.quantity > 0) {
                    missingFoodQuantities.put(fridgeFoodQuantity.getKey(), missingFoodQuantity);
                } else {
                    missingFoodQuantities.remove(fridgeFoodQuantity.getKey());
                }
            }
        }

        return missingFoodQuantities;
    }
}
