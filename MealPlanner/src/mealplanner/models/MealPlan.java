// This project has no license.
package mealplanner.models;

import java.util.Date;
import java.util.HashMap;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class MealPlan {

    public enum Type {
        BREAKFAST,
        LUNCH,
        DINNER;
        
        @Override
        public String toString() {
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }

    private int id;
    private Type type;
    private Date date;
    private HashMap<Integer, Recipe> recipes;

    public MealPlan(int id, Type type, Date date, HashMap<Integer, Recipe> recipe) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.recipes = recipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashMap<Integer, Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(HashMap<Integer, Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "MealPlan{" + "id=" + id + ", type=" + type + ", date=" + date + ", recipe=" + recipes + '}';
    }
}
