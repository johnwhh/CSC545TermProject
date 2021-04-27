// This project has no license.
package mealplanner.controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import mealplanner.MealPlanner;
import mealplanner.models.*;
import mealplanner.views.ListView;
import mealplanner.views.ListViewDataSource;

/**
 * @date 18-04-2021
 * @author johnholtzworth
 */
public class ShoppingListViewController extends JPanel implements ListViewDataSource {

    private final MealPlanModel mealPlanModel;
    private final FridgeModel fridgeModel;

    private ListView foodListView;
    private String[] missingFoodNames;
    
    public ShoppingListViewController() {
        this.fridgeModel = new FridgeModel();
        this.mealPlanModel = new MealPlanModel();

        updateMissingFood();
        setupPanel();
    }

    private void setupPanel() {
        setLayout(null);
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.WHITE);
        
        foodListView = new ListView("Your Shopping List");
        foodListView.setBounds(0, 0, 440, getBounds().height - 150);
        foodListView.setBackground(Color.WHITE);
        foodListView.dataSource = this;
        add(foodListView);
        
        JButton refreshButton = new JButton();
        refreshButton.addActionListener((ActionEvent e) -> {
            updateMissingFood();
            foodListView.reloadData();
        });
        refreshButton.setText("Refresh");
        refreshButton.setBounds((440 / 2) - 50, getBounds().height - 140, 100, 30);
        add(refreshButton);
    }

    private void updateMissingFood() {
        List<String> foodNameList = new ArrayList<>();
        List<MealPlan> mealPlanList = new ArrayList<>(mealPlanModel.getMealPlans().values());
        MealPlan[] mealPlanArray = new MealPlan[mealPlanList.size()];
        mealPlanList.toArray(mealPlanArray);
        
        ShoppingList shoppingList = new ShoppingList("Kroger List", mealPlanArray, fridgeModel.getFridge());
        shoppingList.foodQuantities.forEach((foodId, foodQuantity) -> {
            foodNameList.add(foodQuantity.food.getName() + " (" + foodQuantity.quantity + ")");
        });
        
        missingFoodNames = new String[foodNameList.size()];
        foodNameList.toArray(missingFoodNames);
    }
    
    @Override
    public int numberOfRows(ListView listView) {
        return missingFoodNames.length;
    }

    @Override
    public String contentsOfRow(ListView listView, int row) {
        return missingFoodNames[row];
    }
}
