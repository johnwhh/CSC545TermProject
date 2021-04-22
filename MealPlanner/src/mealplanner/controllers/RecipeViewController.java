// This project has no license.
package mealplanner.controllers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
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
public class RecipeViewController extends JPanel implements ListViewDelegate, ListViewDataSource {

    private final RecipeModel recipeModel;
    private final FoodModel foodModel;
    private final String recipeListViewName = "Your Recipes";
    private final String recipeInformationListViewName = "Ingredients Needed";
    private ListView recipeListView;
    private ListView recipeInformationListView;
    private RecipeInformationView recipeInformationView = new RecipeInformationView();
    private int recipeRowSpot = 0;
    
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
        List<String> ingredientNameList = new ArrayList<>();
        
        recipes.forEach((id, recipe) -> {
            ingredientNameList.add("FOOD NAME");
        });
        
        String[] ingredientNames = new String[ingredientNameList.size()];
        ingredientNameList.toArray(ingredientNames);
        return ingredientNames;
    }
    
    private int[] getIngredientQuantities(int rowSpot) {
        var recipes = recipeModel.getRecipes();
        List<Integer> ingredientQuantityList = new ArrayList<>();
        
        recipes.forEach((id, recipe) -> {
            ingredientQuantityList.add(4);
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
    
    @Override
    public void didSelectRow(ListView listView, int row) {
        if (listView.title.equals(recipeListViewName)){
            recipeRowSpot = row;
            recipeListView.setVisible(false);
            recipeInformationView.setVisible(true);
            String[] recipeNames = getRecipeNames();
            recipeInformationView.setRecipeData(recipeNames[row], getRecipeCategory(row).strip(), getRecipeInstructions(row).strip());
            
            recipeInformationListView = new ListView(recipeInformationListViewName);
            recipeInformationListView.setBackground(Color.LIGHT_GRAY);
            recipeInformationListView.delegate = this;
            recipeInformationListView.dataSource = this;
            recipeInformationListView.setBounds(0, 300, 460, 200);
            recipeInformationView.add(recipeInformationListView);
        }
    }
    
    @Override
    public int numberOfRows(ListView listView) {
        if (listView.title.equals(recipeListViewName)){
            return getRecipeNames().length;
        } else if (listView.title.equals(recipeInformationListViewName)) {
            return getIngredientNames(recipeRowSpot).length;
        } else {
            return 100;
        }
    }

    @Override
    public String contentsOfRow(ListView listView, int row) {
        if (listView.title.equals(recipeListViewName)){
            String[] recipeNames = getRecipeNames();
            return recipeNames[row];
        }  else if (listView.title.equals(recipeInformationListViewName)) {
            String[] ingredientNames = getIngredientNames(recipeRowSpot);
            int[] ingredientQuantities = getIngredientQuantities(recipeRowSpot);
            return Integer.toString(ingredientQuantities[row]) + " of " + ingredientNames[row];
        } else {
            return "Error";
        }
    }
}