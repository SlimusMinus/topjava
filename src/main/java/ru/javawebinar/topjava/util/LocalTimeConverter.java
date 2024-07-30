package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter implements Converter<String, LocalTime> {

    private static final String DEFAULT_PATTERN = "HH-mm";
    private final DateTimeFormatter timeFormatter;

    public LocalTimeConverter() {
        this(DEFAULT_PATTERN);
    }

    public LocalTimeConverter(String timePattern) {
        this.timeFormatter = DateTimeFormatter.ofPattern(timePattern);
    }

    @Override
    public LocalTime convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        return LocalTime.parse(source, timeFormatter);
    }
}
