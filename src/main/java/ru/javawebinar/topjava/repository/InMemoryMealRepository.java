package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {

    private static final AtomicInteger id = new AtomicInteger(-1);

    public static int getId() {
        return id.incrementAndGet();
    }

    private final List<Meal> meals = new CopyOnWriteArrayList<>();

    @Override
    public Meal save(Meal meal) {
        meal.setId(getId());
        meals.add(meal);
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public Meal update(int id, Meal meal) {
        meals.set(id, meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }
}
