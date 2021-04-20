// This project has no license.
package database;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.*;
import mealplanner.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @date 19-04-2021
 * @author johnholtzworth
 */
public class FridgeTest {

    public FridgeTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFridgeExists() {
        FridgeModel fridgeModel = new FridgeModel();
        Fridge fridge = fridgeModel.getFridge();

        assertNotNull(fridge);
    }

    @Test
    public void testAddUpdateQuantityRemoveFridgeFood() {
        final int ID = 999;
        FridgeModel fridgeModel = new FridgeModel();
        Fridge fridge = fridgeModel.getFridge();
        assertNotNull(fridge);

        FoodModel foodModel = new FoodModel();
        Food newFood = new Food(ID, "Mysterious Food", Food.Group.VEGETABLES, 100, 200, 300, 400, 500, 600, 700);
        foodModel.addFood(newFood);

        fridgeModel.addFood(ID, 2);

        List<Food> foodList = new ArrayList<>(fridgeModel.getFridge().getFoods().keySet());
        assertTrue("Food list size is greater than 0", foodList.size() > 0);

        assertEquals(ID, foodList.get(0).getId());

        fridgeModel.updateFoodQuantity(ID, 5);
        List<Integer> values = new ArrayList<>(fridgeModel.getFridge().getFoods().values());
        assertTrue("Quantity list size is greater than 0", values.size() > 0);
        assertEquals(5, values.get(0).intValue());

        fridgeModel.removeFood(ID);
        assertEquals(0, fridgeModel.getFridge().getFoods().size());

        foodModel.removeFood(ID);
        assertEquals(0, foodModel.getFoods().size());
    }
}
