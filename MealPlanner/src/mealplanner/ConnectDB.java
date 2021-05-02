// This project has no license.
// Created on: 18-04-2021
package mealplanner;

/**
 *
 * @author Matthew
 */
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class ConnectDB {

    public static Connection setupConnection() {
        /*
         Specify the database you would like to connect with:
         - protocol
         - vendor
         - driver
         - server ip
         - port number
         - database instance name
         */

        String jdbcDriver = Secrets.JDBCDRIVER;
        String jdbcUrl = Secrets.JDBCURL;  // URL for the database

        /*
         Specify the user account you will use to connect with the database:
         - user name (e.g., myName)
         - password (e.g., myPassword)
         */
        String username = Secrets.USERNAME;
        String password = Secrets.PASSWORD;

        try {
            // Load jdbc driver.            
            Class.forName(jdbcDriver);

            // Connect to the Oracle database
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }

    static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(OraclePreparedStatement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Throwable whatever) {
            }
        }
    }

    static void close(OracleResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable whatever) {
            }
        }
    }
}
