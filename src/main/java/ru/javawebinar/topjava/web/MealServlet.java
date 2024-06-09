package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.DTO.MealInMealTo;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private MealRepository meals;
    @Override
    public void init() throws ServletException {
        meals = new InMemoryMealRepository();
        meals.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.save( new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.save( new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.save( new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.save( new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    private String id;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("meals", MealInMealTo.exchange(meals.getAll()));
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
            return;
        }
        id = req.getParameter("id");
        switch (Objects.requireNonNull(action)) {
            case "delete":
                meals.delete(Integer.parseInt(id));
                resp.sendRedirect("meals");
                break;
            case "edit":
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
                req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("date"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal newMeal = new Meal(dateTime, description, calories);
        if (id == null || id.isEmpty()) {
            meals.save(newMeal);
        } else {
            meals.update(Integer.parseInt(id), newMeal);
        }
        resp.sendRedirect("meals");
    }
}
