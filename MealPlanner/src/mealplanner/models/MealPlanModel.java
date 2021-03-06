// This project has no license.
// Created on: 16-04-2021
package mealplanner.models;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Predicate;
import mealplanner.DatabaseManager;
import oracle.jdbc.OraclePreparedStatement;

/**
 *
 * @author johnholtzworth
 */
public class MealPlanModel {

    private HashMap<Integer, MealPlan> mealPlans;

    public MealPlanModel() {
        fetchMealPlans();
    }

    private void fetchMealPlans() {
        mealPlans = new HashMap<>();

        String statement = "SELECT * FROM mealPlan";
        DatabaseManager.getData(statement, (resultSet) -> {
            try {
                int mealPlanId = resultSet.getInt("ID");
                MealPlan.Type type = MealPlan.Type.values()[resultSet.getInt("type")];
                Date date = resultSet.getDate("mealDate");

                HashMap<Integer, Recipe> recipes = new HashMap<>();
                String recipeStatement = "SELECT * FROM recipe, recipeMealPlan WHERE recipe.ID = recipeMealPlan.recipeID AND recipeMealPlan.mealPlanID = " + mealPlanId;
                DatabaseManager.getData(recipeStatement, (resultSet2) -> {
                    Recipe recipe = DatabaseManager.getRecipe(resultSet2, "recipeID");
                    if (recipe != null) {
                        recipes.put(recipe.getId(), recipe);
                    }
                });

                MealPlan mealPlan = new MealPlan(mealPlanId, type, date, recipes);
                mealPlans.put(mealPlanId, mealPlan);
            } catch (SQLException e) {
                System.out.println(e);
            }
        });
    }

    public HashMap<Integer, MealPlan> getMealPlans() {
        return mealPlans;
    }

    public HashMap<Integer, MealPlan> getMealPlans(Predicate<MealPlan> predicate) {
        HashMap<Integer, MealPlan> dictionary = new HashMap<>();
        mealPlans.entrySet().forEach(entry -> {
            Integer id = entry.getKey();
            MealPlan mealPlan = entry.getValue();
            if (predicate.test(mealPlan)) {
                dictionary.put(id, mealPlan);
            }
        });
        return dictionary;
    }

    public void addMealPlan(MealPlan mealPlan) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "INSERT INTO mealPlan VALUES (?, ?, ?)";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, mealPlan.getId());
                preparedStatement.setInt(2, mealPlan.getType().ordinal());
                preparedStatement.setDate(3, new java.sql.Date(mealPlan.getDate().getTime()));

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        for (Entry<Integer, Recipe> recipe : mealPlan.getRecipes().entrySet()) {
            DatabaseManager.updateData((connection) -> {
                try {
                    String statement = "INSERT INTO recipeMealPlan VALUES (?, ?)";
                    OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                    preparedStatement.setInt(1, recipe.getKey());
                    preparedStatement.setInt(2, mealPlan.getId());

                    return preparedStatement;
                } catch (SQLException e) {
                    System.out.println(e);
                }

                return null;
            });
        }

        fetchMealPlans();
    }

    public void removeMealPlan(int id) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "DELETE FROM recipeMealPlan WHERE mealPlanID = ?";
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
                String statement = "DELETE FROM mealPlan WHERE ID = ?";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, id);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        fetchMealPlans();
    }

    public void updateMealPlan(int id, MealPlan mealPlan) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "UPDATE mealPlan SET type = ?, mealDate = ? WHERE ID = ?";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, mealPlan.getType().ordinal());
                preparedStatement.setDate(2, new java.sql.Date(mealPlan.getDate().getTime()));
                preparedStatement.setInt(3, id);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        // Remove recipes that are no longer in the meal plan
        mealPlans.get(id).getRecipes().forEach((recipeId, recipe) -> {
            System.out.println("Does new meal plan contain recipe with id: " + recipeId + "? " + mealPlan.getRecipes().containsKey(recipeId));
            if (mealPlan.getRecipes().containsKey(recipeId) == false) {
                DatabaseManager.updateData((connection) -> {
                    try {
                        String statement = "DELETE FROM recipeMealPlan WHERE recipeID = ? AND mealPlanID = ?";
                        OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                        preparedStatement.setInt(1, recipeId);
                        preparedStatement.setInt(2, id);

                        return preparedStatement;
                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                    return null;
                });
            }
        });

        // Add recipes that were not apart of the meal plan before
        mealPlan.getRecipes().forEach((recipeId, recipe) -> {
            System.out.println("Does old meal plan contain recipe with id: " + recipeId + "? " + mealPlans.get(id).getRecipes().containsKey(recipeId));

            if (mealPlans.get(id).getRecipes().containsKey(recipeId) == false) {
                DatabaseManager.updateData((connection) -> {
                    try {
                        String statement = "INSERT INTO recipeMealPlan VALUES (?, ?)";
                        OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                        preparedStatement.setInt(1, recipeId);
                        preparedStatement.setInt(2, id);

                        return preparedStatement;
                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                    return null;
                });
            }
        });

        fetchMealPlans();
    }
}
