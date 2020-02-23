package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private MealRepository mealRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealRepository = new InMemoryMealRepository();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("POST request");
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal newMeal = new Meal(id, dateTime, description, calories);

        mealRepository.save(newMeal);

        response.sendRedirect("meals");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET request:  " + request.getRequestURL());

        request.setAttribute("formatter", TimeUtil.getDateTimeFormatter());

        String action = request.getParameter("action");

        if (action != null) {
            action = action.toLowerCase();
        } else {
            action = "getall";
        }

        switch (action) {
            case "create":
                Meal meal = new Meal(-1, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "default", 1000);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
            case "update":
                Meal updMeal = mealRepository.getById(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("meal", updMeal);
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                mealRepository.delete(id);
                response.sendRedirect("meals");
                break;
            default:
                List<MealTo> mealList = MealsUtil.createToList(mealRepository.getAll());
                request.setAttribute("mealList", mealList);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

    }
}
