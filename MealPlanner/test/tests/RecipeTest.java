// This project has no license.
package tests;

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
public class RecipeTest {

    public RecipeTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddUpdateCategoryRemoveRecipe() {
        final int ID = 999;

        Recipe recipe = new Recipe(ID, "Mystery Recipe", "1. Clap hand", Recipe.Category.DESSERT, new HashMap<>());
        RecipeModel recipeModel = new RecipeModel();
        recipeModel.addRecipe(recipe);

        assertEquals(true, recipeModel.getRecipes().containsKey(ID));

        recipe.setCategory(Recipe.Category.SNACK);
        recipeModel.updateRecipe(ID, recipe);
        assertEquals(Recipe.Category.SNACK, recipeModel.getRecipes().get(ID).getCategory());

        recipeModel.removeRecipe(ID);
        assertEquals(0, recipeModel.getRecipes().size());
    }
}
