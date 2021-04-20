// This project has no license.
package mealplanner.models;

import java.util.HashMap;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class Fridge {

    private HashMap<Food, Integer> foods;

    public Fridge(HashMap<Food, Integer> foods) {
        this.foods = foods;
    }

    public Fridge() {
        this.foods = new HashMap<>();
    }

    public HashMap<Food, Integer> getFoods() {
        return foods;
    }

    public void setFoods(HashMap<Food, Integer> foods) {
        this.foods = foods;
    }
}
