// This project has no license.
// Created on: 16-04-2021
package mealplanner.models;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Predicate;
import mealplanner.DatabaseManager;
import oracle.jdbc.OraclePreparedStatement;

/**
 *
 * @author johnholtzworth
 */
public class FoodModel {

    private HashMap<Integer, Food> foods;

    public FoodModel() {
        fetchFoods();
    }

    private void fetchFoods() {
        foods = new HashMap<>();

        String statement = "SELECT * FROM food";
        DatabaseManager.getData(statement, (resultSet) -> {
            Food food = DatabaseManager.getFood(resultSet, "ID");

            if (food != null) {
                foods.put(food.getId(), food);
            }
        });
    }

    public HashMap<Integer, Food> getFoods() {
        return foods;
    }

    public HashMap<Integer, Food> getFoods(Predicate<Food> predicate) {
        HashMap<Integer, Food> dictionary = new HashMap<>();
        foods.entrySet().forEach(entry -> {
            Integer id = entry.getKey();
            Food food = entry.getValue();
            if (predicate.test(food)) {
                dictionary.put(id, food);
            }
        });
        return dictionary;
    }

    public void addFood(Food food) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "INSERT INTO food VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, food.getId());
                preparedStatement.setString(2, food.getName());
                preparedStatement.setInt(3, food.getGroup().ordinal());
                preparedStatement.setInt(4, food.getCalories());
                preparedStatement.setInt(5, food.getSugar());
                preparedStatement.setInt(6, food.getProtein());
                preparedStatement.setInt(7, food.getSodium());
                preparedStatement.setInt(8, food.getFat());
                preparedStatement.setInt(9, food.getCholesterol());
                preparedStatement.setInt(10, food.getCarbs());

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        fetchFoods();
    }

    public void removeFood(int id) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "DELETE FROM food WHERE ID = ?";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, id);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        fetchFoods();
    }

    public void updateFood(int id, Food food) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "UPDATE food SET name = ?, foodGroup = ?, calories = ?, sugar = ?, protein = ?, sodium = ?, fat = ?, cholesterol = ?, carbs = ? WHERE ID = ?";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setString(1, food.getName());
                preparedStatement.setInt(2, food.getGroup().ordinal());
                preparedStatement.setInt(3, food.getCalories());
                preparedStatement.setInt(4, food.getSugar());
                preparedStatement.setInt(5, food.getProtein());
                preparedStatement.setInt(6, food.getSodium());
                preparedStatement.setInt(7, food.getFat());
                preparedStatement.setInt(8, food.getCholesterol());
                preparedStatement.setInt(9, food.getCarbs());
                preparedStatement.setInt(10, id);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        fetchFoods();
    }
}
