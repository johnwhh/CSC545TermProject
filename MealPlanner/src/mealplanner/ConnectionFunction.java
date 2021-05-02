// This project has no license.
// Created on: 19-04-2021
package mealplanner;

import java.sql.Connection;
import oracle.jdbc.OraclePreparedStatement;

/**
 *
 * @author johnholtzworth
 */
public interface ConnectionFunction {

    OraclePreparedStatement use(Connection connection);
}
