package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealRepository {
    Meal save(Meal meal);

    List<Meal> getAll();

    Meal get(int id);

    Meal update(int id, Meal meal);

    void delete(int id);
}
