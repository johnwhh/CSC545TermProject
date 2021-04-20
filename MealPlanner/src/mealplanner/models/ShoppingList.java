// This project has no license.
package mealplanner.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class ShoppingList {

    public final String name;
    public final Food[] foods;

    public ShoppingList(String name, MealPlan[] mealPlans, Fridge fridge) {
        this.name = name;
        this.foods = getMissingFood(mealPlans, fridge);
    }

    private Food[] getMissingFood(MealPlan[] mealPlans, Fridge fridge) {
        List<Food> foodList = new ArrayList<>();

        // TODO: Add logic to get missing food
        
        return (Food[]) foodList.toArray();
    }
}
