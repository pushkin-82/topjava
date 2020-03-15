package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
    @Test
    public void getByIdAndUserId() {
        Meal meal = service.getByIdAndUserId(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        User user = meal.getUser();
        MEAL_MATCHER.assertMatch(meal, MealTestData.MEAL1);
        USER_MATCHER.assertMatch(user, UserTestData.USER);
    }

    @Test
    public void getNotOwnByIdAndUserId() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getByIdAndUserId(MealTestData.MEAL1_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void getNotFoundByIdAndUserId() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getByIdAndUserId(1, UserTestData.USER_ID));
    }}
