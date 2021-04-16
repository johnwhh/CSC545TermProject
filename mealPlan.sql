create table recipe (
 ID number(3) PRIMARY KEY,
 name CHAR(25) NOT NULL,
 instructions CHAR(100) NOT NULL,
 category char(6)
);

create table mealPlan (
 ID number(3) PRIMARY KEY,
 type CHAR(9) NOT NULL,
 date DATE NOT NULL
);

create table fridge (
 ID number(3) PRIMARY KEY
);

create table food (
 ID number(3) PRIMARY KEY,
 name CHAR(9) NOT NULL,
 group CHAR(9),
 calories number(4) DEFAULT 0,
 sugar number(3) DEFAULT 0,
 protein number(3) DEFAULT 0,
 sodium number(3) DEFAULT 0,
 fat number(3) DEFAULT 0,
 cholesterol number(3) DEFAULT 0,
 carbs number(3) DEFAULT 0
);

create table recipeMealPlan (
 recipeID number(3),
 mealPlanID number(3),
 CONSTRAINT fkMRecipe FOREIGN KEY (recipeID) REFERENCES recipe(ID)
 ON DELETE SET NULL,
 CONSTRAINT fkRMealPlan FOREIGN KEY (mealPlanID) REFERENCES mealPlan(ID)
 ON DELETE SET NULL
);

create table fridgeFood (
 fridgeID number(3),
 foodID number(3),
 CONSTRAINT fkFFridge FOREIGN KEY (fridgeID) REFERENCES fridge(ID)
 ON DELETE SET NULL,
 CONSTRAINT fkFFood FOREIGN KEY (foodID) REFERENCES food(ID)
 ON DELETE SET NULL
);

create table recipeFood (
 recipeID number(3),
 foodID number(3),
 CONSTRAINT fkFRecipe FOREIGN KEY (recipeID) REFERENCES recipe(ID)
 ON DELETE SET NULL,
 CONSTRAINT fkRFood FOREIGN KEY (foodID) REFERENCES food(ID)
 ON DELETE SET NULL
);