package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServlet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class AbstractMealController extends HttpServlet {

    protected static final Logger log = LoggerFactory.getLogger(AbstractMealController.class);

    @Autowired
    protected MealService service;

    protected static int getUserId() {
        return SecurityUtil.authUserId();
    }

    public Meal get(int id){
        final int userId = getUserId();
        log.info("get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public List<MealTo> getAll() {
        final int userId = getUserId();
        log.info("getAll for user {}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public void deleteMeal(int id){
        final int userId = getUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public Meal create(Meal meal) {
        final int userId = getUserId();
        log.info("create {} for user {}", meal, userId);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        final int userId = getUserId();
        log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = getUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

}
