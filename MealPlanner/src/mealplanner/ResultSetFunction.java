// This project has no license.
package mealplanner;

import oracle.jdbc.OracleResultSet;

/**
 *
 * @author johnholtzworth
 */
public interface ResultSetFunction {

    void use(OracleResultSet resultSet);
}
