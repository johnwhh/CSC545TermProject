// This project has no license.
package mealplanner.controllers;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import mealplanner.MealPlanner;
import mealplanner.models.*;
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
    private ListView recipeListView;
    private ListView recipeInformationListView;
    private RecipeInformationView recipeInformationView;
    private JButton deleteIngredientButton;
    private JButton createRecipeButton;
    private JButton deleteRecipeButton;
    private JButton backButton;
    private int recipeRowSpot = 0;
    private int selectedIngredientRow = 0;
    
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
        
        recipeListView = new ListView(recipeListViewName);
        recipeListView.setBackground(Color.LIGHT_GRAY);
        recipeListView.delegate = this;
        recipeListView.dataSource = this;
        recipeListView.setBounds(0, 0, 460, 615);
        add(recipeListView);
        recipeListView.setVisible(true);
        
        recipeInformationView = new RecipeInformationView();
        recipeInformationView.setBackground(Color.LIGHT_GRAY);
        recipeInformationView.setBounds(0, 0, 460, 615);
        add(recipeInformationView);
        recipeInformationView.setVisible(false);
    }
    
    private String[] getRecipeNames() {
        var recipes = recipeModel.getRecipes();
        List<String> recipeNameList = new ArrayList<>();
        
        recipes.forEach((id, recipe) -> {
            recipeNameList.add(recipe.getName());
        });
        
        String[] recipeNames = new String[recipeNameList.size()];
        recipeNameList.toArray(recipeNames);
        return recipeNames;
    }
    
    private String[] getIngredientNames(int rowSpot) {
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(rowSpot);
        var ingredients = recipe.getFoods();
        List<String> ingredientNameList = new ArrayList<>();
        
        ingredients.forEach((food, quantity) -> {
            ingredientNameList.add(food.getName());
        });
        
        String[] ingredientNames = new String[ingredientNameList.size()];
        ingredientNameList.toArray(ingredientNames);
        return ingredientNames;
    }
    
    private int[] getIngredientQuantities(int rowSpot) {
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(rowSpot);
        var ingredients = recipe.getFoods();
        List<Integer> ingredientQuantityList = new ArrayList<>();
        
        ingredients.forEach((food, quantity) -> {
            ingredientQuantityList.add(quantity);
        });
        
        int[] ingredientQuantities = new int[ingredientQuantityList.size()];
        for (int i = 0; i < ingredientQuantityList.size(); i++) {
            ingredientQuantities[i] = ingredientQuantityList.get(i);
        }
        return ingredientQuantities;
    }
    
    private String getRecipeInstructions(int recipeSpot){
        var recipes = recipeModel.getRecipes();
        String recipeInstructions = recipes.get(recipeSpot).getInstructions();
        return recipeInstructions;
    }
    
    private String getRecipeCategory(int recipeSpot){
        var recipes = recipeModel.getRecipes();
        String recipeCategory = recipes.get(recipeSpot).getCategory().name();
        recipeCategory = recipeCategory.substring(0, 1) + recipeCategory.substring(1).toLowerCase();
        return recipeCategory;
    }
    
    private void deleteIngredient(int recipeRowSpot, int ingredientRowSpot){
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(recipeRowSpot);
        var ingredients = recipe.getFoods();
        List<String> ingredientNameList = new ArrayList<>();
        
        ingredients.forEach((food, quantity) -> {
            ingredientNameList.add(food.getName());
        });
        
        String foodName = ingredientNameList.get(ingredientRowSpot);
        
        for(Iterator<Food> iter = ingredients.keySet().iterator(); iter.hasNext(); ) {
            Food food = iter.next();
            if(food.getName().equalsIgnoreCase(foodName)) {
                iter.remove();
            }
        }
        
        recipe.setFoods(ingredients);
        //recipeModel.updateRecipe(recipeRowSpot, recipe);
    }
    
    private void addRecipe(String name, String category, String instructions){
        var recipes = recipeModel.getRecipes();
        
    }
    
    private void deleteRecipe(int recipeRowSpot){
        var recipes = recipeModel.getRecipes();
        var recipe = recipes.get(recipeRowSpot);
        recipes.remove(recipe.getId());
        //recipeModel.removeRecipe(recipe.getId());
    }
    
    @Override
    public void didSelectRow(ListView listView, int row) {
        String[] recipeNames = getRecipeNames();
        if (listView.title.equals(recipeListViewName)){
            
            backButton = new JButton("Back");
            recipeInformationView.add(backButton);
            backButton.setBounds(10,570,80,40);
            backButton.addActionListener(this);
            backButton.setVisible(true);
            
            deleteRecipeButton = new JButton("Delete Recipe");
            recipeInformationView.add(deleteRecipeButton);
            deleteRecipeButton.setBounds(325,570,120,40);
            deleteRecipeButton.addActionListener(this);
            
            createRecipeButton = new JButton("Create Recipe Base");
            recipeInformationView.add(createRecipeButton);
            createRecipeButton.setBounds(160,325,150,40);
            createRecipeButton.addActionListener(this);

            if(recipeNames.length != row){
                createRecipeButton.setVisible(false);
                deleteRecipeButton.setVisible(true);
            } else {
                deleteRecipeButton.setVisible(false);
                createRecipeButton.setVisible(true);
            }
            deleteIngredientButton = new JButton("Delete Ingredient");
            recipeInformationView.add(deleteIngredientButton);
            deleteIngredientButton.setEnabled(false);
            deleteIngredientButton.setBounds(170,500,130,40);
            deleteIngredientButton.addActionListener(this);
            deleteIngredientButton.setVisible(true);
            
            recipeRowSpot = row;
            recipeListView.setVisible(false);
            recipeInformationView.setVisible(true);
            if ( recipeNames.length != row ){
                recipeInformationView.setRecipeData(recipeNames[row], getRecipeCategory(row).strip(), getRecipeInstructions(row).strip());
            } else {
                recipeInformationView.setRecipeData("Name", "Dessert", "Write Instructions.");
           }
            recipeInformationListView = new ListView(recipeInformationListViewName);
            recipeInformationListView.setBackground(Color.LIGHT_GRAY);
            recipeInformationListView.delegate = this;
            recipeInformationListView.dataSource = this;
            recipeInformationListView.setBounds(0, 300, 460, 200);
            recipeInformationView.add(recipeInformationListView);
            if (recipeNames.length != row){
                recipeInformationListView.setVisible(true);
                deleteIngredientButton.setVisible(true);
            } else {
                recipeInformationListView.setVisible(false);
                deleteIngredientButton.setVisible(false);
            }
            
        } else if (listView.title.equals(recipeInformationListViewName)){
            deleteIngredientButton.setEnabled(true);
            selectedIngredientRow = row;
        }
    }
    
    @Override
    public int numberOfRows(ListView listView) {
        String[] recipeNames = getRecipeNames();
        if (listView.title.equals(recipeListViewName)){
            return recipeNames.length+1;
        } else if (listView.title.equals(recipeInformationListViewName)) {
            if ( recipeNames.length != recipeRowSpot ){
                return getIngredientNames(recipeRowSpot).length+1;
            } else {
                return 1;
            }
        } else {
            return 100;
        }
    }

    @Override
    public String contentsOfRow(ListView listView, int row) {
        String[] recipeNames = getRecipeNames();
        if (listView.title.equals(recipeListViewName)){
            if ( row == recipeNames.length ){
                return "Add a new recipe.";
            } else {
                return recipeNames[row];
            }
        }  else if (listView.title.equals(recipeInformationListViewName)) {
            if ( recipeRowSpot != recipeNames.length ){
                String[] ingredientNames = getIngredientNames(recipeRowSpot);
                int[] ingredientQuantities = getIngredientQuantities(recipeRowSpot);
                if ( row == ingredientNames.length ){
                    return "Add a new ingredient.";
                } else {
                    return Integer.toString(ingredientQuantities[row]) + " of " + ingredientNames[row];
                }
            } else {
                return "Add a new ingredient.";
            }
        } else {
            return "Error";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Back")){
            deleteIngredientButton.setEnabled(false);
            deleteRecipeButton.setVisible(false);
            deleteIngredientButton.setVisible(false);
            createRecipeButton.setVisible(false);
            recipeInformationView.setVisible(false);
            recipeInformationListView.setVisible(false);
            recipeListView.setVisible(true);
        } else if (action.equals("Delete Ingredient")){
            deleteIngredient(recipeRowSpot, selectedIngredientRow);
            recipeInformationView.setVisible(false);
            recipeInformationView.setVisible(true);
        } else if (action.equals("Delete Recipe")){
            deleteRecipe(recipeRowSpot);
            recipeInformationView.setVisible(false);
            recipeListView.setVisible(true);
        } else if (action.equals("Create Recipe Base")){
            String recipeName = recipeInformationView.getRecipeName();
            String recipeCategory = recipeInformationView.getRecipeCategory();
            String recipeInstructions = recipeInformationView.getRecipeInstructions();
        } else {
            System.out.println("Action " + action + " not registered.");
        }
    }
}