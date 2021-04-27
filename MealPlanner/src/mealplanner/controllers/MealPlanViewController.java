// This project has no license.
package mealplanner.controllers;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JPanel;
import mealplanner.MealPlanner;
import mealplanner.models.*;
import mealplanner.views.ListView;
import mealplanner.views.ListViewDataSource;
import mealplanner.views.ListViewDelegate;
import mealplanner.views.MealPlanListView;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import mealplanner.views.ConfirmationView;

/**
 * @date 18-04-2021
 * @author johnholtzworth
 */
public class MealPlanViewController extends JPanel implements ListViewDelegate, ListViewDataSource{

    private enum State {
        SHOWING_MEAL_PLAN,
        ADDING_RECIPE,
        REMOVING_RECIPE
    }

    private State state;
    private Recipe selectedRecipe = null;
    private MealPlan selectedMealPlan = null;
    
    private final MealPlanModel mealPlanModel;
    private final RecipeModel recipeModel;
    
    private ListView breakfastListView;
    private ListView lunchListView;
    private ListView dinnerListView;
    private ListView recipeListView;
    
    private HashMap<Integer, MealPlan> breakfastMealPlans;
    private HashMap<Integer, MealPlan> lunchMealPlans;
    private HashMap<Integer, MealPlan> dinnerMealPlans;
    private HashMap<Integer, Recipe> recipeList;
    private Set<Integer> mealPlanIds;
    private int[] recipeIds;
    private int[] breakfastRecipeIds;
    private int[] lunchRecipeIds;
    private int[] dinnerRecipeIds;
    private int breakfastMealPlanId;
    private int lunchMealPlanId;
    private int dinnerMealPlanId;
    
    private String mealType;
    private String date = null;
    private Recipe[] recipes;
    private String[] dinnerRecipeNames;
    private String[] lunchRecipeNames;
    private String[] breakfastRecipeNames;
    private String[] recipeDates;
    private String[] recipeNames;//used for add recipe view

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    
    public MealPlanViewController() {
        this.mealPlanModel = new MealPlanModel();
        this.recipeModel = new RecipeModel();
        setupPanel();
        getRecipeList();
        setState(State.SHOWING_MEAL_PLAN);
    }
    
    private void setState(State state) {
        this.state = state;
        removeAll();
        revalidate();
        repaint();

        switch (state) {
            case SHOWING_MEAL_PLAN -> {
                showMealPlanList();
            }
            case ADDING_RECIPE -> {
                showAddRecipeView();
            }
            case REMOVING_RECIPE -> {
                showRemoveRecipeView();
            }
        }
    }
    
