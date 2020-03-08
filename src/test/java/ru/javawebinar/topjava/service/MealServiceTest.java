package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-jdbc.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL_ID, USER_ID);
        assertMatch(USER_MEAL_1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        mealService.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() {
        mealService.get(MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        mealService.delete(MEAL_ID, USER_ID);
        mealService.get(MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() {
        mealService.delete(MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        mealService.delete(1, USER_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> filteredMeals = mealService.getBetweenHalfOpen(LocalDate.of(2015, Month.MAY, 31),
                LocalDate.of(2015, Month.MAY, 31), USER_ID);
        assertMatch(FILTERED_USER_MEALS, filteredMeals);
    }

    @Test
    public void getBetweenHalfOpenNullStartDate() {
        List<Meal> filteredMeals = mealService.getBetweenHalfOpen(null,
                LocalDate.of(2015, Month.MAY, 31), USER_ID);
        assertMatch(USER_MEALS, filteredMeals);
    }

    @Test
    public void getBetweenHalfOpenNullEndDate() {
        List<Meal> filteredMeals = mealService.getBetweenHalfOpen(LocalDate.of(2015, Month.MAY, 31),
                null, USER_ID);
        assertMatch(FILTERED_USER_MEALS, filteredMeals);
    }

    @Test
    public void getBetweenHalfOpenNullDates() {
        List<Meal> filteredMeals = mealService.getBetweenHalfOpen(null,null, USER_ID);
        assertMatch(USER_MEALS, filteredMeals);
    }

    @Test(expected = AssertionError.class)
    public void getBetweenHalfOpenAnotherUser() {
        List<Meal> filteredMeals = mealService.getBetweenHalfOpen(LocalDate.of(2015, Month.MAY, 31),
                LocalDate.of(2015, Month.MAY, 31), ADMIN_ID);
        assertMatch(FILTERED_USER_MEALS, filteredMeals);
    }
    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(USER_MEALS, meals);
    }

    @Test(expected = AssertionError.class)
    public void getAllAnotherUser() {
        List<Meal> meals = mealService.getAll(ADMIN_ID);
        assertMatch(USER_MEALS, meals);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(MEAL_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherUserMeal() {
        Meal updated = getUpdated();
        mealService.update(updated, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = mealService.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(mealService.get(newId, USER_ID), newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicatedDateTime() {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY,31, 13, 0), "Lunch", 252);
        mealService.create(newMeal, USER_ID);
    }
}