// This project has no license.
package mealplanner.controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mealplanner.MealPlanner;
import mealplanner.models.*;
import mealplanner.views.RecipeIngredientAddView;
import mealplanner.views.ListView;
import mealplanner.views.ListViewDataSource;
import mealplanner.views.ListViewDelegate;
import mealplanner.views.RecipeInformationView;

/**
 * @date 18-04-2021
 * @author johnholtzworth
 */
public class RecipeViewController extends JPanel implements ListViewDelegate, ListViewDataSource, ActionListener {

    private final RecipeModel recipeModel;
    private final FoodModel foodModel;
    private final String recipeListViewName = "Your Recipes";
    private final String recipeInformationListViewName = "Ingredients Needed";
    private final String ingredientAddListViewName = "Ingredient Options";
    private ListView recipeListView;
    private ListView recipeInformationListView;
    private ListView ingredientAddListView;
    private RecipeInformationView recipeInformationView;
    private RecipeIngredientAddView recipeIngredientAddView;
    private JButton deleteIngredientButton;
    private JButton createRecipeButton;
    private JButton deleteRecipeButton;
    private JButton backButton;
    private JButton backToRecipeButton;
    private JButton confirmButton;
    private int recipeID = 0;
    private int selectedIngredientRow = 0;
    private String selectedFood = "";

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

        // Sets up the view to show recipe information
        recipeInformationView = new RecipeInformationView();
        recipeInformationView.setBackground(Color.LIGHT_GRAY);
        recipeInformationView.setBounds(0, 0, 460, 615);
        add(recipeInformationView);
        recipeInformationView.setVisible(false);

        // Sets up the view to adding ingredients to recipe
        recipeIngredientAddView = new RecipeIngredientAddView();
        recipeIngredientAddView.setBackground(Color.LIGHT_GRAY);
        recipeIngredientAddView.setBounds(0, 0, 460, 615);
        add(recipeIngredientAddView);
        recipeIngredientAddView.setVisible(false);

