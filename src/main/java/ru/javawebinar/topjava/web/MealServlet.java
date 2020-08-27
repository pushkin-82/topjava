package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.inmemory.MealRepository;
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
import java.util.List;

public class MealServlet extends HttpServlet {
    public static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealRepository mealRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

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
            case "getall":
            default:
                List<MealTo> mealList = MealsUtil.createToList(mealRepository.getAll());
                request.setAttribute("mealList", mealList);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
