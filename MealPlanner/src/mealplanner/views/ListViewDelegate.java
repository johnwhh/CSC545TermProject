// This project has no license.
// Created on: 21-04-2021

package mealplanner.views;

/**
 *
 * @author johnholtzworth
 */
public interface ListViewDelegate {

    /**
     * Called when a row in the list view is selected.
     * @param listView The list view being displayed.
     * @param row The row at which a selection was just made.
     */
    public void didSelectRow(ListView listView, int row);
}
