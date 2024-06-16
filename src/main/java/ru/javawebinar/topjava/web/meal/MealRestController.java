
package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        final int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return service.getAll(userId);
    }

    public Meal get(int id) {
        final int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        final int userId = SecurityUtil.authUserId();
        log.info("create meal {} for user {}", meal, userId);
        return service.create(meal, userId);
    }

    public Meal update(Meal meal, int id){
        final int userId = SecurityUtil.authUserId();
        log.info("update {} by {}", meal, id);
        return service.update(meal, id, userId);
    }

    public void delete(int id) {
        final int userId = SecurityUtil.authUserId();
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getFiltered for user {} from {} {} to {} {}", userId, startDate, startTime, endDate, endTime);
        return service.getFiltered(userId, startDate, endDate, startTime, endTime);
    }


}
