// This project has no license.
package mealplanner.views;

/**
 * @date 21-04-2021
 * @author johnholtzworth
 */
public interface ListViewDataSource {

    /**
     * Provides the list view with the number of rows in the list.
     * @param listView The list view being displayed.
     * @return The number of rows in the list view
     */
    public int numberOfRows(ListView listView);

    /**
     * Provides the list view with contents at a given row.
     * @param listView The list view being displayed.
     * @param row The row at which the list view will display contents.
     * @return The contents of the list view at the given row.
     */
    public String contentsOfRow(ListView listView, int row);
}
