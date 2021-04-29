// This project has no license.
package mealplanner.controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import mealplanner.DatabaseManager;
import mealplanner.MealPlanner;
import mealplanner.ModelUpdater;
import mealplanner.models.*;
import mealplanner.views.RecipeIngredientAddView;
import mealplanner.views.ListView;
import mealplanner.views.ListViewDataSource;
import mealplanner.views.ListViewDelegate;
import mealplanner.views.RecipeInformationView;
import mealplanner.views.ConfirmationView;

/**
 * @date 18-04-2021
 * @author Matthew, johnholtzworth
 */
public class RecipeViewController extends JPanel implements ListViewDelegate, ListViewDataSource, ActionListener, ModelUpdater {

    enum SearchFilter {
        INGREDIENT,
        CATEGORY;

        @Override
        public String toString() {
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }

    public RecipeModel recipeModel;
    private FoodModel foodModel;
    private final String recipeListViewName = "Your Recipes";
    private final String recipeInformationListViewName = "Ingredients Needed";
    private final String ingredientAddListViewName = "Ingredient Options";
    private ListView recipeListView;
    private ListView recipeInformationListView;
    private ListView ingredientAddListView;
    private ConfirmationView confirmationView;
    private RecipeInformationView recipeInformationView;
    private RecipeIngredientAddView recipeIngredientAddView;
    private JButton deleteIngredientButton;
    private JButton createRecipeButton;
    private JButton deleteRecipeButton;
    private JButton backButton;
    private JButton cancelButton;
    private JButton backToRecipeButton;
    private JButton confirmButton;
    private JComboBox searchFilterComboBox;
    private JTextField searchTextField;
    private int recipeID = 0;
    private int selectedIngredientRow = 0;
    private String selectedFood = "";
    private int[] recipeIds;

    private String searchTerm = "";
    private SearchFilter searchFilter = SearchFilter.INGREDIENT;

    public RecipeViewController() {
        this.recipeModel = new RecipeModel();
        this.foodModel = new FoodModel();

        setupPanel();
    }

    @Override
    public void updateModels() {
        recipeModel = new RecipeModel();
        foodModel = new FoodModel();
        recipeListView.reloadData();
        recipeInformationListView.reloadData();
        ingredientAddListView.reloadData();
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
        recipeInformationView.setBounds(0, 0, 440, 600);
        add(recipeInformationView);
        recipeInformationView.setVisible(false);

        // Sets up the view to adding ingredients to recipe
        recipeIngredientAddView = new RecipeIngredientAddView();
        recipeIngredientAddView.setBounds(0, 0, 440, 600);
        add(recipeIngredientAddView);
        recipeIngredientAddView.setVisible(false);

        // Sets up the list views
        createRecipeListView();
        createRecipeInformationListView();
        createAddIngredientListView();
    }

