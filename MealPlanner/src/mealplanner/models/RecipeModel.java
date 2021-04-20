// This project has no license.
package mealplanner.models;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Predicate;
import mealplanner.DatabaseManager;
import oracle.jdbc.OraclePreparedStatement;

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

        String statement = "SELECT * FROM recipe";
        DatabaseManager.getData(statement, (resultSet) -> {
            Recipe recipe = DatabaseManager.getRecipe(resultSet, "ID");

            if (recipe != null) {
                recipes.put(recipe.getId(), recipe);
            }
        });
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
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "INSERT INTO recipe VALUES (?, ?, ?, ?)";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, recipe.getId());
                preparedStatement.setString(2, recipe.getName());
                preparedStatement.setString(3, recipe.getInstructions());
                preparedStatement.setInt(4, recipe.getCategory().ordinal());

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        recipe.getFoods().forEach((food, quantity) -> {
            DatabaseManager.updateData((connection) -> {
                try {
                    String statement = "INSERT INTO recipeFood VALUES (?, ?, ?)";
                    OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                    preparedStatement.setInt(1, recipe.getId());
                    preparedStatement.setInt(2, food.getId());
                    preparedStatement.setInt(3, quantity);

                    return preparedStatement;
                } catch (SQLException e) {
                    System.out.println(e);
                }

                return null;
            });
        });

        fetchRecipes();
    }

    public void removeRecipe(int id) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "DELETE FROM recipeFood WHERE recipeID = ?";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, id);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "DELETE FROM recipe WHERE ID = ?";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, id);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        fetchRecipes();
    }

    public void updateRecipe(int id, Recipe recipe) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "UPDATE recipe SET name = ?, instructions = ?, category = ? WHERE ID = ?";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setString(1, recipe.getName());
                preparedStatement.setString(2, recipe.getInstructions());
                preparedStatement.setInt(3, recipe.getCategory().ordinal());
                preparedStatement.setInt(4, id);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        // Remove foods that are no longer in the recipe
        recipes.get(id).getFoods().forEach((food, quantity) -> {
            if (recipe.getFoods().containsKey(food) == false) {
                DatabaseManager.updateData((connection) -> {
                    try {
                        String statement = "DELETE FROM recipeFood WHERE recipeID = ? AND foodID = ?";
                        OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                        preparedStatement.setInt(1, id);
                        preparedStatement.setInt(2, food.getId());

                        return preparedStatement;
                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                    return null;
                });
            }
        });

        // Add foods that were not in the recipe before
        recipe.getFoods().forEach((food, quantity) -> {
            if (recipes.get(id).getFoods().containsKey(food) == false) {
                DatabaseManager.updateData((connection) -> {
                    try {
                        String statement = "INSERT INTO recipeFood VALUES (?, ?, ?)";
                        OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                        preparedStatement.setInt(1, recipe.getId());
                        preparedStatement.setInt(2, food.getId());
                        preparedStatement.setInt(3, quantity);

                        return preparedStatement;
                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                    return null;
                });
            }
        });

        // Update quantities of foods in recipe
        recipe.getFoods().forEach((food, quantity) -> {
            DatabaseManager.updateData((connection) -> {
                try {
                    String statement = "UPDATE recipeFood SET (?, ?, ?)";
                    OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                    preparedStatement.setInt(1, recipe.getId());
                    preparedStatement.setInt(2, food.getId());
                    preparedStatement.setInt(3, quantity);

                    return preparedStatement;
                } catch (SQLException e) {
                    System.out.println(e);
                }

                return null;
            });
        });

        fetchRecipes();
    }
}