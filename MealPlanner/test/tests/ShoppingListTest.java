// This project has no license.
package tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import static junit.framework.Assert.*;
import mealplanner.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @date 19-04-2021
 * @author johnholtzworth
 */
public class ShoppingListTest {

    public ShoppingListTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMakeValidShoppingList() {
        final int FOOD_QUANTITY = 3;
        
        // Make food and food quantities dictionary
        HashMap<Integer, Object[]> foodQuantities = new HashMap<>();
        
        Food food1 = new Food(0, "Havarti", Food.Group.DAIRY, 10, 9, 8, 7, 6, 5, 4);
        Object[] foodQuantity = new Object[2];
        foodQuantity[0] = food1;
        foodQuantity[1] = FOOD_QUANTITY;
        foodQuantities.put(0, foodQuantity);
        
        Food food2 = new Food(1, "Turkey", Food.Group.PROTEINS, 10, 9, 25, 7, 6, 5, 4);
        Object[] foodQuantity2 = new Object[2];
        foodQuantity2[0] = food2;
        foodQuantity2[1] = FOOD_QUANTITY;
        foodQuantities.put(1, foodQuantity2);
        
        // Make new recipe, add it to recipe dictionary which is added to mealPlan1
        Recipe recipe1 = new Recipe(0, "Chef's Special", "1. Find a chef", Recipe.Category.ENTREE, foodQuantities);
        HashMap<Integer, Recipe> recipes = new HashMap<>();
        recipes.put(0, recipe1);
        MealPlan mealPlan1 = new MealPlan(0, MealPlan.Type.DINNER, new Date(), recipes);

        // Make array of meal plans
        List<MealPlan> mealPlanList = new ArrayList<>();
        mealPlanList.add(mealPlan1);
        MealPlan[] mealPlanArray = new MealPlan[mealPlanList.size()];
        mealPlanList.toArray(mealPlanArray);
        
        Fridge fridge = new Fridge();
        
        ShoppingList shoppingList = new ShoppingList("Kroger List", mealPlanArray, fridge);
        
        HashMap<Food, Integer> expectedFoodQuantities = new HashMap<>();
        expectedFoodQuantities.put(food1, FOOD_QUANTITY);
        expectedFoodQuantities.put(food2, FOOD_QUANTITY);
        assertEquals(expectedFoodQuantities, shoppingList.foodQuantities);
    }
}
