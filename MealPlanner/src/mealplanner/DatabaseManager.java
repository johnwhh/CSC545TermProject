// This project has no license.
package mealplanner;

import java.sql.Connection;
import java.util.HashMap;
import java.sql.SQLException;
import mealplanner.models.Food;
import mealplanner.models.Recipe;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 * @date 18-04-2021
 * @author johnholtzworth
 */
public class DatabaseManager {

    public static void getData(String statement, ResultSetFunction resultSetFunction) {
        Connection connection = (Connection) ConnectDB.setupConnection();
        OraclePreparedStatement preparedStatement = null;
        OracleResultSet resultSet = null;

        try {
            preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);

            resultSet = (OracleResultSet) preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultSetFunction.use(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            ConnectDB.close(resultSet);
            ConnectDB.close(preparedStatement);
            ConnectDB.close(connection);
        }
    }

    public static void updateData(ConnectionFunction connectionFunction) {
        Connection connection = (Connection) ConnectDB.setupConnection();
        OraclePreparedStatement preparedStatement = null;

        try {
            preparedStatement = connectionFunction.use(connection);

            if (preparedStatement != null) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            ConnectDB.close(preparedStatement);
            ConnectDB.close(connection);
        }
    }

    public static Food getFood(OracleResultSet resultSet, String foodIdLabel) {
        try {
            int id = resultSet.getInt(foodIdLabel);
            String name = resultSet.getString("name");
            Food.Group group = Food.Group.values()[resultSet.getInt("foodGroup")];
            int calories = resultSet.getInt("calories");
            int sugar = resultSet.getInt("sugar");
            int protein = resultSet.getInt("protein");
            int sodium = resultSet.getInt("sodium");
            int fat = resultSet.getInt("fat");
            int cholesterol = resultSet.getInt("cholesterol");
            int carbs = resultSet.getInt("carbs");

            return new Food(id, name, group, calories, sugar, protein, sodium, fat, cholesterol, carbs);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public static Recipe getRecipe(OracleResultSet resultSet, String recipeIdLabel) {
        try {
            int recipeId = resultSet.getInt(recipeIdLabel);
            String recipeName = resultSet.getString("name");
            String recipeInstructions = resultSet.getString("instructions");
            Recipe.Category recipeCategory = Recipe.Category.values()[resultSet.getInt("category")];

            HashMap<Food, Integer> foods = new HashMap<>();
            String foodsStatement = "SELECT * FROM food, recipeFood WHERE food.ID = recipeFood.foodID AND recipeFood.recipeID = " + recipeId;
            getData(foodsStatement, (foodResultSet) -> {
                try {
                    int quantity = foodResultSet.getInt("quantity");
                    Food food = getFood(foodResultSet, "foodID");

                    if (food != null) {
                        foods.put(food, quantity);
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            });

            return new Recipe(recipeId, recipeName, recipeInstructions, recipeCategory, foods);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }
}
