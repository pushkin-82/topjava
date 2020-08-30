package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getByIdAndMeals() {
        User user = service.getByIdWithMeals(UserTestData.USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.USER);
        MEAL_MATCHER.assertMatch(user.getMeals(), MealTestData.MEALS);
    }

    @Test
    public void getNotFoundByIdAndMeals() {
        Assert.assertThrows(NotFoundException.class,() -> service.getByIdWithMeals(1));
    }
}
