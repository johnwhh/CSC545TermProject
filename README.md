# CSC 545 Term Project [![tests](https://github.com/johnwhh/CSC545TermProject/actions/workflows/main.yml/badge.svg)](https://github.com/johnwhh/CSC545TermProject/actions/workflows/main.yml)
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
## Assumptions
Due to some restrictions, whether it be of the technologies or our collective knowledge, this application has a few limitations and therefore some assumptions have been made:
1. Food names and recipe names must be no longer than 50 characters.
2. Recipe instructions must be no longer than 255 characters.
3. The unit of measurements for nutritional values of foods are based on those in American nutritional facts labels. For example, the unit of measurement used for sugar is typically grams. However, in the end, the user has ability to choose what unit of measurement is most appropriate.
4. Nutritional values must be whole numbers.
5. All numbers, including quantities, must be less than 1,000.
6. Only one user is supported per database. This could be scaled at some point in the future.
## Note
The target JDK version of this application is JDK 16.
