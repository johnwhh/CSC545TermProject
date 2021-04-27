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
        final int FOOD_ID_0 = 0;
        final int FOOD_ID_1 = 1;
        final int FOOD_QUANTITY = 3;
        final int EXPECTED_FOOD_QUANTITY = 3;

        // Make food and food quantities dictionary
        HashMap<Integer, FoodQuantity> foodQuantities = new HashMap<>();

        Food food1 = new Food(FOOD_ID_0, "Havarti", Food.Group.DAIRY, 10, 9, 8, 7, 6, 5, 4);
        FoodQuantity foodQuantity1 = new FoodQuantity(food1, FOOD_QUANTITY);
        foodQuantities.put(FOOD_ID_0, foodQuantity1);

        Food food2 = new Food(FOOD_ID_1, "Turkey", Food.Group.PROTEINS, 10, 9, 25, 7, 6, 5, 4);
        FoodQuantity foodQuantity2 = new FoodQuantity(food2, FOOD_QUANTITY);
        foodQuantities.put(FOOD_ID_1, foodQuantity2);

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
        HashMap<Integer, FoodQuantity> expectedFoodQuantities = new HashMap<>();
        expectedFoodQuantities.put(FOOD_ID_0, new FoodQuantity(food1, EXPECTED_FOOD_QUANTITY));
        expectedFoodQuantities.put(FOOD_ID_1, new FoodQuantity(food2, EXPECTED_FOOD_QUANTITY));
        assertEquals(expectedFoodQuantities, shoppingList.foodQuantities);
    }
}
