// This project has no license.
package tests;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.*;
import mealplanner.DatabaseManager;
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
    public void testGetAvailableId() {
        List<Integer> usedIds = new ArrayList<>();
        usedIds.add(0);
        usedIds.add(2);

        int id = DatabaseManager.getAvailableId(Food.class, usedIds);

        assertEquals(1, id);
    }

}
