package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;

    public static final Meal USER_MEAL_1 = new Meal(MEAL_ID,
            LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_2 = new Meal(MEAL_ID + 1,
            LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal(MEAL_ID + 2,
            LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL_4 = new Meal(MEAL_ID + 3,
            LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal USER_MEAL_5 = new Meal(MEAL_ID + 4,
            LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal USER_MEAL_6 = new Meal(MEAL_ID + 5,
            LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final Meal ADMIN_MEAL_1 = new Meal(MEAL_ID + 6,
            LocalDateTime.of(2012, Month.JANUARY, 23, 13, 0), "Lunch", 400);
    public static final Meal ADMIN_MEAL_2 = new Meal(MEAL_ID + 7,
            LocalDateTime.of(2012, Month.JANUARY, 24, 12, 0), "Dinner", 1610);


    public static final List<Meal> USER_MEALS = Arrays.asList(USER_MEAL_6, USER_MEAL_5, USER_MEAL_4,
            USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);

    public static final List<Meal> FILTERED_USER_MEALS = Arrays.asList(USER_MEAL_6, USER_MEAL_5, USER_MEAL_4);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 26, 12, 00),
                "qwerty", 2000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setDescription("ololo");
        updated.setCalories(212);

        return updated;
    }


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}