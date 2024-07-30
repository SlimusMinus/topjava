package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements Converter<String, LocalDate> {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    private final DateTimeFormatter timeFormatter;

    public LocalDateConverter() {
        this(DEFAULT_PATTERN);
    }

    public LocalDateConverter(String datePattern) {
        this.timeFormatter = DateTimeFormatter.ofPattern(datePattern);
    }

    @Override
    public LocalDate convert(String source) {
        if(source == null || source.trim().isEmpty()){
            return null;
        }
        return LocalDate.parse(source, timeFormatter);
    }
}
