# CSC 545 Term Project
This is project is the work of John Holtzworth, Matthew Abney and Jessica Haeckler.
## Setup
### Initializing the database
In order for the application to store data, you must run the Oracle SQL script called `mealPlan.sql`.  This will set up the tables with the correct schema as required by the app.
### Hooking up to the database
In order to use our Meal Planner, you must first add a file called `Secrets.java` to the mealplanner package (located at `/MealPlanner/src/mealplanner`). The file should contain this code:
```
// This project has no license.
package mealplanner;

public class Secrets {

    public static final String USERNAME = "<username>";
    public static final String PASSWORD = "<password>";
    public static final String JDBCDRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String JDBCURL = "jdbc:oracle:thin:@<ip_address>:<port>:<database>";

}
```
Simply fill in the string literals with the corresponding information in the angle brackets.
### Why?
The reason for this extra step is because  `Secrets.java` (as the name suggests) contains secrets including login information, an IP address, etc. Only students and the professor in CSC 545 in 2021 should have access to EKU's database that we used. However, these constants can also be given any values that connect to any accessible database.
