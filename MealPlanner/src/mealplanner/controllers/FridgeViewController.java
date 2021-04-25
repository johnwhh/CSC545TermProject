// This project has no license.
package mealplanner.controllers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import mealplanner.MealPlanner;
import mealplanner.models.*;
import mealplanner.views.ListView;
import mealplanner.views.ListViewDataSource;
import mealplanner.views.ListViewDelegate;

/**
 * @date 18-04-2021
 * @author johnholtzworth
 */
public class FridgeViewController extends JPanel implements ListViewDataSource, ListViewDelegate {

    private final FridgeModel fridgeModel;
    private final FoodModel foodModel;

    private ListView listView;
    private HashMap<Food, Integer> fridgeFoods;
    private String[] foodNames;
    private int[] fridgeFoodIds;
    
    public FridgeViewController() {
        this.fridgeModel = new FridgeModel();
        this.foodModel = new FoodModel();

        updateFridgeFoods();
        setupPanel();
    }

    private void setupPanel() {
        setLayout(null);
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.WHITE);

        listView = new ListView("Your Food");
        listView.dataSource = this;
        listView.delegate = this;
        add(listView);
    }

    private void updateFridgeFoods() {
        fridgeFoods = fridgeModel.getFridge().getFoods();
        List<String> foodNameList = new ArrayList<>();

        fridgeFoods.forEach((food, quantity) -> {
            foodNameList.add(food.getName());
        });

        foodNames = new String[foodNameList.size()];
        foodNameList.toArray(foodNames);
    }
//    
//    private String[] getFoodNames() {
//        var foods = foodModel.getFoods();
//        List<String> foodNameList = new ArrayList<>();
//
//        foods.forEach((id, food) -> {
//            foodNameList.add(food.getName());
//        });
//
//        String[] foodNames = new String[foodNameList.size()];
//        foodNameList.toArray(foodNames);
//        return foodNames;
//    }

    @Override
    public int numberOfRows(ListView listView) {
        return foodNames.length;
    }

    @Override
    public String contentsOfRow(ListView listView, int row) {
        return foodNames[row];
    }

    @Override
    public void didSelectRow(ListView listView, int row) {
        System.out.println("Selected food" + row);
    }
}
