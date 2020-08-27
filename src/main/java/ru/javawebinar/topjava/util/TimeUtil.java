package ru.javawebinar.topjava.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static boolean isBetweenInclusive(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return DATE_TIME_FORMATTER;
    }
}
