// This project has no license.
// Created on: 18-04-2021
package mealplanner;

import java.sql.Connection;
import java.util.HashMap;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mealplanner.models.Food;
import mealplanner.models.FoodQuantity;
import mealplanner.models.Recipe;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
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
            System.out.println("Update data error: " + e);
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

            HashMap<Integer, FoodQuantity> foods = new HashMap<>();
            String foodsStatement = "SELECT * FROM food, recipeFood WHERE food.ID = recipeFood.foodID AND recipeFood.recipeID = " + recipeId;
            getData(foodsStatement, (foodResultSet) -> {
                try {
                    int quantity = foodResultSet.getInt("quantity");
                    Food food = getFood(foodResultSet, "foodID");
                    FoodQuantity foodQuantity = new FoodQuantity(food, quantity);

                    if (food != null) {
                        foods.put(food.getId(), foodQuantity);
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

    public static int getAvailableId(Class type, List<Integer> usedIds) {
        String className = type.getSimpleName();
        
        if (className.equals("Food") || className.equals("MealPlan") || className.equals("Recipe")) {
            List<Integer> ids = usedIds;

            if (ids == null) {
                String statement = "SELECT * FROM " + className.substring(0, 1).toLowerCase() + className.substring(1).toLowerCase();
                
                List<Integer> tempIds = new ArrayList<>();
                getData(statement, (resultSet) -> {
                    try {
                        int id = resultSet.getInt("ID");
                        tempIds.add(id);
                    } catch (SQLException e) {
                    }
                });
                ids = tempIds;
            }
            
            Collections.sort(ids);

            int currentId = 0;
            for (Integer id : ids) {
                if (currentId != id) {
                    return currentId;
                }
                currentId++;
            }

            return currentId;
        }

        return -1;
    }
}
