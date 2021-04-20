// This project has no license.
package mealplanner.controllers;

import javax.swing.JPanel;
import javax.swing.*;
import mealplanner.MealPlanner;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class TabbedViewController extends JTabbedPane {

    public static final int PADDING = 20;

    public TabbedViewController() {
        makeTabs();
    }

    private void makeTabs() {
        setBounds(PADDING,
                PADDING,
                MealPlanner.FRAME_WIDTH - (PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (PADDING * 3));

        FridgeViewController fridgeViewController = new FridgeViewController();
        addPanel(fridgeViewController, "Fridge");

        RecipeViewController recipeViewController = new RecipeViewController();
        addPanel(recipeViewController, "Recipes");

        MealPlanViewController mealPlanViewController = new MealPlanViewController();
        addPanel(mealPlanViewController, "Meal Plans");

        ShoppingListViewController shoppingListViewController = new ShoppingListViewController();
        addPanel(shoppingListViewController, "Shopping List");
    }

    private void addPanel(JPanel panel, String title) {
        addTab(title, null, panel,
                "Explore your " + title);
    }
}
