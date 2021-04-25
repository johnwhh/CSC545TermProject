DROP TABLE fridgeFood;
DROP TABLE recipeFood;
DROP TABLE recipeMealPlan;

DROP TABLE recipe;
DROP TABLE mealPlan;
DROP TABLE fridge;
DROP TABLE food;

CREATE TABLE recipe (
    ID NUMBER(3) PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    instructions CHAR(256) NOT NULL,
    category NUMBER(2)
);

CREATE TABLE mealPlan (
    ID NUMBER(3) PRIMARY KEY,
    type NUMBER(2) NOT NULL,
    mealDate DATE NOT NULL
);

CREATE TABLE fridge (
    ID NUMBER(3) PRIMARY KEY
);

CREATE TABLE food (
    ID NUMBER(3) PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    foodGroup NUMBER(2),
    calories NUMBER(4) DEFAULT 0,
    sugar NUMBER(3) DEFAULT 0,
    protein NUMBER(3) DEFAULT 0,
    sodium NUMBER(3) DEFAULT 0,
    fat NUMBER(3) DEFAULT 0,
    cholesterol NUMBER(3) DEFAULT 0,
    carbs NUMBER(3) DEFAULT 0
);

CREATE TABLE recipeMealPlan (
    recipeID NUMBER(3),
    mealPlanID NUMBER(3),
    CONSTRAINT fkMRecipe FOREIGN KEY (recipeID) REFERENCES recipe(ID)
    ON DELETE SET NULL,
    CONSTRAINT fkRMealPlan FOREIGN KEY (mealPlanID) REFERENCES mealPlan(ID)
    ON DELETE SET NULL
);

CREATE TABLE fridgeFood (
    fridgeID NUMBER(3),
    foodID NUMBER(3),
    quantity NUMBER(3),
    CONSTRAINT fkFFridge FOREIGN KEY (fridgeID) REFERENCES fridge(ID)
    ON DELETE SET NULL,
    CONSTRAINT fkFFood FOREIGN KEY (foodID) REFERENCES food(ID)
    ON DELETE SET NULL
);

CREATE TABLE recipeFood (
    recipeID NUMBER(3),
    foodID NUMBER(3),
    quantity NUMBER(3),
    CONSTRAINT fkFRecipe FOREIGN KEY (recipeID) REFERENCES recipe(ID)
    ON DELETE SET NULL,
    CONSTRAINT fkRFood FOREIGN KEY (foodID) REFERENCES food(ID)
    ON DELETE SET NULL
);

INSERT INTO fridge VALUES (0);

-- Here are various statements for debugging:

--INSERT INTO fridgeFood VALUES (0, 0, 4);
--INSERT INTO fridgeFood VALUES (0, 1, 3);
--
--INSERT INTO food VALUES (0, 'Amazing Food', 0, 0, 0, 0, 0, 0, 0, 0);
--INSERT INTO food VALUES (1, 'Yummy Food', 0, 0, 0, 0, 0, 0, 0, 0);
--
--INSERT INTO mealPlan VALUES (0, 0, CURRENT_DATE);
--INSERT INTO recipe VALUES (0, 'RecipeName', '1. Open box', 0);
--INSERT INTO recipeFood VALUES (0, 0, 2);
--INSERT INTO recipeFood VALUES (0, 1, 5);
--INSERT INTO recipeMealPlan VALUES (0, 0);

--SELECT * FROM mealPlan, recipeMealPlan, recipe
--WHERE mealPlan.ID = recipeMealPlan.mealPlanID 
--AND recipeMealPlan.recipeID = recipe.ID;
--
--SELECT * FROM mealPlan, recipeMealPlan, recipe WHERE mealPlan.id = recipeMealPlan.mealPlanID AND recipeMealPlan.recipeID = recipe.id;
--
--SELECT * FROM fridgeFood, food WHERE fridgeFood.foodID = food.ID;
--
--SELECT * FROM food;
--
--DELETE FROM food WHERE ID = 999;
--
--UPDATE mealPlan SET type = 0, mealDate = CURRENT_DATE WHERE ID = 999;