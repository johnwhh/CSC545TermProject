// This project has no license.
package mealplanner.models;

import java.util.Date;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class MealPlan {

    public enum Type {
        BREAKFAST,
        LUNCH,
        DINNER
    }

    private int id;
    private Type type;
    private Date date;
    private Recipe recipe;

    public MealPlan(int id, Type type, Date date, Recipe recipe) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.recipe = recipe;
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

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
