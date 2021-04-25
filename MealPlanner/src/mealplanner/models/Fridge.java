// This project has no license.
package mealplanner.models;

import java.util.HashMap;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class Fridge {

    private HashMap<Integer, Object[]> foods;

    public Fridge(HashMap<Integer, Object[]> foods) {
        this.foods = foods;
    }

    public Fridge() {
        this.foods = new HashMap<>();
    }

    public HashMap<Integer, Object[]> getFoods() {
        return foods;
    }

    public void setFoods(HashMap<Integer, Object[]> foods) {
        this.foods = foods;
    }
}
