package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final int authUserId = SecurityUtil.authUserId();

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(authUserId);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(authUserId, id);
    }

    public Meal create(Meal user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(authUserId, user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(authUserId, id);
    }

    public void update(Meal user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(authUserId, user);
    }


}