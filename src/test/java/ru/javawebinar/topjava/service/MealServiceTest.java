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
import ru.javawebinar.topjava.repository.MealRepository;
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
        "classpath:spring/spring-jdbc.xml",
        "classpath:spring/spring-db.xml"
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
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(meal, USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() {
        service.get(MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        service.get(MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(1, USER_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> filteredMeals = service.getBetweenHalfOpen(LocalDate.of(2015, Month.MAY, 31),
                LocalDate.of(2015, Month.MAY, 31), USER_ID);
        assertMatch(FILTERED_USER_MEALS, filteredMeals);
    }

    @Test
    public void getBetweenHalfOpenNullStartDate() {
        List<Meal> filteredMeals = service.getBetweenHalfOpen(null,
                LocalDate.of(2015, Month.MAY, 31), USER_ID);
        assertMatch(USER_MEALS, filteredMeals);
    }

    @Test
    public void getBetweenHalfOpenNullEndDate() {
        List<Meal> filteredMeals = service.getBetweenHalfOpen(LocalDate.of(2015, Month.MAY, 31),
                null, USER_ID);
        assertMatch(FILTERED_USER_MEALS, filteredMeals);
    }

    @Test
    public void getBetweenHalfOpenNullDates() {
        List<Meal> filteredMeals = service.getBetweenHalfOpen(null,null, USER_ID);
        assertMatch(USER_MEALS, filteredMeals);
    }

    @Test(expected = AssertionError.class)
    public void getBetweenHalfOpenAnotherUser() {
        List<Meal> filteredMeals = service.getBetweenHalfOpen(LocalDate.of(2015, Month.MAY, 31),
                LocalDate.of(2015, Month.MAY, 31), ADMIN_ID);
        assertMatch(FILTERED_USER_MEALS, filteredMeals);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, USER_MEALS);
    }

    @Test(expected = AssertionError.class)
    public void getAllAnotherUser() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        assertMatch(USER_MEALS, meals);
    }

    @Test
    public void update() {
        Meal updated= getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotOwnMeal() {
        Meal updated= getUpdated();
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeCreated() {
        service.create(new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                "Dinner", 3200), USER_ID);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeUpdated() {
        service.update(new Meal(MEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                "Dinner", 3200), USER_ID);
    }
}