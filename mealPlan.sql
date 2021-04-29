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
    ON DELETE CASCADE,
    CONSTRAINT fkRMealPlan FOREIGN KEY (mealPlanID) REFERENCES mealPlan(ID)
    ON DELETE CASCADE
);

CREATE TABLE fridgeFood (
    fridgeID NUMBER(3),
    foodID NUMBER(3),
    quantity NUMBER(3),
    CONSTRAINT fkFFridge FOREIGN KEY (fridgeID) REFERENCES fridge(ID)
    ON DELETE CASCADE,
    CONSTRAINT fkFFood FOREIGN KEY (foodID) REFERENCES food(ID)
    ON DELETE CASCADE
);

CREATE TABLE recipeFood (
    recipeID NUMBER(3),
    foodID NUMBER(3),
    quantity NUMBER(3),
    CONSTRAINT fkFRecipe FOREIGN KEY (recipeID) REFERENCES recipe(ID)
    ON DELETE CASCADE,
    CONSTRAINT fkRFood FOREIGN KEY (foodID) REFERENCES food(ID)
    ON DELETE CASCADE
);

INSERT INTO food(id, name) VALUES(0, 'bread'); -- id,name, #*8
INSERT INTO food(id, name) VALUES(1, 'peanut butter');
INSERT INTO food(id, name) VALUES(2, 'jelly');
INSERT INTO food(id, name) VALUES(3, 'egg');
INSERT INTO recipe(id, name, instructions, category) VALUES (0, 'peanutbutter and jelly sandwich', '1. put peanutbutter on bread 2. put jelly on bread 3. put breads together', 1);--id name instructions category
INSERT INTO recipe(id, name, instructions, category) VALUES (1, 'egg', '1. cook egg', 0);
INSERT INTO mealPlan(id, type, mealDate) VALUES (0, 0, CURRENT_DATE);
INSERT INTO mealPlan(id, type, mealDate) VALUES (1, 1, CURRENT_DATE);
INSERT INTO recipemealplan (recipeID, mealPlanID) VALUES (0, 0);
INSERT INTO recipemealplan (recipeID, mealPlanID) VALUES (1, 1);
INSERT INTO recipeFood(recipeID, foodID, quantity) VALUES(0, 0, 1);
INSERT INTO recipeFood(recipeID, foodID, quantity) VALUES(0, 1, 1);
INSERT INTO recipeFood(recipeID, foodID, quantity) VALUES(0, 2, 1);
INSERT INTO recipeFood(recipeID, foodID, quantity) VALUES(1, 3, 1);



