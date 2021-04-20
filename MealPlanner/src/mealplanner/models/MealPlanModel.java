// This project has no license.
package mealplanner.models;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Predicate;
import mealplanner.DatabaseManager;
import oracle.jdbc.OraclePreparedStatement;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class MealPlanModel {

    private HashMap<Integer, MealPlan> mealPlans;

    public MealPlanModel() {
        fetchMealPlans();
    }

    private void fetchMealPlans() {
        mealPlans = new HashMap<>();

        String statement = "SELECT * FROM mealPlan, recipeMealPlan, recipe WHERE mealPlan.id = recipeMealPlan.mealPlanID AND recipeMealPlan.recipeID = recipe.id";
        DatabaseManager.getData(statement, (resultSet) -> {
            try {
                int mealPlanId = resultSet.getInt("mealPlanID");
                MealPlan.Type type = MealPlan.Type.values()[resultSet.getInt("type")];
                Date date = resultSet.getDate("mealDate");
                Recipe recipe = DatabaseManager.getRecipe(resultSet, "recipeID");

                if (recipe != null) {
                    MealPlan mealPlan = new MealPlan(mealPlanId, type, date, recipe);
                    mealPlans.put(mealPlanId, mealPlan);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        });
    }

    public HashMap<Integer, MealPlan> getMealPlans() {
        return mealPlans;
    }

    public HashMap<Integer, MealPlan> getFoods(Predicate<MealPlan> predicate) {
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

        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "INSERT INTO recipeMealPlan VALUES (?, ?)";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, mealPlan.getRecipe().getId());
                preparedStatement.setInt(2, mealPlan.getId());

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

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

        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "UPDATE recipeMealPlan SET recipeID = ? WHERE mealPlanID = ?";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, mealPlan.getRecipe().getId());
                preparedStatement.setInt(2, id);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        fetchMealPlans();
    }
}
