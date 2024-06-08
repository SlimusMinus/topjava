package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.CRUDInterface;
import ru.javawebinar.topjava.repository.CRUDInterfaceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {
    private final CRUDInterface meals = new CRUDInterfaceImpl();
    private String id;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("meals", meals.getAll());
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
            return;
        }
        id = req.getParameter("id");
        switch (Objects.requireNonNull(action)) {
            case "delete":
                meals.deleteMeal(Integer.parseInt(id));
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
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("date"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal newMeal = new Meal(dateTime, description, calories);
        if (id == null || id.isEmpty()) {
            meals.addMeal(newMeal);
        } else {
            meals.updateMeal(Integer.parseInt(id), newMeal);
        }
        resp.sendRedirect("meals");
    }
}
