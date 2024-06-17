
package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return (meal != null && meal.getUserId() == userId) ? meal : null;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            int id = counter.incrementAndGet();
            meal.setId(id);
            repository.put(id, meal);
            return repository.computeIfPresent(meal.getId(), (idMeal, oldUser) -> meal);
        } else {
            int id = meal.getId();
            Meal existingMeal = repository.get(id);
            if (existingMeal == null || existingMeal.getUserId() != userId) {
                return null;
            }
            meal.setId(id);
            repository.put(id, meal);
            return meal;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.remove(id) != null;
    }

    @Override
    public List<Meal> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getFilteredTos(startDate, endDate, startTime, endTime);
    }

}
