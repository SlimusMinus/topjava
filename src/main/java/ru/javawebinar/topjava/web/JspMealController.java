package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    @GetMapping("/meals/{id}")
    public String get(Model model, @PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        model.addAttribute("meal", service.get(id, userId));
        return "mealForm";
    }

    @PostMapping("/meals/{id}")
    public String delete(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/meals")
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll meal for user {}", userId);
        model.addAttribute("allMeals", MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/meals/{id}/edit")
    public String formUpdateMeal(@PathVariable int id, Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping("addNewMeal")
    public String addNewMeal(Model model){
        model.addAttribute("meal", new Meal());
        return "mealForm";

    }

    @RequestMapping("/saveMeal")
    public String saveMeal(@ModelAttribute("meal") Meal meal){
        int userId = SecurityUtil.authUserId();
        if (meal.isNew()) {
            service.create(meal, userId);
        } else {
            service.update(meal, userId);
        }        return "redirect:/meals";
    }

    @GetMapping("/mealsSort")
    public String getBetween(Model model,
                             @RequestParam LocalDate startDate,
                             @RequestParam LocalTime startTime,
                             @RequestParam LocalDate endDate,
                             @RequestParam LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        model.addAttribute("allMeals", MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }

}
