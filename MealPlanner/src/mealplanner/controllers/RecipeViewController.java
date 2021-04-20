// This project has no license.
package mealplanner.controllers;

import mealplanner.views.RecipeListView;
import java.awt.Color;
import javax.swing.JPanel;
import mealplanner.MealPlanner;
import mealplanner.models.*;

/**
 * @date 18-04-2021
 * @author johnholtzworth
 */
public class RecipeViewController extends JPanel {

    private final RecipeModel recipeModel;
    private final FoodModel foodModel;

    public RecipeViewController() {
        this.recipeModel = new RecipeModel();
        this.foodModel = new FoodModel();

        setupPanel();
    }

    private void setupPanel() {
        setLayout(null);
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.WHITE);
        
        RecipeListView recipeListView = new RecipeListView();
        recipeListView.setBackground(Color.LIGHT_GRAY);
        recipeListView.setBounds(this.getBounds());
        add(recipeListView);
        recipeListView.setVisible(true);
        
    }
}
