// This project has no license.
package mealplanner.models;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class Food {

    public enum Group {
        FRUITS,
        VEGETABLES,
        GRAINS,
        PROTEINS,
        DAIRY;
        
        @Override
        public String toString() {
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }

    private int id;
    private String name;
    private Group group;
    private int calories;
    private int sugar;
    private int protein;
    private int sodium;
    private int fat;
    private int cholesterol;
    private int carbs;

    public Food(int id, String name, Group group, int calories, int sugar, int protein, int sodium, int fat, int cholesterol, int carbs) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.calories = calories;
        this.sugar = sugar;
        this.protein = protein;
        this.sodium = sodium;
        this.fat = fat;
        this.cholesterol = cholesterol;
        this.carbs = carbs;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    @Override
    public String toString() {
        return "Food{" + "id=" + id + ", name=" + name + ", group=" + group + ", calories=" + calories + ", sugar=" + sugar + ", protein=" + protein + ", sodium=" + sodium + ", fat=" + fat + ", cholesterol=" + cholesterol + ", carbs=" + carbs + '}';
    }
}
