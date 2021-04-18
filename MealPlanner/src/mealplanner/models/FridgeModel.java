// This project has no license.
package mealplanner.models;

import java.util.HashMap;
import java.util.function.Predicate;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class FridgeModel {

    private Fridge fridge;

    public FridgeModel() {
        fetchFridge();
    }

    private void fetchFridge() {
        fridge = new Fridge();

        // TODO: Fetch data from database
    }

    private void updateFridge() {
        // TODO: Update database with data from model
    }

    public Fridge getFridge() {
        return fridge;
    }

    public Fridge getFridge(Predicate<Food> predicate) {
        HashMap<Food, Integer> dictionary = new HashMap<>();
        fridge.getFoods().entrySet().forEach(entry -> {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            if (predicate.test(food)) {
                dictionary.put(food, quantity);
            }
        });

        return new Fridge(dictionary);
    }

    public void addFood(Food food, int quantity) {
        HashMap<Food, Integer> foods = fridge.getFoods();
        foods.put(food, quantity);
        fridge.setFoods(foods);

        updateFridge();
    }

    public void removeFood(Food food, int quantity) {
        HashMap<Food, Integer> foods = fridge.getFoods();
        foods.remove(food);
        fridge.setFoods(foods);

        updateFridge();
    }
}
