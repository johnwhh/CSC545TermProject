// This project has no license.
package mealplanner.models;

import java.util.HashMap;
import java.util.function.Predicate;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class FoodModel {

    private HashMap<Integer, Food> foods;

    public FoodModel() {
        fetchFoods();
    }

    private void fetchFoods() {
        foods = new HashMap<>();

        // TODO: Fetch data from database
    }

    private void updateFoods() {
        // TODO: Update database with data from model
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
        foods.put(food.getId(), food);
        
        updateFoods();
    }

    public void removeFood(int id) {
        foods.remove(id);
        
        updateFoods();
    }

    public void updateFood(int id, Food food) {
        foods.put(id, food);
        
        updateFoods();
    }
}
