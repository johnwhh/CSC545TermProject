// This project has no license.
package tests;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.*;
import mealplanner.DatabaseManager;
import mealplanner.models.*;
import org.junit.Test;

/**
 * @date 19-04-2021
 * @author johnholtzworth
 */
public class RecipeTest {
    
    public RecipeTest() {
    }

    @Test
    public void testGetAvailableId() {
        List<Integer> usedIds = new ArrayList<>();
        usedIds.add(0);
        usedIds.add(2);

        int id = DatabaseManager.getAvailableId(Recipe.class, usedIds);

        assertEquals(1, id);
    }
}
