// This project has no license.
package mealplanner.controllers;

import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mealplanner.MealPlanner;
import mealplanner.ModelUpdater;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class TabbedViewController extends JTabbedPane {

    public static final int PADDING = 20;
    private ModelUpdater[] modelUpdaters;

    public TabbedViewController() {
        makeTabs();
    }

    private void makeTabs() {
        setBounds(PADDING,
                PADDING,
                MealPlanner.FRAME_WIDTH - (PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (PADDING * 3));

        modelUpdaters = new ModelUpdater[4];

        FridgeViewController fridgeViewController = new FridgeViewController();
        addPanel(fridgeViewController, "Fridge");
        modelUpdaters[0] = fridgeViewController;

        RecipeViewController recipeViewController = new RecipeViewController();
        addPanel(recipeViewController, "Recipes");
        modelUpdaters[1] = recipeViewController;

        MealPlanViewController mealPlanViewController = new MealPlanViewController();
        addPanel(mealPlanViewController, "Meal Plans");
        modelUpdaters[2] = mealPlanViewController;

        ShoppingListViewController shoppingListViewController = new ShoppingListViewController();
        addPanel(shoppingListViewController, "Shopping List");
        modelUpdaters[3] = shoppingListViewController;

        addChangeListener((ChangeEvent e) -> {
            modelUpdaters[getSelectedIndex()].updateModels();
        });
    }

    private void addPanel(JPanel panel, String title) {
        addTab(title, null, panel,
                "Explore your " + title);
    }
}
