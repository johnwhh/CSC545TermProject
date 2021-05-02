// This project has no license.
// Created on: 16-04-2021
package mealplanner.models;

import java.util.HashMap;

/**
 *
 * @author johnholtzworth
 */
public class Fridge {

    private HashMap<Integer, FoodQuantity> foods;

    public Fridge(HashMap<Integer, FoodQuantity> foods) {
        this.foods = foods;
    }

    public Fridge() {
        this.foods = new HashMap<>();
    }

    public HashMap<Integer, FoodQuantity> getFoods() {
        return foods;
    }

    public void setFoods(HashMap<Integer, FoodQuantity> foods) {
        this.foods = foods;
    }
}
