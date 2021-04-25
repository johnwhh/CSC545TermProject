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
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @date 18-04-2021
 * @author johnholtzworth
 */
public class MealPlanViewController extends JPanel implements ListViewDelegate, ListViewDataSource{

    private final MealPlanModel mealPlanModel;
    
    private ListView breakfastListView;
    private ListView lunchListView;
    private ListView dinnerListView;
    
    private HashMap<Integer, MealPlan> mealPlans;
    private Recipe[] recipes;
    private String[] dinnerRecipeNames;
    private String[] lunchRecipeNames;
    private String[] breakfastRecipeNames;
    private String[] recipeDates;

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    
    public MealPlanViewController() {
        this.mealPlanModel = new MealPlanModel();
        
        setupPanel();
    }
    
    public String[] getDates(){
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        dateList.add(dateFormat.format(date));
        String todate = dateFormat.format(date);
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
//        mealPlans = mealPlanModel.getMealPlans();
//        List<String> dateList = new ArrayList<>();
//        
//        mealPlans.forEach((quantity, mealPlan) -> {
//            dateList.add(mealPlan.getDate().toString());
//        });
//        
//        recipeDates = new String[dateList.size()];
//        dateList.toArray(recipeDates);
//        return recipeDates;
    }
    private List<String> getList(String type, String date){
        mealPlans = mealPlanModel.getMealPlans((mealPlan) -> {
            return mealPlan.getType().toString().equals(type) && mealPlan.getDate().toString().equals(date);
        });
        List<String> recipeNameList = new ArrayList<>();
        mealPlans.forEach((quantity, mealPlan) -> {
            recipeNameList.add(mealPlan.getRecipe().getName());
        });
        return recipeNameList;
    }
    
    private void updateMealPlans(String date) {
        List<String> recipeNameList = new ArrayList<>();
        
        recipeNameList = getList("BREAKFAST", date);
        breakfastRecipeNames = new String[recipeNameList.size()];
        recipeNameList.toArray(breakfastRecipeNames);
        
        recipeNameList = getList("LUNCH", date);
        lunchRecipeNames = new String[recipeNameList.size()];
        recipeNameList.toArray(lunchRecipeNames);
        
        recipeNameList = getList("DINNER", date);
        dinnerRecipeNames = new String[recipeNameList.size()];
        recipeNameList.toArray(dinnerRecipeNames);
    }
    
    private void setupPanel() {
        setBounds(0,
                0,
                MealPlanner.FRAME_WIDTH - (TabbedViewController.PADDING * 2),
                MealPlanner.FRAME_HEIGHT - (TabbedViewController.PADDING));
        setBackground(Color.gray);
        
        getDates();
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
        
        jButton1 = new javax.swing.JButton("Edit Breakfast");
        jButton2 = new javax.swing.JButton("Edit Lunch");
        jButton3 = new javax.swing.JButton("Edit Dinner");
        jButton1.setPreferredSize(new Dimension(145, 40));
        jButton2.setPreferredSize(new Dimension(145, 40));
        jButton3.setPreferredSize(new Dimension(145, 40));
        add(jButton1);
        add(jButton2);
        add(jButton3);
    }
    
    
    
    public void comboBoxSelected(String date){
        updateMealPlans(date);
        breakfastListView.reloadData();
        lunchListView.reloadData();
        dinnerListView.reloadData();
    }
    
    @Override
    public void didSelectRow(ListView listView, int row) {
        System.out.print("row selected");
    }

    @Override
    public int numberOfRows(ListView listView) {
        switch (listView.title) {
            case "Breakfast":
                return breakfastRecipeNames.length;
            case "Lunch":
                return lunchRecipeNames.length;
            case "Dinner":
                return dinnerRecipeNames.length;
            default:
                return 0;
        }
    }

    @Override
    public String contentsOfRow(ListView listView, int row) {
        switch (listView.title) {
            case "Breakfast":
                return breakfastRecipeNames[row];
            case "Lunch":
                return lunchRecipeNames[row];
            case "Dinner":
                return dinnerRecipeNames[row];
            default:
                return " ";
        }
    }
}
