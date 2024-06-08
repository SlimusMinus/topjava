package ru.javawebinar.topjava.DTO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealInMealTo {
    private static final int normCalories = 2000;

    public static List<MealTo> exchange(List<Meal> meals){

        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .map(meal -> new MealTo(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesSumByDate.get(meal.getDate()) > normCalories
                ))
                .collect(Collectors.toList());
    }
}
