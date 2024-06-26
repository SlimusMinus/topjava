package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int ID_MEAL_1 = START_SEQ + 3;
    public static final int NOT_FOUND = 25;
    public static final int SIZE_AFTER_DELETE = 6;
    public static final LocalDateTime NEW_DATE_TIME = LocalDateTime.of(2022, Month.JUNE, 25, 9, 30);
    public static final String NEW_DESCRIPTION = "Роллы";
    public static final int NEW_CALORIES = 777;

    public static final Meal userMeal1 = new Meal(ID_MEAL_1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(ID_MEAL_1+1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(ID_MEAL_1+2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(ID_MEAL_1+3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(ID_MEAL_1+4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(ID_MEAL_1+5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(ID_MEAL_1+6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static final Meal adminMeal1 = new Meal(ID_MEAL_1+7, LocalDateTime.of(2020, Month.JANUARY, 31, 9, 0), "Омлет", 1000);
    public static final Meal adminMeal2 = new Meal(ID_MEAL_1+8, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Паста карбонара", 500);
    public static final Meal adminMeal3 = new Meal(ID_MEAL_1+9, LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Устрицы", 410);

    public static final List<Meal> allUserMeals = Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    public static final List<Meal> filteredUserMeals = Arrays.asList(userMeal3, userMeal2, userMeal1);

    public static Meal getUpdated() {
        return new Meal(ID_MEAL_1, NEW_DATE_TIME, NEW_DESCRIPTION, NEW_CALORIES);
    }

    public static Meal getUpdatedMealAmin(){
        return new Meal(ID_MEAL_1+7, adminMeal1.getDateTime(), adminMeal1.getDescription(), adminMeal1.getCalories());
    }

    public static Meal getCreated() {
        return new Meal(LocalDateTime.of(2020, Month.MAY, 18, 12, 0), "Картошка", 500);
    }

    public static Meal getMealWithNotExistId() {
        return new Meal(NOT_FOUND, LocalDateTime.of(2020, Month.MAY, 18, 12, 0), "Плов", 500);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(List<Meal> actual, List<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(expected);
    }

}
