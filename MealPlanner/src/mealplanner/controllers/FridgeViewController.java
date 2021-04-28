// This project has no license.
package mealplanner.controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mealplanner.DatabaseManager;
import mealplanner.MealPlanner;
import mealplanner.models.*;
import mealplanner.views.EditFoodView;
import mealplanner.views.ConfirmationView;
import mealplanner.views.ListView;
import mealplanner.views.ListViewDataSource;
import mealplanner.views.ListViewDelegate;
import mealplanner.views.QuantityView;

/**
 * @date 18-04-2021
 * @author johnholtzworth
 */
public class FridgeViewController extends JPanel implements ListViewDataSource, ListViewDelegate {

    private enum State {
        SHOWING_LIST,
        ADDING_FRIDGE_FOOD,
        REMOVING_FRIDGE_FOOD,
        ADDING_FOOD,
        EDITING_FOOD,
        REMOVING_FOOD
    }

    private State state;

    private final FridgeModel fridgeModel;
    private final FoodModel foodModel;

    private ListView fridgeFoodListView;
    private Food selectedFridgeFood = null;
    private HashMap<Integer, FoodQuantity> fridgeFoods;
    private String[] fridgeFoodNames;
    private int[] fridgeFoodIds;

    private ListView foodListView;
    private Food selectedFood = null;
    private HashMap<Integer, Food> foods;
    private String[] foodNames;
    private int[] foodIds;

    public FridgeViewController() {
        this.fridgeModel = new FridgeModel();
        this.foodModel = new FoodModel();

        updateFoods();
        updateFridgeFoods();
        setupPanel();
        setState(State.SHOWING_LIST);
    }

