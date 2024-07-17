package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/meals")
public class JspController extends AbstractMealController {

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable int id) {
        model.addAttribute("meal", get(id));
        return "mealForm";
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable int id) {
        deleteMeal(id);
        return "redirect:/meals";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("allMeals", getAll());
        return "meals";
    }

    @GetMapping("/{id}/edit")
    public String formUpdate(@PathVariable int id, Model model) {
        int userId = getUserId();
        Meal meal = service.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping("/add-new")
    public String formAddNew(Model model) {
        model.addAttribute("meal", new Meal());
        return "mealForm";

    }

    @RequestMapping("/save")
    public String save(@ModelAttribute("meal") Meal meal) {
        if (meal.isNew()) {
            create(meal);
        } else {
            update(meal, getUserId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String getBetween(Model model,
                             @RequestParam String startDate,
                             @RequestParam String startTime,
                             @RequestParam String endDate,
                             @RequestParam String endTime) {

        LocalDate startD = (startDate == null || startDate.isEmpty()) ? LocalDate.of(2000, 1, 1) : LocalDate.parse(startDate);
        LocalTime startT = (startTime == null || startTime.isEmpty()) ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalDate endD = (endDate == null || endDate.isEmpty()) ? LocalDate.now() : LocalDate.parse(endDate);
        LocalTime endT = (endTime == null || endTime.isEmpty()) ? LocalTime.MAX : LocalTime.parse(endTime);
        model.addAttribute("allMeals", getBetween(startD,startT, endD, endT));
        return "meals";
    }

}
