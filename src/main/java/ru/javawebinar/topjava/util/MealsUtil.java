package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static List<MealTo> mealInMealTo(List<Meal> meals, int caloriesPerDay) {
        return filteredByStreams(meals, meal -> true, caloriesPerDay);
    }

    public static List<MealTo> mealInMealToWithFilter(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return filteredByStreams(meals, meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime), caloriesPerDay);
    }

    private static List<MealTo> filteredByStreams(List<Meal> meals, Predicate<Meal> filter, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

}
