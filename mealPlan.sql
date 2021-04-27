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


