// This project has no license.
package mealplanner.models;

import java.util.HashMap;
import java.util.function.Predicate;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class MealPlanModel {

    private HashMap<Integer, MealPlan> mealPlans;

    public MealPlanModel() {
        fetchMealPlans();
    }

    private void fetchMealPlans() {
        mealPlans = new HashMap<>();

        // TODO: Fetch data from database
    }

    private void updateMealPlans() {
        // TODO: Update database with data from model
    }

    public HashMap<Integer, MealPlan> getMealPlans() {
        return mealPlans;
    }

    public HashMap<Integer, MealPlan> getFoods(Predicate<MealPlan> predicate) {
        HashMap<Integer, MealPlan> dictionary = new HashMap<>();
        mealPlans.entrySet().forEach(entry -> {
            Integer id = entry.getKey();
            MealPlan mealPlan = entry.getValue();
            if (predicate.test(mealPlan)) {
                dictionary.put(id, mealPlan);
            }
        });
        return dictionary;
    }

    public void addMealPlan(MealPlan mealPlan) {
        mealPlans.put(mealPlan.getId(), mealPlan);

        updateMealPlans();
    }

    public void removeMealPlan(int id) {
        mealPlans.remove(id);

        updateMealPlans();
    }
}
