// This project has no license.
package mealplanner.models;

import java.util.Objects;

/**
 * @date 27-04-2021
 * @author johnholtzworth
 */
public class FoodQuantity {

    public Food food;
    public int quantity;

    public FoodQuantity(Food food, int quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "FoodQuantity{" + "food=" + food + ", quantity=" + quantity + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FoodQuantity other = (FoodQuantity) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        return Objects.equals(this.food, other.food);
    }

}
