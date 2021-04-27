// This project has no license.
package database;

import java.util.HashMap;
import java.util.Map.Entry;
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

    private final int ID = 999;
    private RecipeModel recipeModel;
    FoodModel foodModel;

    public RecipeTest() {
    }

    @Before
    public void setUp() {
        recipeModel = new RecipeModel();
        foodModel = new FoodModel();
    }

    @After
    public void tearDown() {
        recipeModel.removeRecipe(ID);
        foodModel.removeFood(ID);
    }

    @Test
    public void testAddUpdateCategoryRemoveRecipe() {
        Recipe recipe = new Recipe(ID, "Mystery Recipe", "1. Clap hand", Recipe.Category.DESSERT, new HashMap<>());
        recipeModel.addRecipe(recipe);

        assertEquals(true, recipeModel.getRecipes().containsKey(ID));

        recipe.setCategory(Recipe.Category.SNACK);
        recipeModel.updateRecipe(ID, recipe);
        assertEquals(Recipe.Category.SNACK, recipeModel.getRecipes().get(ID).getCategory());

        recipeModel.removeRecipe(ID);
        assertEquals(0, recipeModel.getRecipes().size());
    }

    @Test
    public void testModifyIngredientQuantity() {
        final int expectedQuantity = 2;

        HashMap<Food, Integer> ingredients = new HashMap<>();
        Food ingredient = new Food(ID, "Mysterious Food", Food.Group.VEGETABLES, 100, 200, 300, 400, 500, 600, 700);
        ingredients.put(ingredient, 3);

        foodModel.addFood(ingredient);
        assertEquals(true, foodModel.getFoods().containsKey(ID));

        Recipe recipe = new Recipe(ID, "Mystery Recipe", "1. Clap hand", Recipe.Category.DESSERT, ingredients);
        recipeModel.addRecipe(recipe);

        assertEquals(true, recipeModel.getRecipes().containsKey(ID));

        HashMap<Food, Integer> newIngredients = new HashMap<>();
        newIngredients.put(ingredient, expectedQuantity);
        Recipe newRecipe = new Recipe(ID, "Mystery Recipe", "1. Clap hand", Recipe.Category.DESSERT, newIngredients);

        assertEquals(newRecipe.getFoods().size(), recipeModel.getRecipes().get(ID).getFoods().size());
        assertEquals(true, newRecipe.getFoods().containsKey(ingredient));
        recipeModel.updateRecipe(ID, newRecipe);

        assertEquals(true, recipeModel.getRecipes().containsKey(ID));

        assertEquals(true, isFoodInRecipe(recipeModel, newRecipe));
        int quantity = -1;
        for (Entry<Food, Integer> foodQuantity : recipeModel.getRecipes().get(ID).getFoods().entrySet()) {
            if (foodQuantity.getKey().getId() == ingredient.getId()) {
                quantity = foodQuantity.getValue();
            }
        }
        assertEquals(expectedQuantity, quantity);
    }

    private boolean isFoodInRecipe(RecipeModel recipeModel, Recipe newRecipe) {
        for (Food food : recipeModel.getRecipes().get(ID).getFoods().keySet()) {
            for (Food recipeFood : newRecipe.getFoods().keySet()) {
                if (recipeFood.getId() == food.getId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