        // Sets up the list views
        createRecipeListView();
        createRecipeInformationListView();
        createAddIngredientListView();
    }

    // Gets the recipe ID for later use
    private int getRecipeID(int row) {
        String[] recipeNames = getRecipeNames();
        var recipes = recipeModel.getRecipes();
        String recipeName = recipeNames[row];

        // Iterates through the recipes HashMap and gets the ID of the recipe that has a matching name
        for (int currRecipeID : recipes.keySet()) {
            if (recipes.get(currRecipeID).getName().equals(recipeName)) {
                return currRecipeID;
            }
        }

        return -1;
    }

    // Gets an array of strings that has recipe names
    private String[] getRecipeNames() {
        var recipes = recipeModel.getRecipes();
        List<String> recipeNameList = new ArrayList<>();

        // Gets each name in the recipes HashMap into a list
        recipes.forEach((id, recipe) -> {
            recipeNameList.add(recipe.getName());
        });

        // Converts the list into an array and returns
        String[] recipeNames = new String[recipeNameList.size()];
        recipeNameList.toArray(recipeNames);
        return recipeNames;
    }

    // Gets an array of strings that has food names
    private String[] getFoodNames() {
        var foods = foodModel.getFoods();
        List<String> foodNameList = new ArrayList<>();

        // Gets each name in the foods HashMap into a list
        foods.forEach((id, food) -> {
            foodNameList.add(food.getName());
        });

        // Converts the list into an array and returns
        String[] recipeNames = new String[foodNameList.size()];
        foodNameList.toArray(recipeNames);
        return recipeNames;
    }

    // Gets an array of strings that has ingredient names 
    private String[] getIngredientNames(int rowSpot) {
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(rowSpot);
        var ingredients = recipe.getFoods();
        List<String> ingredientNameList = new ArrayList<>();

        // Gets each name in the ingredients HashMap into a list
        ingredients.forEach((food, quantity) -> {
            ingredientNameList.add(food.getName());
        });

        // Converts the list into an array and returns
        String[] ingredientNames = new String[ingredientNameList.size()];
        ingredientNameList.toArray(ingredientNames);
        return ingredientNames;
    }

    // Gets an array of ints that has ingredient quantities
    private int[] getIngredientQuantities(int rowSpot) {
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(rowSpot);
        var ingredients = recipe.getFoods();
        List<Integer> ingredientQuantityList = new ArrayList<>();

        // Gets each quantity in the ingredients HashMap into a list
        ingredients.forEach((food, quantity) -> {
            ingredientQuantityList.add(quantity);
        });

        // Converts the list into an array and returns
        int[] ingredientQuantities = new int[ingredientQuantityList.size()];
        for (int i = 0; i < ingredientQuantityList.size(); i++) {
            ingredientQuantities[i] = ingredientQuantityList.get(i);
        }
        return ingredientQuantities;
    }

    // Gets the instructions of the recipe with matching ID
    private String getRecipeInstructions(int recipeID) {
        var recipes = recipeModel.getRecipes();
        String recipeInstructions = recipes.get(recipeID).getInstructions();
        return recipeInstructions;
    }

    // Gets the category of the recipe with matching ID
    private String getRecipeCategory(int recipeID) {
        var recipes = recipeModel.getRecipes();

        // Turns the enum Category into a string
        String recipeCategory = recipes.get(recipeID).getCategory().name();
        // Turns the string into first letter capital and rest lower
        recipeCategory = recipeCategory.substring(0, 1) + recipeCategory.substring(1).toLowerCase();
        return recipeCategory;
    }

    // Adds an ingredient to the recipe
    private void recipeAddIngredient(String foodName, int quantity, int recipeID) {
        var foods = foodModel.getFoods();
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(recipeID);
        HashMap<Food, Integer> newIngredients = new HashMap<>();
        int foodKey = -1;

        // Loops through all the foods and gets the key of the matching food name
        for (int currFoodKey : foods.keySet()) {
            if (foods.get(currFoodKey).getName().equals(foodName)) {
                foodKey = currFoodKey;
            }
        }

        // Gets the food with the key
        var newFood = foods.get(foodKey);

        // Puts all the old ingredients in with the new one
        for (Entry<Food, Integer> food : recipe.getFoods().entrySet()) {
            newIngredients.put(food.getKey(), food.getValue());
        }

        newIngredients.put(newFood, quantity);
        // Updates the recipe
        Recipe newRecipe = new Recipe(recipe.getId(), recipe.getName(), recipe.getInstructions(), recipe.getCategory(), newIngredients);
        recipeModel.updateRecipe(recipeID, newRecipe);
    }

    // Deletes an ingredient in the recipe
    private void deleteIngredient(int recipeID, int ingredientRowSpot) {
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(recipeID);
        HashMap<Food, Integer> ingredients = recipe.getFoods();
        HashMap<Food, Integer> newIngredients = new HashMap<>();
        List<String> ingredientNameList = new ArrayList<>();

        // Gets all the ingredient names into a list
        ingredients.forEach((food, quantity) -> {
            ingredientNameList.add(food.getName());
        });

        // Gets the name of the ingredient
        String foodName = ingredientNameList.get(ingredientRowSpot);

        // Removes the food with matching name from ingredients
        for (Entry<Food, Integer> food : recipe.getFoods().entrySet()) {
            if (!foodName.equals(food.getKey().getName())) {
                newIngredients.put(food.getKey(), food.getValue());
            }
        }

        // Updates the recipe
        Recipe newRecipe = new Recipe(recipe.getId(), recipe.getName(), recipe.getInstructions(), recipe.getCategory(), newIngredients);
        recipeModel.updateRecipe(recipeID, newRecipe);
    }

    // Gets a new ID for a recipe being added
    private int getNewRecipeID() {
        var recipes = recipeModel.getRecipes();
        int maxID = -1;

        List<Integer> recipeIDList = new ArrayList<>();

        // Goes through each recipe adding it's ID to a list
        recipes.forEach((ID, recipe) -> {
            recipeIDList.add(ID);
        });

        // Loops through the entire list looking for an open ID spot or if all taken then max ID + 1
        for (int i = 0; i < recipeIDList.size(); i++) {
            int currID = recipeIDList.get(i);
            if (currID != i) {
                return i;
            } else if (currID > maxID) {
                maxID = currID;
            }
        }

        return maxID + 1;
    }

    // Adds a recipe to the system
    private void addRecipe(String name, String category, String instructions) {
        Recipe recipe;
        HashMap<Food, Integer> foods = new HashMap();
        int newRecipeID = getNewRecipeID();

        // Creates a new recipe based off of the category
        recipe = new Recipe(newRecipeID, name, instructions, Recipe.Category.valueOf(category.toUpperCase()), foods);
        recipeModel.addRecipe(recipe);
    }

    // Deletes a recipe out of the system
    private void deleteRecipe(int recipeID) {
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(recipeID);
        recipeModel.removeRecipe(recipe.getId());
    }

    // Creates the recipe list view to display
    private void createRecipeListView() {
        recipeListView = new ListView(recipeListViewName);
        recipeListView.setBackground(Color.LIGHT_GRAY);
        recipeListView.delegate = this;
        recipeListView.dataSource = this;
        recipeListView.setBounds(0, 0, 460, 615);
        add(recipeListView);
        recipeListView.setVisible(true);
    }

    // Creates the recipe information list view to display
    private void createRecipeInformationListView() {
        recipeInformationListView = new ListView(recipeInformationListViewName);
        recipeInformationListView.setBackground(Color.LIGHT_GRAY);
        recipeInformationListView.delegate = this;
        recipeInformationListView.dataSource = this;
        recipeInformationListView.setBounds(0, 300, 460, 200);
        recipeInformationView.add(recipeInformationListView);

        backButton = new JButton("Back");
        recipeInformationView.add(backButton);
        backButton.setBounds(10, 570, 80, 40);
        backButton.addActionListener(this);

        deleteRecipeButton = new JButton("Delete Recipe");
        recipeInformationView.add(deleteRecipeButton);
        deleteRecipeButton.setBounds(325, 570, 120, 40);
        deleteRecipeButton.addActionListener(this);

        createRecipeButton = new JButton("Create Recipe Base");
        recipeInformationView.add(createRecipeButton);
        createRecipeButton.setBounds(160, 325, 150, 40);
        createRecipeButton.addActionListener(this);

        deleteIngredientButton = new JButton("Delete Ingredient");
        recipeInformationView.add(deleteIngredientButton);
        deleteIngredientButton.setEnabled(false);
        deleteIngredientButton.setBounds(170, 500, 130, 40);
        deleteIngredientButton.addActionListener(this);
    }

    // Creates the recipe information list view to display
    private void createAddIngredientListView() {
        ingredientAddListView = new ListView(ingredientAddListViewName);
        ingredientAddListView.setBackground(Color.LIGHT_GRAY);
        ingredientAddListView.delegate = this;
        ingredientAddListView.dataSource = this;
        ingredientAddListView.setBounds(0, 0, 460, 200);
        recipeIngredientAddView.add(ingredientAddListView);

        confirmButton = new JButton("Confirm");
        recipeIngredientAddView.add(confirmButton);
        confirmButton.setBounds(160, 325, 130, 40);
        confirmButton.addActionListener(this);

        backToRecipeButton = new JButton("Back To Recipe");
        recipeIngredientAddView.add(backToRecipeButton);
        backToRecipeButton.setBounds(10, 570, 130, 40);
        backToRecipeButton.addActionListener(this);
    }

    @Override
    public void didSelectRow(ListView listView, int row) {
        if (row >= 0) {
            String[] recipeNames = getRecipeNames();
            switch (listView.title) {

                // For the main recipe list view
                case recipeListViewName -> {

                    backButton.setVisible(true);

                    // If the length == row then it is the add new recipe
                    if (recipeNames.length != row) {
                        createRecipeButton.setVisible(false);
                        deleteRecipeButton.setVisible(true);
                    } else {
                        deleteRecipeButton.setVisible(false);
                        createRecipeButton.setVisible(true);
                    }

                    // Tries to get the recipe ID and if it fails then it is add a new recipe
                    try {
                        recipeID = getRecipeID(row);
                    } catch (Exception e) {
                        System.out.println(e);
                        recipeID = row;
                    }

                    // Hides the recipe list and shows the information view
                    recipeListView.setVisible(false);
                    recipeInformationView.setVisible(true);

                    // Shows either the recipe information or the blank slate
                    if (recipeNames.length != row) {
                        recipeInformationView.setRecipeData(recipeNames[row], getRecipeCategory(recipeID).strip(), getRecipeInstructions(recipeID).strip());
                    } else {
                        recipeInformationView.setRecipeData("Name", "Dessert", "Write Instructions.");
                    }

                    // Decides if needs to show the ingredient section
                    if (recipeNames.length != row) {
                        recipeInformationListView.setVisible(true);
                        deleteIngredientButton.setVisible(true);
                    } else {
                        recipeInformationListView.setVisible(false);
                        deleteIngredientButton.setVisible(false);
                    }
                }

                // If list view title is for information
                case recipeInformationListViewName -> {
                    var recipes = recipeModel.getRecipes();
                    var recipe = recipes.get(recipeID);
                    var ingredients = recipe.getFoods();

                    // Checks if adding new ingredient size == row is new ingredient
                    if (ingredients.size() != row) {
                        deleteIngredientButton.setEnabled(true);
                    } else {

                        // Hides nonneeded button, shows new view
                        deleteIngredientButton.setEnabled(false);
                        deleteIngredientButton.setVisible(false);
                        recipeIngredientAddView.setVisible(true);
                        recipeInformationView.setVisible(false);
                        ingredientAddListView.setVisible(true);
                        ingredientAddListView.reloadData();
                        confirmButton.setEnabled(false);
                        confirmButton.setVisible(true);
                        backToRecipeButton.setVisible(true);
                    }
                    selectedIngredientRow = row;
                }

                // If list view title is for adding an ingredient
                case ingredientAddListViewName -> {
                    String[] foods = getFoodNames();
                    selectedFood = foods[row];
                    confirmButton.setEnabled(true);
                }
                default -> {
                    System.out.println("List view title " + listView.title + " unknown.");
                }
            }
        }
    }

    @Override
    public int numberOfRows(ListView listView) {
        String[] recipeNames = getRecipeNames();
        switch (listView.title) {
            case recipeListViewName -> {
                return recipeNames.length + 1;
            }
            case recipeInformationListViewName -> {
                if (recipeNames.length != recipeID) {
                    return getIngredientNames(recipeID).length + 1;
                } else {
                    return 1;
                }
            }
            case ingredientAddListViewName -> {
                String[] foodNames = getFoodNames();
                return foodNames.length;
            }
            default -> {
                return 100;
            }
        }
    }

    @Override
    public String contentsOfRow(ListView listView, int row) {
        String[] recipeNames = getRecipeNames();
        switch (listView.title) {
            case recipeListViewName -> {
                if (row == recipeNames.length) {
                    return "Add a new recipe.";
                } else {
                    return recipeNames[row];
                }
            }
            case recipeInformationListViewName -> {
                if (recipeID != recipeNames.length) {
                    String[] ingredientNames = getIngredientNames(recipeID);
                    int[] ingredientQuantities = getIngredientQuantities(recipeID);
                    if (row == ingredientNames.length) {
                        return "Add a new ingredient.";
                    } else {
                        return Integer.toString(ingredientQuantities[row]) + " of " + ingredientNames[row];
                    }
                } else {
                    return "Add a new ingredient.";
                }
            }
            case ingredientAddListViewName -> {
                String[] foodNames = getFoodNames();
                return foodNames[row];
            }
            default -> {
                return "Error";
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Back" ->
                goBackToRecipeList();
            case "Delete Ingredient" -> {
                deleteIngredient(recipeID, selectedIngredientRow);
                deleteIngredientButton.setEnabled(false);
                recipeInformationListView.reloadData();
            }
            case "Delete Recipe" -> {
                deleteRecipe(recipeID);
                goBackToRecipeList();
            }
            case "Create Recipe Base" -> {
                String recipeName = recipeInformationView.getRecipeName();
                String recipeCategory = recipeInformationView.getRecipeCategory();
                String recipeInstructions = recipeInformationView.getRecipeInstructions();
                addRecipe(recipeName, recipeCategory, recipeInstructions);
                goBackToRecipeList();
            }
            case "Back To Recipe" -> {
                goBackToRecipe();
            }
            case "Confirm" -> {
                int quantity = recipeIngredientAddView.getQuantity();
                if (0 <= quantity && quantity <= 999) {
                    recipeAddIngredient(selectedFood, quantity, recipeID);
                    goBackToRecipe();
                } else {
                    JOptionPane.showMessageDialog(null, "Amount must be a between 0 and 1,000.", "InfoBox: Amount Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            default ->
                System.out.println("Action " + action + " not registered.");
        }
    }

    private void goBackToRecipe() {
        deleteIngredientButton.setEnabled(false);
        deleteIngredientButton.setVisible(true);
        confirmButton.setEnabled(false);
        confirmButton.setVisible(false);
        backToRecipeButton.setVisible(false);
        recipeIngredientAddView.setVisible(false);
        recipeIngredientAddView.setQuantity();
        recipeInformationView.setVisible(true);
        recipeInformationListView.reloadData();
    }

    private void goBackToRecipeList() {
        deleteIngredientButton.setEnabled(false);
        deleteRecipeButton.setVisible(false);
        deleteIngredientButton.setVisible(false);
        createRecipeButton.setVisible(false);
        recipeInformationView.setVisible(false);
        recipeListView.reloadData();
        recipeListView.setVisible(true);
    }
}
