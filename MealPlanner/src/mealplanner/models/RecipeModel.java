// This project has no license.
package mealplanner.models;

import java.util.HashMap;
import java.util.function.Predicate;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class RecipeModel {

    private HashMap<Integer, Recipe> recipes;

    public RecipeModel() {
        fetchRecipes();
    }

    private void fetchRecipes() {
        recipes = new HashMap<>();

        // TODO: Fetch data from database
    }

    private void updateRecipes() {
        // TODO: Update database with data from model
    }

    public HashMap<Integer, Recipe> getRecipes() {
        return recipes;
    }

    public HashMap<Integer, Recipe> getRecipes(Predicate<Recipe> predicate) {
        HashMap<Integer, Recipe> dictionary = new HashMap<>();
        recipes.entrySet().forEach(entry -> {
            Integer id = entry.getKey();
            Recipe recipe = entry.getValue();
            if (predicate.test(recipe)) {
                dictionary.put(id, recipe);
            }
        });
        return dictionary;
    }

    public void addRecipe(Recipe recipe) {
        recipes.put(recipe.getId(), recipe);
        
        updateRecipes();
    }

    public void removeRecipe(int id) {
        recipes.remove(id);

        updateRecipes();
    }
}
