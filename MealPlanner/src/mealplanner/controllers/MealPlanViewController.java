// This project has no license.
package mealplanner.controllers;

import java.awt.Color;
import java.util.HashMap;
import javax.swing.JPanel;
import mealplanner.MealPlanner;
import mealplanner.models.*;
import mealplanner.views.MealPlanListView;

/**
 * @date 18-04-2021
 * @author johnholtzworth
 */
public class MealPlanViewController extends JPanel {

    private final MealPlanModel mealPlanModel;

    public MealPlanViewController() {
        this.mealPlanModel = new MealPlanModel();

        setupPanel();
    }

    private void setupPanel() {
        mealPlanModel.getMealPlans();
        MealPlanListView mealPlanListView = new MealPlanListView();
        mealPlanListView.setBounds(this.getBounds());
        add(mealPlanListView);
        mealPlanListView.setVisible(true);

        
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.gray);
    }
}
