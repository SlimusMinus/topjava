package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int NORMCALORIES = 2000;
    private MealRepository meals;

    @Override
    public void init() throws ServletException {
        log.info("MealServlet start initialization");
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        meals = new InMemoryMealRepository();
        mealList.forEach(meal -> meals.save(meal));
        log.info("in meals collection add {} meals", meals.getAll().size());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            showAllMeals(req, resp);
            return;
        }
        String id = req.getParameter("id");
        switch (Objects.requireNonNull(action)) {
            case "delete":
                log.info("delete meal with id = {}", id);
                meals.delete(Integer.parseInt(id));
                resp.sendRedirect("meals");
                break;
            case "edit":
                log.info("start edit meal");
                Meal meal;
                if (id == null) {
                    meal = new Meal();
                } else {
                    meal = meals.get(Integer.parseInt(id));
                }
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("edit.jsp").forward(req, resp);
                break;
            default:
                log.info("incorrect action = {}", action);
                showAllMeals(req, resp);
        }
    }

    private void showAllMeals(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("meals", MealsUtil.mealInMealTo(meals.getAll(), NORMCALORIES));
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("date"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("id");
        log.info("doPost method started processing meal with id = {}, description = {}, calories = {}", id, description, calories);

        Meal newMeal;
        if (id == null || id.isEmpty()) {
            log.info("add new meal");
            newMeal = new Meal(dateTime, description, calories);
        } else {
            log.info("edit meal with id = {}", id);
            newMeal = new Meal(Integer.parseInt(id), dateTime, description, calories);
        }

        meals.save(newMeal);
        resp.sendRedirect("meals");
    }
}
