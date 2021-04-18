// This project has no license.
package mealplanner.controllers;

import java.awt.Color;
import javax.swing.JPanel;
import mealplanner.MealPlanner;
import mealplanner.models.*;

/**
 * @date 18-04-2021
 * @author johnholtzworth
 */
public class ShoppingListViewController extends JPanel {

    private final MealPlanModel mealPlanModel;
    private final FridgeModel fridgeModel;

    public ShoppingListViewController() {
        this.fridgeModel = new FridgeModel();
        this.mealPlanModel = new MealPlanModel();

        setupPanel();
    }

    private void setupPanel() {
        setLayout(null);
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.WHITE);
    }
}
