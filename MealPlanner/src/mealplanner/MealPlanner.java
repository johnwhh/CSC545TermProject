// This project has no license.
package mealplanner;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import mealplanner.controllers.TabbedViewController;

/**
 * @date 16-04-2021
 * @author johnholtzworth
 */
public class MealPlanner {

    public static final int PADDING = 20;
    public static final int FRAME_WIDTH = 500, FRAME_HEIGHT = 700;

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Meal Planner");
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        Container mainContainer = mainFrame.getContentPane();
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setLayout(null);
        
        TabbedViewController tabbedViewController = new TabbedViewController();

        mainContainer.add(tabbedViewController);
        tabbedViewController.setVisible(true);

        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
    }
}
