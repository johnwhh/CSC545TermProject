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
public class FridgeModel {

    private static final int FRIDGE_ID = 0;
    private Fridge fridge;

    public FridgeModel() {
        fetchFridge();
    }

    private void fetchFridge() {
        HashMap<Integer, Object[]> foods = new HashMap<>();
        String statement = "SELECT * FROM fridgeFood, food WHERE fridgeFood.foodID = food.ID";
        DatabaseManager.getData(statement, (resultSet) -> {
            try {
                int quantity = resultSet.getInt("quantity");
                Food food = DatabaseManager.getFood(resultSet, "foodID");
                Object[] foodQuantity = {food, quantity};

                if (food != null) {
                    foods.put(food.getId(), foodQuantity);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        });

        fridge = new Fridge(foods);
    }

    public Fridge getFridge() {
        return fridge;
    }

    public Fridge getFridge(Predicate<Food> predicate) {
        HashMap<Integer, Object[]> dictionary = new HashMap<>();
        fridge.getFoods().entrySet().forEach(entry -> {
            Food food = (Food) entry.getValue()[0];
            if (predicate.test(food)) {
                dictionary.put(entry.getKey(), entry.getValue());
            }
        });

        return new Fridge(dictionary);
    }

    public void addFood(int foodId, int quantity) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "INSERT INTO fridgeFood VALUES (?, ?, ?)";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, FRIDGE_ID);
                preparedStatement.setInt(2, foodId);
                preparedStatement.setInt(3, quantity);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        fetchFridge();
    }

    public void removeFood(int foodId) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "DELETE FROM fridgeFood WHERE fridgeID = ? AND foodID = ?";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, FRIDGE_ID);
                preparedStatement.setInt(2, foodId);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        fetchFridge();
    }

    public void updateFoodQuantity(int foodId, int quantity) {
        DatabaseManager.updateData((connection) -> {
            try {
                String statement = "UPDATE fridgeFood SET quantity = ? WHERE fridgeID = ? AND foodID = ?";
                OraclePreparedStatement preparedStatement = (OraclePreparedStatement) connection.prepareStatement(statement);
                preparedStatement.setInt(1, quantity);
                preparedStatement.setInt(2, FRIDGE_ID);
                preparedStatement.setInt(3, foodId);

                return preparedStatement;
            } catch (SQLException e) {
                System.out.println(e);
            }

            return null;
        });

        fetchFridge();
    }
}
