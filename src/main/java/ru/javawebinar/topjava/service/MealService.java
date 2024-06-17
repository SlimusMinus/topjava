
package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(mealRepository.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(mealRepository.get(id, userId), id);
    }

    public Meal create(Meal meal, int userId) {
        return mealRepository.save(meal, userId);
    }

    public Meal update(Meal meal, int userId) {
        return checkNotFoundWithId(mealRepository.save(meal, userId), meal.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(mealRepository.delete(id, userId), id);
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getTos(mealRepository.getFiltered(startDate, endDate, startTime, endTime), SecurityUtil.authUserCaloriesPerDay());
    }
}
