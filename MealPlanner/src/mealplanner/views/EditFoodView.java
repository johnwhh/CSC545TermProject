// This project has no license.
package mealplanner.views;

import javax.swing.DefaultComboBoxModel;
import mealplanner.FoodFunction;
import mealplanner.models.Food;

/**
 * @date 21-04-2021
 * @author johnholtzworth
 */
public class EditFoodView extends javax.swing.JPanel {

    public final String title;
    public final int newFoodId;
    public final Food existingFood;
    public final FoodFunction foodFunction;

    /**
     * Creates new form AddFoodView
     *
     * @param title The title of the list view.
     * @param newFoodId The new id for the food that will be added.
     * @param existingFood The food being edited, if it exists.
     * @param foodFunction The handler for the generated food.
     */
    public EditFoodView(String title, int newFoodId, Food existingFood, FoodFunction foodFunction) {
        this.title = title;
        this.newFoodId = newFoodId;
        this.existingFood = existingFood;
        this.foodFunction = foodFunction;
        
        initComponents();
        
        if (existingFood != null) {
            useExistingFood(existingFood);
        }
    }

    private void useExistingFood(Food food) {
        foodNameTextField.setText(food.getName());
        foodGroupComboBox.setSelectedIndex(food.getGroup().ordinal());
        foodCaloriesTextField.setText(Integer.toString(food.getCalories()));
        foodSugarTextField.setText(Integer.toString(food.getSugar()));
        foodProteinTextField.setText(Integer.toString(food.getProtein()));
        foodSodiumTextField.setText(Integer.toString(food.getSodium()));
        foodFatTextField.setText(Integer.toString(food.getFat()));
        foodCholesterolTextField.setText(Integer.toString(food.getCholesterol()));
        foodCarbsTextField.setText(Integer.toString(food.getCarbs()));
    }

    private Food generateFood() {
        String name = foodNameTextField.getText().equals("") ? "No Name" : foodNameTextField.getText();
        Food.Group group = (Food.Group) foodGroupComboBox.getSelectedItem();

        try {
            int calories = Integer.parseInt(foodCaloriesTextField.getText());
            int sugar = Integer.parseInt(foodSugarTextField.getText());
            int protein = Integer.parseInt(foodProteinTextField.getText());
            int sodium = Integer.parseInt(foodSodiumTextField.getText());
            int fat = Integer.parseInt(foodFatTextField.getText());
            int cholesterol = Integer.parseInt(foodCholesterolTextField.getText());
            int carbs = Integer.parseInt(foodCarbsTextField.getText());
            return new Food(newFoodId, name, group, calories, sugar, protein, sodium, fat, cholesterol, carbs);
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        acceptButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        foodNameTextField = new javax.swing.JTextField();
        foodNameLabel = new javax.swing.JLabel();
        foodGroupLabel = new javax.swing.JLabel();
        foodGroupComboBox = new javax.swing.JComboBox<>();
        foodCaloriesLabel = new javax.swing.JLabel();
        foodCaloriesTextField = new javax.swing.JTextField();
        foodSugarLabel = new javax.swing.JLabel();
        foodSugarTextField = new javax.swing.JTextField();
        foodProteinLabel = new javax.swing.JLabel();
        foodProteinTextField = new javax.swing.JTextField();
        foodSodiumLabel = new javax.swing.JLabel();
        foodSodiumTextField = new javax.swing.JTextField();
        foodFatLabel = new javax.swing.JLabel();
        foodFatTextField = new javax.swing.JTextField();
        foodCholesterolLabel = new javax.swing.JLabel();
        foodCholesterolTextField = new javax.swing.JTextField();
        foodCarbsLabel = new javax.swing.JLabel();
        foodCarbsTextField = new javax.swing.JTextField();

        setBounds(new java.awt.Rectangle(0, 0, 440, 300));
        setMaximumSize(new java.awt.Dimension(460, 400));
        setMinimumSize(new java.awt.Dimension(460, 400));
        setPreferredSize(new java.awt.Dimension(460, 400));

        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText(title);
        titleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        acceptButton.setText("Accept");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        foodNameLabel.setText("Name:");

        foodGroupLabel.setText("Group:");

        foodGroupComboBox.setModel(new DefaultComboBoxModel<Food.Group>(Food.Group.values()));

        foodCaloriesLabel.setText("Calories:");

        foodSugarLabel.setText("Sugar:");

        foodProteinLabel.setText("Protein:");

        foodSodiumLabel.setText("Sodium:");

        foodFatLabel.setText("Fat:");

        foodCholesterolLabel.setText("Cholesterol:");

        foodCarbsLabel.setText("Carbs:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(123, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(acceptButton)
                                .addGap(29, 29, 29)
                                .addComponent(cancelButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(foodNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(foodNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(foodGroupLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(foodGroupComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(foodCaloriesLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(foodCaloriesTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(foodSugarLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(foodSugarTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(foodProteinLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(foodProteinTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(foodSodiumLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(foodSodiumTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(foodFatLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(foodFatTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(foodCholesterolLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(foodCholesterolTextField))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(foodCarbsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(foodCarbsTextField)))
                        .addGap(129, 129, 129)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(foodNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodGroupLabel)
                    .addComponent(foodGroupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodCaloriesLabel)
                    .addComponent(foodCaloriesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodSugarLabel)
                    .addComponent(foodSugarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodProteinLabel)
                    .addComponent(foodProteinTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodSodiumLabel)
                    .addComponent(foodSodiumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodFatLabel)
                    .addComponent(foodFatTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodCholesterolLabel)
                    .addComponent(foodCholesterolTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodCarbsLabel)
                    .addComponent(foodCarbsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acceptButton)
                    .addComponent(cancelButton))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        foodFunction.use(generateFood());
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        foodFunction.use(null);
    }//GEN-LAST:event_cancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel foodCaloriesLabel;
    private javax.swing.JTextField foodCaloriesTextField;
    private javax.swing.JLabel foodCarbsLabel;
    private javax.swing.JTextField foodCarbsTextField;
    private javax.swing.JLabel foodCholesterolLabel;
    private javax.swing.JTextField foodCholesterolTextField;
    private javax.swing.JLabel foodFatLabel;
    private javax.swing.JTextField foodFatTextField;
    private javax.swing.JComboBox<Food.Group> foodGroupComboBox;
    private javax.swing.JLabel foodGroupLabel;
    private javax.swing.JLabel foodNameLabel;
    private javax.swing.JTextField foodNameTextField;
    private javax.swing.JLabel foodProteinLabel;
    private javax.swing.JTextField foodProteinTextField;
    private javax.swing.JLabel foodSodiumLabel;
    private javax.swing.JTextField foodSodiumTextField;
    private javax.swing.JLabel foodSugarLabel;
    private javax.swing.JTextField foodSugarTextField;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