    private void showMealPlanList(){
        MealPlanListView mealPlanListView = new MealPlanListView(recipeDates);
        mealPlanListView.setBounds(this.getBounds());
        mealPlanListView.meal = this;
        add(mealPlanListView);
        mealPlanListView.setVisible(true);
        String date = mealPlanListView.getComboSelection();
        updateMealPlans(date);
        
        breakfastListView = new ListView("Breakfast");
        breakfastListView.delegate = this;
        breakfastListView.dataSource = this;
        breakfastListView.setPreferredSize(new Dimension(145, 300));
        add(breakfastListView);
        
        lunchListView = new ListView("Lunch");
        lunchListView.delegate = this;
        lunchListView.dataSource = this;
        lunchListView.setPreferredSize(new Dimension(145, 300));
        add(lunchListView);
        
        dinnerListView = new ListView("Dinner");
        dinnerListView.delegate = this;
        dinnerListView.dataSource = this;
        dinnerListView.setPreferredSize(new Dimension(145, 300));
        add(dinnerListView);
        
        jButton1 = new javax.swing.JButton("Add Breakfast");
        jButton2 = new javax.swing.JButton("Add Lunch");
        jButton3 = new javax.swing.JButton("Add Dinner");
        jButton4 = new javax.swing.JButton("Remove Meal");
        jButton1.setPreferredSize(new Dimension(145, 40));
        jButton2.setPreferredSize(new Dimension(145, 40));
        jButton3.setPreferredSize(new Dimension(145, 40));
        jButton4.setPreferredSize(new Dimension(445, 40));
        add(jButton1);
        add(jButton2);
        add(jButton3);
        add(jButton4);
        jButton1.addActionListener((java.awt.event.ActionEvent evt) -> {
            mealType = "breakfast";
            selectedMealPlan = (MealPlan) breakfastMealPlans.get(breakfastMealPlanId);
            setState(State.ADDING_RECIPE);
        });
        jButton2.addActionListener((java.awt.event.ActionEvent evt) -> {
            mealType = "lunch";
            selectedMealPlan = (MealPlan) lunchMealPlans.get(lunchMealPlanId);
            setState(State.ADDING_RECIPE);
        });
        jButton3.addActionListener((java.awt.event.ActionEvent evt) -> {
            mealType = "dinner";
            selectedMealPlan = (MealPlan) dinnerMealPlans.get(dinnerMealPlanId);
            setState(State.ADDING_RECIPE);
        });
        jButton4.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (selectedRecipe != null) {
                setState(State.REMOVING_RECIPE);
            }
        });
    }
    
    private void showAddRecipeView(){
        getRecipeList();
        recipeListView = new ListView("Recipies");
        recipeListView.delegate = this;
        recipeListView.dataSource = this;
        recipeListView.setPreferredSize(new Dimension(350, 400));
        add(recipeListView);
        
        jButton5 = new javax.swing.JButton("Add Recipe");
        jButton5.setPreferredSize(new Dimension(350, 40));
        add(jButton5);
        jButton5.addActionListener((java.awt.event.ActionEvent evt) -> {
            if(selectedMealPlan != null)
                selectedMealPlan.getRecipes().put(selectedRecipe.getId(), selectedRecipe);
            else{
                try {
                    Date mealDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                    System.out.println("date: "+ mealDate);
                    HashMap mealRecipe = new HashMap<Integer, Recipe>();
                    mealRecipe.put(0, selectedRecipe);
                    int id = (Integer) mealPlanIds.toArray()[mealPlanIds.size()-1];
                    switch (mealType) {
                        case "breakfast":
                            selectedMealPlan = new MealPlan(id+1, MealPlan.Type.BREAKFAST, mealDate, mealRecipe);
                            break;
                        case "lunch":
                            selectedMealPlan = new MealPlan(id+1, MealPlan.Type.LUNCH, mealDate, mealRecipe);
                            break;
                        default:
                            selectedMealPlan = new MealPlan(id+1, MealPlan.Type.DINNER, mealDate, mealRecipe);
                            break;
                    }
                    System.out.println("add: "+selectedMealPlan);
                    mealPlanModel.addMealPlan(selectedMealPlan);
                } catch (ParseException ex) {
                    Logger.getLogger(MealPlanViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            setState(State.SHOWING_MEAL_PLAN);
        });
    }
    
    private void showRemoveRecipeView(){
        ConfirmationView confirmationView = new ConfirmationView("Are you sure you want to delete this recipe?", (boolean status) -> {
            if (status == true) {
                if(selectedMealPlan.getRecipes().size()==1)
                    mealPlanModel.removeMealPlan(selectedMealPlan.getId());
                else {
                    selectedMealPlan.getRecipes().remove(selectedRecipe);
                    mealPlanModel.updateMealPlan(selectedMealPlan.getId(), selectedMealPlan);
                    selectedRecipe = null;
                    updateMealPlans(date);
                    breakfastListView.reloadData();
                    lunchListView.reloadData();
                    dinnerListView.reloadData();
                }
            }

            setState(State.SHOWING_MEAL_PLAN);
        });
        confirmationView.setBounds(0, 0, getBounds().width, getBounds().height);
        confirmationView.setBackground(Color.WHITE);
        add(confirmationView);
    }
    
    private void setupPanel() {
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.gray);
        
        getDates();
                
    }
    
    private void getRecipeList(){
        if (selectedMealPlan != null){
            recipeList = recipeModel.getRecipes((recipe) ->{
                return !(selectedMealPlan.getRecipes().keySet().contains(recipe.getId()));
            });
        }else{
            recipeList = recipeModel.getRecipes();
        }
        List<String> recipeNameList = new ArrayList<>();
        recipeIds = new int[recipeList.size()];
        int i = 0;
        for (Entry<Integer, Recipe> recipes : recipeList.entrySet()) {
            Recipe recipe = (Recipe) recipes.getValue();

            recipeNameList.add(recipe.getName());
            recipeIds[i] = recipe.getId();
            i++;
        }

        recipeNames = new String[recipeNameList.size()];
        recipeNameList.toArray(recipeNames);
    }
    
    public String[] getDates(){
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        dateList.add(dateFormat.format(date));
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i<6; i++){
            cal.add(Calendar.DATE, 1);
            Date todate1 = cal.getTime();  
            String fromdate = dateFormat.format(todate1);
            dateList.add(fromdate);
        }
        recipeDates = new String[dateList.size()];
        dateList.toArray(recipeDates);
        return recipeDates;
    }
    
    private void updateMealPlans(String date){
        this.date = date;
        mealPlanIds = mealPlanModel.getMealPlans().keySet();
        System.out.println(mealPlanIds);
        List<String> recipeNameList = new ArrayList<>();
        breakfastMealPlans = mealPlanModel.getMealPlans((mealPlan) -> {
            return mealPlan.getType().toString().equals(MealPlan.Type.BREAKFAST.toString()) && mealPlan.getDate().toString().equals(date);
        });
        if(!breakfastMealPlans.isEmpty()){
            breakfastMealPlanId = breakfastMealPlans.keySet().iterator().next();
            MealPlan mealPlan = (MealPlan) new ArrayList( breakfastMealPlans.values()).get(0);
            int i = 0;
            breakfastRecipeIds = new int[mealPlan.getRecipes().size()];
            for (Entry<Integer, Recipe> recipe : mealPlan.getRecipes().entrySet()) {
                breakfastRecipeIds[i] = recipe.getKey();
                recipeNameList.add(recipe.getValue().getName());
                i++;
            }
        }else{
            recipeNameList.clear();
        }
        breakfastRecipeNames = new String[recipeNameList.size()];
        recipeNameList.toArray(breakfastRecipeNames);
        //get lunch recipes and ids
        recipeNameList = new ArrayList<>();
        lunchMealPlans = mealPlanModel.getMealPlans((mealPlan) -> {
            return mealPlan.getType().toString().equals(MealPlan.Type.LUNCH.toString()) && mealPlan.getDate().toString().equals(date);
        });
        if(!lunchMealPlans.isEmpty()){
            lunchMealPlanId = lunchMealPlans.keySet().iterator().next();
            MealPlan mealPlan = (MealPlan) new ArrayList( lunchMealPlans.values()).get(0);
            int i = 0;
            lunchRecipeIds = new int[mealPlan.getRecipes().size()];
            for (Entry<Integer, Recipe> recipe : mealPlan.getRecipes().entrySet()) {
                lunchRecipeIds[i] = recipe.getKey();
                recipeNameList.add(recipe.getValue().getName());
                i++;
            }
        }else{
            recipeNameList.clear();
        }
        lunchRecipeNames = new String[recipeNameList.size()];
        recipeNameList.toArray(lunchRecipeNames);
        
        //get dinner recipes and ids
        recipeNameList = new ArrayList<>();
        dinnerMealPlans = mealPlanModel.getMealPlans((mealPlan) -> {
            return mealPlan.getType().toString().equals(MealPlan.Type.DINNER.toString()) && mealPlan.getDate().toString().equals(date);
        });
        if(!dinnerMealPlans.isEmpty()){
            dinnerMealPlanId = dinnerMealPlans.keySet().iterator().next();
            MealPlan mealPlan = (MealPlan) new ArrayList( dinnerMealPlans.values()).get(0);
            int i = 0;
            dinnerRecipeIds = new int[mealPlan.getRecipes().size()];
            for (Entry<Integer, Recipe> recipe : mealPlan.getRecipes().entrySet()) {
                dinnerRecipeIds[i] = recipe.getKey();
                recipeNameList.add(recipe.getValue().getName());
                i++;
            }
        }else{
            recipeNameList.clear();
        }
        dinnerRecipeNames = new String[recipeNameList.size()];
        recipeNameList.toArray(dinnerRecipeNames);
    }
    
    public void comboBoxSelected(String date){
        updateMealPlans(date);
        breakfastListView.reloadData();
        lunchListView.reloadData();
        dinnerListView.reloadData();
    }
    
    @Override
    public void didSelectRow(ListView listView, int row) {
        if (row >= 0) {
            Recipe recipe;
            switch (listView.title) {
                case "Breakfast" -> {
                    recipe = (Recipe) recipeList.get(breakfastRecipeIds[row]);
                    selectedMealPlan = (MealPlan) breakfastMealPlans.get(breakfastMealPlanId);
                    System.out.println(selectedMealPlan);
                }
                case "Lunch" -> {
                    recipe = (Recipe) recipeList.get(lunchRecipeIds[row]);
                    selectedMealPlan = (MealPlan) lunchMealPlans.get(lunchMealPlanId);
                }
                case "Dinner" -> {
                    recipe = (Recipe) recipeList.get(dinnerRecipeIds[row]);
                    selectedMealPlan = (MealPlan) dinnerMealPlans.get(dinnerMealPlanId);
                }
                default ->
                    recipe = (Recipe) recipeList.get(recipeIds[row]);
            }
            selectedRecipe = recipe;
        }
    }

    @Override
    public int numberOfRows(ListView listView) {
        return switch (listView.title) {
            case "Breakfast" -> breakfastRecipeNames.length;
            case "Lunch" -> lunchRecipeNames.length;
            case "Dinner" -> dinnerRecipeNames.length;
            default -> recipeNames.length;
        };
    }

    @Override
    public String contentsOfRow(ListView listView, int row) {
        return switch (listView.title) {
            case "Breakfast" -> breakfastRecipeNames[row];
            case "Lunch" -> lunchRecipeNames[row];
            case "Dinner" -> dinnerRecipeNames[row];
            default -> recipeNames[row];
        };
    }
}
