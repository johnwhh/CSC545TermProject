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
    
    private ListView listView;
    private RecipeListView recipeListView = new RecipeListView();
    private RecipeInformationView recipeInformationView = new RecipeInformationView();

    private void setupPanel() {
        setLayout(null);
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.WHITE);
        
        listView = new ListView("Your Recipes");
        listView.delegate = this;
        listView.dataSource = this;
        add(listView);
        
//        recipeListView.setBackground(Color.LIGHT_GRAY);
//        recipeListView.setBounds(this.getBounds());
//        add(recipeListView);
//        recipeListView.setVisible(true);
//        recipeListView.setRecipeNames(getRecipeNames());
//        
//        recipeInformationView.setBackground(Color.LIGHT_GRAY);
//        recipeInformationView.setBounds(this.getBounds());
//        add(recipeInformationView);
//        recipeInformationView.setVisible(false);
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
        return 3;
    }

    @Override
    public String contentsOfRow(ListView listView, int row) {
        return "Popcorn " + row;
    }
    
}