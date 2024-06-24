package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MealTestData {
    public static final int ID_MEAL_FROM_DB = 100003;
    public static final int INCORRECT_ID_MEAL = 25;
    public static final int SIZE_AFTER_DELETE = 6;
    public static final int SIZE_AFTER_FILTER = 3;
    public static final int SIZE_ALL_MEALS = 7;
    public static final int SIZE_AFTER_CREATE = 8;
    public static final LocalDateTime NEW_DATE_TIME = LocalDateTime.of(2022, Month.JUNE, 25, 9, 30);
    public static final String NEW_DESCRIPTION = "Роллы";
    public static final int NEW_CALORIES = 777;
    public static final int OTHER_USER = 15;
    public static final Meal meal1 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal6 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal meal7 = new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal meal8 = new Meal(100010, LocalDateTime.of(2020, Month.JANUARY, 31, 9, 0), "Омлет", 1000);
    public static final Meal meal9 = new Meal(100011, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Паста карбонара", 500);
    public static final Meal meal10 = new Meal(100012, LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Устрицы", 410);
    public static final Meal mealForSave = new Meal(LocalDateTime.of(2020, Month.MAY, 18, 12, 0), "Картошка", 500);
    public static final Meal MEAL_WITH_NOT_EXiST_ID = new Meal(INCORRECT_ID_MEAL, LocalDateTime.of(2020, Month.MAY, 18, 12, 0), "Плов", 500);

    public static final List<Meal> ALL_MEALS_USER = Stream.of(meal1, meal2, meal3, meal4, meal5, meal6, meal7)
            .sorted(Comparator.comparing(Meal::getDate).thenComparing(Meal::getTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> ALL_MEALS_AFTER_FILTERED = Stream.of(meal1, meal2, meal3)
            .sorted(Comparator.comparing(Meal::getDate).thenComparing(Meal::getTime).reversed())
            .collect(Collectors.toList());

    public static Meal getUpdated(){
        final Meal mealFromDB = MealTestData.meal1;
        mealFromDB.setDateTime(NEW_DATE_TIME);
        mealFromDB.setDescription(NEW_DESCRIPTION);
        mealFromDB.setCalories(NEW_CALORIES);
        return mealFromDB;
    }

}
