package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Map<Integer, Meal>> repository = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        MealsUtil.MEALS_USER_2.forEach(meal -> save(meal,2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} for {}", meal, userId);

        Map<Integer, Meal> currentUserMeals = new HashMap<>();

        if (repository.containsKey(userId)) {
            currentUserMeals = repository.get(userId);
        } else {
            repository.put(userId, currentUserMeals);
        }

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            currentUserMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return currentUserMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} for {}", id, userId);

        Map<Integer, Meal> currentUserMeals = repository.getOrDefault(userId, Collections.emptyMap());

        Meal meal = currentUserMeals.get(id);

        return meal != null && currentUserMeals.remove(id, meal);
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} for {}", id, userId);

        return repository.getOrDefault(userId, Collections.emptyMap()).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFilteredByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return getFilteredByPredicate(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate));
    }

    private List<Meal> getFilteredByPredicate(Integer userId, Predicate<Meal> filter) {
        return userId == null ? Collections.emptyList() : repository.get(userId).values()
                .stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}

