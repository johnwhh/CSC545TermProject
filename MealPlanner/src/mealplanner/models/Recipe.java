// This project has no license.
package mealplanner.models;

import java.util.HashMap;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class Recipe {

    public enum Category {
        DESSERT,            // 0
        SNACK,              // 1
        APPETIZER,          // 2
        ENTREE              // 3
    }

    private int id;
    private String name;
    private String instructions;
    private Category category;
    private HashMap<Food, Integer> foods;

    public Recipe(int id, String name, String instructions, Category category, HashMap<Food, Integer> foods) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.category = category;
        this.foods = foods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public HashMap<Food, Integer> getFoods() {
        return foods;
    }

    public void setFoods(HashMap<Food, Integer> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "Recipe{" + "id=" + id + ", name=" + name + ", instructions=" + instructions + ", category=" + category + ", foods=" + foods + '}';
    }
}