    // Gets an array of strings that has recipe names
    private String[] getRecipeNames() {
        HashMap<Integer, Recipe> recipes;
        if (searchTerm.equals("") == false) {
            recipes = recipeModel.getRecipes((recipe) -> {
                if (searchFilter == SearchFilter.CATEGORY) {
                    return recipe.getCategory().toString().toLowerCase().startsWith(searchTerm.toLowerCase());
                }

                for (FoodQuantity foodQuantity : recipe.getFoods().values()) {
                    if (foodQuantity.food.getName().toLowerCase().startsWith(searchTerm.toLowerCase())) {
                        return true;
                    }
                }

                return false;
            });
        } else {
            recipes = recipeModel.getRecipes();
        }

        List<String> recipeNameList = new ArrayList<>();
        recipeIds = new int[recipes.size()];
        int i = 0;

        // Gets each name in the recipes HashMap into a list
        for (Entry<Integer, Recipe> recipe : recipes.entrySet()) {
            recipeNameList.add(recipe.getValue().getName());
            recipeIds[i] = recipe.getKey();
            i++;
        }

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
    private String[] getIngredientNames(int recipeId) {
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(recipeId);
        if (recipe == null) {
            return new String[0];
        }

        var ingredients = recipe.getFoods();
        List<String> ingredientNameList = new ArrayList<>();

        // Gets each name in the ingredients HashMap into a list
        ingredients.forEach((id, foodQuantity) -> {
            ingredientNameList.add(foodQuantity.food.getName());
        });

        // Converts the list into an array and returns
        String[] ingredientNames = new String[ingredientNameList.size()];
        ingredientNameList.toArray(ingredientNames);
        return ingredientNames;
    }

    // Gets an array of ints that has ingredient quantities
    private int[] getIngredientQuantities(int recipeId) {
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(recipeId);
        if (recipe == null) {
            return new int[0];
        }

        var ingredients = recipe.getFoods();
        List<Integer> ingredientQuantityList = new ArrayList<>();

        // Gets each quantity in the ingredients HashMap into a list
        ingredients.forEach((id, foodQuantity) -> {
            ingredientQuantityList.add(foodQuantity.quantity);
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
        HashMap<Integer, FoodQuantity> newIngredients = new HashMap<>();
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
        for (Entry<Integer, FoodQuantity> foodQuantity : recipe.getFoods().entrySet()) {
            newIngredients.put(foodQuantity.getKey(), foodQuantity.getValue());
        }

        newIngredients.put(newFood.getId(), new FoodQuantity(newFood, quantity));
        // Updates the recipe
        Recipe newRecipe = new Recipe(recipe.getId(), recipe.getName(), recipe.getInstructions(), recipe.getCategory(), newIngredients);
        recipeModel.updateRecipe(recipeID, newRecipe);
    }

    // Deletes an ingredient in the recipe
    private void deleteIngredient(int recipeID, int ingredientRowSpot) {
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(recipeID);
        HashMap<Integer, FoodQuantity> ingredients = recipe.getFoods();
        HashMap<Integer, FoodQuantity> newIngredients = new HashMap<>();
        List<String> ingredientNameList = new ArrayList<>();

        // Gets all the ingredient names into a list
        ingredients.forEach((id, foodQuantity) -> {
            ingredientNameList.add(foodQuantity.food.getName());
        });

        // Gets the name of the ingredient
        String foodName = ingredientNameList.get(ingredientRowSpot);

        // Removes the food with matching name from ingredients
        for (Entry<Integer, FoodQuantity> foodQuantity : recipe.getFoods().entrySet()) {
            if (!foodName.equals(foodQuantity.getValue().food.getName())) {
                newIngredients.put(foodQuantity.getKey(), foodQuantity.getValue());
            }
        }

        // Updates the recipe
        Recipe newRecipe = new Recipe(recipe.getId(), recipe.getName(), recipe.getInstructions(), recipe.getCategory(), newIngredients);
        recipeModel.updateRecipe(recipeID, newRecipe);
    }

    // Adds a recipe to the system
    private void addRecipe(String name, Recipe.Category category, String instructions) {
        Recipe recipe;
        HashMap<Integer, FoodQuantity> foods = new HashMap();
        int newRecipeID = DatabaseManager.getAvailableId(Recipe.class, null);

        // Creates a new recipe based off of the category
        recipe = new Recipe(newRecipeID, name, instructions, category, foods);
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
        searchTextField = new JTextField();
        searchTextField.setBounds(0, 0, 300, 30);
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void update() {
                searchTerm = searchTextField.getText();
                recipeListView.reloadData();
            }
        });
        add(searchTextField);
        searchTextField.setVisible(true);

        searchFilterComboBox = new JComboBox();
        searchFilterComboBox.setModel(new DefaultComboBoxModel<>(SearchFilter.values()));
        searchFilterComboBox.addActionListener((e) -> {
            searchFilter = (SearchFilter) searchFilterComboBox.getSelectedItem();
            recipeListView.reloadData();
        });
        searchFilterComboBox.setBounds(305, 0, 135, 30);
        add(searchFilterComboBox);
        searchFilterComboBox.setVisible(true);

        recipeListView = new ListView(recipeListViewName);
        recipeListView.delegate = this;
        recipeListView.dataSource = this;
        recipeListView.setBounds(0, 50, 440, 550);
        add(recipeListView);
        recipeListView.setVisible(true);
    }

    // Creates the recipe information list view to display
    private void createRecipeInformationListView() {
        recipeInformationListView = new ListView(recipeInformationListViewName);
        recipeInformationListView.setBackground(Color.WHITE);
        recipeInformationListView.delegate = this;
        recipeInformationListView.dataSource = this;
        recipeInformationListView.setBounds(0, 320, 440, 200);
        recipeInformationView.add(recipeInformationListView);

        backButton = new JButton("Save and Return");
        recipeInformationView.add(backButton);
        backButton.setBounds(10, 550, 130, 40);
        backButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        recipeInformationView.add(cancelButton);
        cancelButton.setBounds(10, 550, 130, 40);
        cancelButton.addActionListener(this);

        deleteRecipeButton = new JButton("Delete Recipe");
        recipeInformationView.add(deleteRecipeButton);
        deleteRecipeButton.setBounds(315, 550, 120, 40);
        deleteRecipeButton.addActionListener(this);

        createRecipeButton = new JButton("Create Recipe Base");
        recipeInformationView.add(createRecipeButton);
        createRecipeButton.setBounds(160, 325, 150, 40);
        createRecipeButton.addActionListener(this);

        deleteIngredientButton = new JButton("Delete Ingredient");
        recipeInformationView.add(deleteIngredientButton);
        deleteIngredientButton.setEnabled(false);
        deleteIngredientButton.setBounds((440 / 2) - 65, 550, 130, 40);
        deleteIngredientButton.addActionListener(this);
    }

    // Creates the recipe information list view to display
    private void createAddIngredientListView() {
        ingredientAddListView = new ListView(ingredientAddListViewName);
        ingredientAddListView.delegate = this;
        ingredientAddListView.dataSource = this;
        ingredientAddListView.setBounds(0, 0, 440, 200);
        recipeIngredientAddView.add(ingredientAddListView);

        confirmButton = new JButton("Confirm");
        recipeIngredientAddView.add(confirmButton);
        confirmButton.setBounds(160, 325, 130, 40);
        confirmButton.addActionListener(this);

        backToRecipeButton = new JButton("Back To Recipe");
        recipeIngredientAddView.add(backToRecipeButton);
        backToRecipeButton.setBounds(10, 550, 130, 40);
        backToRecipeButton.addActionListener(this);
    }

    @Override
    public void didSelectRow(ListView listView, int row) {
        if (row >= 0) {
            String[] recipeNames = getRecipeNames();
            switch (listView.title) {

                // For the main recipe list view
                case recipeListViewName -> {
                    searchTextField.setVisible(false);
                    searchFilterComboBox.setVisible(false);
                    backButton.setVisible(false);
                    cancelButton.setVisible(false);

                    // If the length == row then it is the add new recipe
                    if (recipeNames.length != row) {
                        createRecipeButton.setVisible(false);
                        deleteRecipeButton.setVisible(true);
                        backButton.setVisible(true);
                    } else {
                        deleteRecipeButton.setVisible(false);
                        createRecipeButton.setVisible(true);
                        cancelButton.setVisible(true);
                    }

                    // Tries to get the recipe ID and if it fails then it is add a new recipe
                    try {
                        recipeID = recipeIds[row];
                    } catch (Exception e) {
                        // Now we know this is a new recipe so get an available id
                        recipeID = DatabaseManager.getAvailableId(Recipe.class, null);
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

                    recipeInformationListView.reloadData();
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
                return getIngredientNames(recipeID).length + 1;
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
                String[] ingredientNames = getIngredientNames(recipeID);
                int[] ingredientQuantities = getIngredientQuantities(recipeID);

                if (ingredientNames.length == 0 || ingredientQuantities.length == 0) {
                    return "Add a new ingredient.";
                }

                if (row == ingredientNames.length) {
                    return "Add a new ingredient.";
                } else {
                    return Integer.toString(ingredientQuantities[row]) + " of " + ingredientNames[row];
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
            case "Save and Return" -> {
                Recipe updatedRecipe = new Recipe(recipeID, recipeInformationView.getRecipeName(), recipeInformationView.getRecipeInstructions(), Recipe.Category.values()[recipeInformationView.getRecipeCategory()], recipeModel.getRecipes().get(recipeID).getFoods());
                recipeModel.updateRecipe(recipeID, updatedRecipe);
                goBackToRecipeList(true);
            }
            case "Cancel" -> {
                searchTextField.setVisible(true);
                searchFilterComboBox.setVisible(true);
                backButton.setVisible(false);
                cancelButton.setVisible(false);
                deleteIngredientButton.setEnabled(false);
                deleteRecipeButton.setVisible(false);
                deleteIngredientButton.setVisible(false);
                createRecipeButton.setVisible(false);
                recipeInformationView.setVisible(false);
                recipeListView.reloadData();
                recipeListView.setVisible(true);
            }
            case "Delete Ingredient" -> {
                deleteIngredient(recipeID, selectedIngredientRow);
                deleteIngredientButton.setEnabled(false);
                recipeInformationListView.reloadData();
            }
            case "Delete Recipe" -> {
                showRemoveRecipeConfirmation(recipeID);
            }
            case "Create Recipe Base" -> {
                String recipeName = recipeInformationView.getRecipeName();

                int recipeCategoryIndex = recipeInformationView.getRecipeCategory();
                Recipe.Category recipeCategory = Recipe.Category.values()[recipeCategoryIndex];
                String recipeInstructions = recipeInformationView.getRecipeInstructions();

                if (recipeName.length() > 50) {
                    JOptionPane.showMessageDialog(null, "Recipe name must be no longer than 50 characters.", "Invalid Recipe Name Length", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (recipeInstructions.length() > 255) {
                    JOptionPane.showMessageDialog(null, "Instructions must be no longer than 255 characters.", "Invalid Instructions Length", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                addRecipe(recipeName, recipeCategory, recipeInstructions);
                goBackToRecipeList(true);
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
                    JOptionPane.showMessageDialog(null, "Quantity must be a between 0 and 1,000.", "Invalid Quantity", JOptionPane.INFORMATION_MESSAGE);
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

    private void goBackToRecipeList(boolean reloadData) {
        backButton.setVisible(false);
        cancelButton.setVisible(false);
        deleteIngredientButton.setEnabled(false);
        deleteRecipeButton.setVisible(false);
        deleteIngredientButton.setVisible(false);
        createRecipeButton.setVisible(false);
        recipeInformationView.setVisible(false);
        recipeListView.reloadData();
        recipeListView.setVisible(true);
        searchFilterComboBox.setVisible(true);
        searchTextField.setVisible(true);
        if (reloadData) {
            recipeInformationListView.reloadData();
        } else {
            confirmationView.setVisible(false);
        }
    }

    private void showRemoveRecipeConfirmation(int recipeID) {
        confirmationView = new ConfirmationView("Are you sure you want to permanently delete this recipe?", (status) -> {
            if (status == true) {
                deleteRecipe(recipeID);
            }
            goBackToRecipeList(false);
        });
        confirmationView.setBounds(0, 0, getBounds().width, getBounds().height);
        confirmationView.setBackground(Color.WHITE);
        add(confirmationView);
        confirmationView.setVisible(true);
        recipeInformationView.setVisible(false);
    }
}
