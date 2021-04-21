// This project has no license.
package mealplanner.controllers;

import mealplanner.views.RecipeListView;
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
    
    public RecipeViewController() {
        this.recipeModel = new RecipeModel();
        this.foodModel = new FoodModel();

        setupPanel();
    }
    
    private ListView recipelistView;

    private void setupPanel() {
        setLayout(null);
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.WHITE);
        
        recipelistView = new ListView("Your Recipes");
        recipelistView.delegate = this;
        recipelistView.dataSource = this;
        recipelistView.setBounds(this.getBounds());
        add(recipelistView);
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

    @Override
    public void didSelectRow(ListView listView, int row) {
        System.out.println("Selected row " + row);
    }
    
    
    @Override
    public int numberOfRows(ListView listView) {
        String[] recipeNames = getRecipeNames();
        return recipeNames.length;
    }

    @Override
    public String contentsOfRow(ListView listView, int row) {
        String[] recipeNames = getRecipeNames();
        return recipeNames[row] + row;
    }
    
}