    private void setupPanel() {
        setLayout(null);
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.WHITE);
    }

    private void setState(State state) {
        this.state = state;
        removeAll();
        revalidate();
        repaint();

        switch (state) {
            case SHOWING_LIST -> {
                showFridgeList();
            }
            case ADDING_FRIDGE_FOOD -> {
                showAddFridgeFoodView();
            }
            case REMOVING_FRIDGE_FOOD -> {
                showRemoveFridgeFoodView();
            }
            case ADDING_FOOD -> {
                showAddFoodView();
            }
            case EDITING_FOOD -> {
                showEditFoodView();
            }
            case REMOVING_FOOD -> {
                showRemoveFoodConfirmation();
            }
        }
    }

    private void showFridgeList() {
        fridgeFoodListView = new ListView("Your Fridge");
        fridgeFoodListView.setBackground(Color.WHITE);
        fridgeFoodListView.dataSource = this;
        fridgeFoodListView.delegate = this;
        add(fridgeFoodListView);

        JButton removeFridgeFoodButton = new JButton();
        removeFridgeFoodButton.addActionListener((ActionEvent e) -> {
            if (selectedFridgeFood != null) {
                setState(State.REMOVING_FRIDGE_FOOD);
            }
        });
        removeFridgeFoodButton.setText("Remove food from fridge");
        removeFridgeFoodButton.setBounds((440 / 2) - 100, 300, 200, 30);
        add(removeFridgeFoodButton);

        foodListView = new ListView("Your Food");
        foodListView.setBackground(Color.WHITE);
        foodListView.setBounds(0, 340, 440, 220);
        foodListView.dataSource = this;
        foodListView.delegate = this;
        add(foodListView);

        JButton addFridgeFoodButton = new JButton();
        addFridgeFoodButton.addActionListener((ActionEvent e) -> {
            if (selectedFood != null) {
                setState(State.ADDING_FRIDGE_FOOD);
            }
        });
        addFridgeFoodButton.setText("Add food to fridge");
        addFridgeFoodButton.setBounds(0, 560, 150, 30);
        add(addFridgeFoodButton);

        JButton addFoodButton = new JButton();
        addFoodButton.addActionListener((ActionEvent e) -> {
            setState(State.ADDING_FOOD);
        });
        addFoodButton.setText("Add food");
        addFoodButton.setBounds(150, 560, 90, 30);
        add(addFoodButton);

        JButton editFoodButton = new JButton();
        editFoodButton.addActionListener((ActionEvent e) -> {
            if (selectedFood != null) {
                setState(State.EDITING_FOOD);
            }
        });
        editFoodButton.setText("Edit food");
        editFoodButton.setBounds(240, 560, 90, 30);
        add(editFoodButton);

        JButton removeFoodButton = new JButton();
        removeFoodButton.addActionListener((ActionEvent e) -> {
            if (selectedFood != null) {
                setState(State.REMOVING_FOOD);
            }
        });
        removeFoodButton.setText("Remove food");
        removeFoodButton.setBounds(330, 560, 110, 30);
        add(removeFoodButton);
    }

    private void showRemoveFridgeFoodView() {
        int currentQuantity = (int) fridgeFoods.get(selectedFridgeFood.getId()).quantity;
        QuantityView quantityView = new QuantityView("How many " + selectedFridgeFood.getName() + " would you like to remove from your fridge? You currently have " + currentQuantity + ".", (quantity) -> {
            if (quantity != -1) {
                if (quantity >= currentQuantity) {
                    fridgeModel.removeFood(selectedFridgeFood.getId());
                } else {
                    int newQuantity = currentQuantity - quantity;
                    fridgeModel.updateFoodQuantity(selectedFridgeFood.getId(), newQuantity);
                }

                updateFridgeFoods();
                fridgeFoodListView.reloadData();
            }

            selectedFridgeFood = null;
            setState(State.SHOWING_LIST);
        });
        quantityView.setBounds(0, 0, getBounds().width, getBounds().height);
        quantityView.setBackground(Color.WHITE);
        add(quantityView);
    }

    private void showAddFridgeFoodView() {
        QuantityView quantityView = new QuantityView("How many " + selectedFood.getName() + " would you like to add to your fridge?", (quantity) -> {
            if (quantity != -1) {
                fridgeModel.addFood(selectedFood.getId(), quantity);

                updateFridgeFoods();
                fridgeFoodListView.reloadData();
            }

            selectedFood = null;
            setState(State.SHOWING_LIST);
        });
        quantityView.setBounds(0, 0, getBounds().width, getBounds().height);
        quantityView.setBackground(Color.WHITE);
        add(quantityView);
    }

    private void showAddFoodView() {
        EditFoodView addFoodView = new EditFoodView("Add Food", DatabaseManager.getAvailableId(Food.class, null), null, (food) -> {
            if (food != null) {
                if (food.getName().length() > 50) {
                    JOptionPane.showMessageDialog(null, "Food name must be no longer than 50 characters.", "Invalid Food Name Length", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                foodModel.addFood(food);

                updateFoods();
                foodListView.reloadData();
            }

            selectedFood = null;
            setState(State.SHOWING_LIST);
        });
        addFoodView.setBounds(0, 0, 460, 500);
        addFoodView.setBackground(Color.WHITE);
        add(addFoodView);
    }

    private void showEditFoodView() {
        EditFoodView addFoodView = new EditFoodView("Edit Food", selectedFood.getId(), selectedFood, (food) -> {

            if (food != null) {
                if (food.getName().length() > 50) {
                    JOptionPane.showMessageDialog(null, "Food name must be no longer than 50 characters.", "Invalid Food Name Length", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                foodModel.updateFood(selectedFood.getId(), food);
                updateFoods();
                foodListView.reloadData();
            }

            selectedFood = null;
            setState(State.SHOWING_LIST);
        });
        addFoodView.setBounds(0, 0, 460, 500);
        addFoodView.setBackground(Color.WHITE);
        add(addFoodView);
    }

    private void showRemoveFoodConfirmation() {
        ConfirmationView confirmationView = new ConfirmationView("Are you sure you want to permanently delete this food?", (status) -> {
            if (status == true) {
                foodModel.removeFood(selectedFood.getId());

                updateFoods();
                foodListView.reloadData();

                updateFridgeFoods();
                fridgeFoodListView.reloadData();
            }

            selectedFood = null;
            setState(State.SHOWING_LIST);
        });
        confirmationView.setBounds(0, 0, getBounds().width, getBounds().height);
        confirmationView.setBackground(Color.WHITE);
        add(confirmationView);
    }

    private void updateFridgeFoods() {
        fridgeFoods = fridgeModel.getFridge().getFoods();
        List<String> foodNameList = new ArrayList<>();
        fridgeFoodIds = new int[fridgeFoods.size()];
        int i = 0;

        for (Entry<Integer, FoodQuantity> fridgeFoodQuantity : fridgeFoods.entrySet()) {
            foodNameList.add(fridgeFoodQuantity.getValue().food.getName() + " (" + fridgeFoodQuantity.getValue().quantity + ")");
            fridgeFoodIds[i] = fridgeFoodQuantity.getKey();
            i++;
        }

        fridgeFoodNames = new String[foodNameList.size()];
        foodNameList.toArray(fridgeFoodNames);
    }

    private void updateFoods() {
        foods = foodModel.getFoods();
        List<String> foodNameList = new ArrayList<>();
        foodIds = new int[foods.size()];
        int i = 0;

        for (Entry<Integer, Food> food : foods.entrySet()) {
            foodNameList.add(food.getValue().getName());
            foodIds[i] = food.getKey();
            i++;
        }

        foodNames = new String[foodNameList.size()];
        foodNameList.toArray(foodNames);
    }

    @Override
    public int numberOfRows(ListView listView) {
        if (listView.title.equals("Your Fridge")) {
            return fridgeFoodNames.length;
        }
        return foodNames.length;
    }

    @Override
    public String contentsOfRow(ListView listView, int row) {
        if (listView.title.equals("Your Fridge")) {
            return fridgeFoodNames[row];
        }
        return foodNames[row];
    }

    @Override
    public void didSelectRow(ListView listView, int row) {
        if (listView.title.equals("Your Fridge")) {
            Food food = (Food) fridgeFoods.get(fridgeFoodIds[row]).food;
            selectedFridgeFood = food;
            return;
        }

        Food food = foods.get(foodIds[row]);
        selectedFood = food;
    }
}
