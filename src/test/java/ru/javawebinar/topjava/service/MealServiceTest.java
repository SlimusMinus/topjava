package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/initDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    private final int NOT_FOUND_MEAL = 25;

    @Test
    public void get() {
        final Meal mealFromDB = MealTestData.meal1;
        assertThat(mealFromDB).isEqualTo(MealTestData.meal1);
    }

    @Test
    public void getNotFoundMeal() {
        assertThatThrownBy(() -> service.get(NOT_FOUND_MEAL, 100000)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void delete() {
        service.delete(1, 100000);
        assertThat(6).isEqualTo(getSizeMeals());
    }

    @Test
    public void deleteNotFoundMeal() {
        assertThatThrownBy(() -> service.delete(NOT_FOUND_MEAL, 100000)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate start = MealTestData.meal1.getDate();
        LocalDate end = MealTestData.meal1.getDate();
        final List<Meal> betweenInclusive = service.getBetweenInclusive(start, end, 100000);
        assertThat(3).isEqualTo(betweenInclusive.size());
    }

    @Test
    public void getAll() {
        assertThat(7).isEqualTo(getSizeMeals());
    }

    @Test
    public void update() {
        final Meal mealFromDB = MealTestData.meal1;
        mealFromDB.setCalories(777);
        service.update(mealFromDB, 100000);
        assertThat(777).isEqualTo(mealFromDB.getCalories());
    }

    @Test
    public void updateNotFoundMeal() {
        assertThatThrownBy(() -> service.update(service.get(NOT_FOUND_MEAL, 100000), 100000)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void create() {
        service.create(MealTestData.userForSave, 100000);
        assertThat(8).isEqualTo(getSizeMeals());
    }

    @Test
    public void createDuplicate() {
        Meal duplicateMeal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Duplicate", 300);
        assertThatThrownBy(() -> service.create(duplicateMeal, 100000)).isInstanceOf(DataIntegrityViolationException.class);
    }

    private int getSizeMeals() {
        return service.getAll(100000).size();
    }
}