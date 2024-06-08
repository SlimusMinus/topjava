package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.DTO.MealInMealTo;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.ArrayList;
import java.util.List;

public class CRUDInterfaceImpl implements CRUDInterface {
    private final List<Meal> meals = new ArrayList<>(Meal.getMeals());

    @Override
    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    @Override
    public List<MealTo> getAll() {
        return MealInMealTo.exchange(meals);
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public void updateMeal(int id, Meal meal) {
        meals.set(id, meal);
    }

    @Override
    public void deleteMeal(int id) {
        meals.remove(id);
    }
}
