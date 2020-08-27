package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getAll();
    Meal getById(int id);
    Meal save(Meal meal);
    void delete(int id);
}
