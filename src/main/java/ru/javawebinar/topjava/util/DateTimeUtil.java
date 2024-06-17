package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpenTime(LocalTime mealTime, LocalTime startTime, LocalTime endTime) {
        return !mealTime.isBefore(startTime) && mealTime.isBefore(endTime);
    }

    public static boolean isBetweenHalfOpenDate(LocalDate mealDate, LocalDate startDate, LocalDate endDate) {
        return !mealDate.isBefore(startDate) && mealDate.isBefore(endDate);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

