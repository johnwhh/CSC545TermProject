// This project has no license.
package mealplanner.controllers;

import mealplanner.views.RecipeListView;
import mealplanner.views.RecipeInformationView;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
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
    
    static RecipeInformationView recipeInformationView = new RecipeInformationView();
    static RecipeListView recipeListView = new RecipeListView();

    private void setupPanel() {
        setLayout(null);
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.WHITE);
        
        recipeListView.setBackground(Color.LIGHT_GRAY);
        recipeListView.setBounds(this.getBounds());
        add(recipeListView);
        recipeListView.setVisible(true);
        recipeListView.setRecipeNames(getRecipeNames());
        
        recipeInformationView.setBackground(Color.LIGHT_GRAY);
        recipeInformationView.setBounds(this.getBounds());
        add(recipeInformationView);
        
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
    
    private String getRecipeCategory(String selectedItem) {
        var recipes = recipeModel.getRecipes();
        String recipeInstructions = "";
        
        
    }
    
    public static void showSelectedItem(String selectedItem){
        recipeListView.setVisible(false);
        recipeInformationView.setVisible(true);
        
    }
}
