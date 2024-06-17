package ru.javawebinar.topjava.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;


public interface MealRepository {
    List<Meal> getAll(int userId);

    Meal get(int id, int userId);

    Meal save(Meal meal, int userId);

    boolean delete(int id, int userId);

    List<Meal> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}
