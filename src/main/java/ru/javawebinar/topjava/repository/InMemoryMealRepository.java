package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {

    private final List<Meal> meals = new CopyOnWriteArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(-1);

    @Override
    public Meal createOrUpdate(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(getId());
            meals.add(meal);
        } else {
            Meal existingMeal = meals.stream()
                    .filter(m -> Objects.equals(m.getId(), meal.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Meal not found: " + meal.getId()));
            int index = meals.indexOf(existingMeal);
            meals.set(index, meal);
        }
        return meal;
    }

    private int getId() {
        return counter.incrementAndGet();
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals);
    }

    @Override
    public Meal get(int id) {
        return meals.stream()
                .filter(meal -> Objects.equals(meal.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Meal not found: " + id));
    }

    @Override
    public void delete(int id) {
        meals.removeIf(meal -> Objects.equals(meal.getId(), id));
    }
}
