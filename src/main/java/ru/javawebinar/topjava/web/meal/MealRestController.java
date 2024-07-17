package ru.javawebinar.topjava.web.meal;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController extends AbstractMealController {
    public Meal get(int id) {
        return getMeal(id);
    }

    public void delete(int id) {
        deleteMeal(id);
    }

    public List<MealTo> getAll() {
        return getAllMeal();
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        return createMeal(meal);
    }

    public void update(Meal meal, int id) {
        updateMeal(meal, id);
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        return getMealsBetween(startDate, startTime, endDate, endTime);
    }
}