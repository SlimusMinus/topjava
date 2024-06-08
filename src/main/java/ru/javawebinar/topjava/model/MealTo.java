package ru.javawebinar.topjava.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MealTo {
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    private boolean excess;

}
