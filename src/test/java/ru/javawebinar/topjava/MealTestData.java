package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

public class MealTestData {
    public static final Meal meal1 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal6 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal meal7 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal meal8 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 9, 0), "Омлет", 1000);
    public static final Meal meal9 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Паста карбонара", 500);
    public static final Meal meal10 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Устрицы", 410);
    public static final Meal userForSave = new Meal(LocalDateTime.of(2020, Month.MAY, 18, 12, 0), "Картошка", 500);

  static {
        meal1.setId(1);
        meal2.setId(2);
        meal3.setId(3);
        meal4.setId(4);
        meal5.setId(5);
        meal6.setId(6);
        meal7.setId(7);
        meal8.setId(8);
        meal9.setId(9);
        meal10.setId(10);
    }
}
