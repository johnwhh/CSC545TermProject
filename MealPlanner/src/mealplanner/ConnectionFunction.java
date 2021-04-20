// This project has no license.
package mealplanner;

import java.sql.Connection;
import oracle.jdbc.OraclePreparedStatement;

/**
 * @date 19-04-2021
 * @author johnholtzworth
 */
public interface ConnectionFunction {

    OraclePreparedStatement use(Connection connection);
}
