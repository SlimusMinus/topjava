package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface CRUDInterface {
    void addMeal(Meal meal);

    List<MealTo> getAll();

    Meal get(int id);

    void updateMeal(int id, Meal meal);

    void deleteMeal(int id);
}
