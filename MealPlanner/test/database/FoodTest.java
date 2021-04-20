// This project has no license.
package database;

import static junit.framework.Assert.*;
import mealplanner.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @date 19-04-2021
 * @author johnholtzworth
 */
public class FoodTest {

    public FoodTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddUpdateCaloriesRemoveFood() {
        final int id = 999;
        FoodModel foodModel = new FoodModel();

        Food newFood = new Food(id, "Mysterious Food", Food.Group.VEGETABLES, 100, 200, 300, 400, 500, 600, 700);
        foodModel.addFood(newFood);
        assertEquals(true, foodModel.getFoods().containsKey(id));

        final int newCalories = 42;
        newFood.setCalories(newCalories);
        foodModel.updateFood(id, newFood);
        assertEquals(newCalories, foodModel.getFoods().get(id).getCalories());

        foodModel.removeFood(id);
        assertEquals(false, foodModel.getFoods().containsKey(id));
    }
}
