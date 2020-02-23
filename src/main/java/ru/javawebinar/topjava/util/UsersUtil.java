package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> USERS = Arrays.asList(
            new User(null, "admin", "user@gmail.com", "pass", Role.ROLE_ADMIN, Role.ROLE_USER),
            new User(null, "petro", "123@321", "ololo", Role.ROLE_USER)
    );
}
