package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal createOrUpdate(Meal meal);

    List<Meal> getAll();

    Meal get(int id);

    void delete(int id);
}
