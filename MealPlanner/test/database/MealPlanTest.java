// This project has no license.
package database;

import java.util.Date;
import java.util.HashMap;
import static junit.framework.Assert.*;
import mealplanner.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @date 19-04-2021
 * @author johnholtzworth
 */
public class MealPlanTest {

    public MealPlanTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddUpdateTypeRemoveMealPlan() {
        final int ID = 999;
        MealPlanModel mealPlanModel = new MealPlanModel();
        assertEquals(0, mealPlanModel.getMealPlans().size());

        
        Recipe recipe = new Recipe(ID, "Mystery Recipe", "1. Clap hand", Recipe.Category.DESSERT, new HashMap<>());
        RecipeModel recipeModel = new RecipeModel();
        recipeModel.addRecipe(recipe);

        HashMap<Integer, Recipe> recipes = new HashMap<>();
        recipes.put(ID, recipe);
        MealPlan newMealPlan = new MealPlan(ID, MealPlan.Type.LUNCH, new Date(), recipes);
        mealPlanModel.addMealPlan(newMealPlan);

        assertEquals(1, mealPlanModel.getMealPlans().size());
        assertEquals(true, mealPlanModel.getMealPlans().containsKey(ID));

        newMealPlan.setType(MealPlan.Type.BREAKFAST);
        mealPlanModel.updateMealPlan(ID, newMealPlan);
        assertEquals(MealPlan.Type.BREAKFAST, mealPlanModel.getMealPlans().get(ID).getType());

        mealPlanModel.removeMealPlan(ID);
        assertEquals(0, mealPlanModel.getMealPlans().size());
    }
}
