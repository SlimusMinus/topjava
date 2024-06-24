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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        assertEquals(MealTestData.meal1, service.get(ID_MEAL_FROM_DB, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate start = MealTestData.meal1.getDate();
        LocalDate end = MealTestData.meal1.getDate();
        final List<Meal> betweenInclusive = service.getBetweenInclusive(start, end, USER_ID);
        assertThat(SIZE_AFTER_FILTER).isEqualTo(betweenInclusive.size());
        assertThat(betweenInclusive).containsExactlyElementsOf(ALL_MEALS_AFTER_FILTERED);

    }

    @Test
    public void getAll() {
        List<Meal> allMeals = service.getAll(USER_ID);
        assertThat(allMeals).hasSize(SIZE_ALL_MEALS);
        assertThat(allMeals).containsExactlyElementsOf(ALL_MEALS_USER);
    }

    @Test
    public void update() {
        service.update(getUpdated(), USER_ID);
        assertEquals(getUpdated(), service.get(ID_MEAL_FROM_DB, USER_ID));
    }


    @Test
    public void create() {
        final Meal newMeal = service.create(mealForSave, USER_ID);
        Integer newId = newMeal.getId();
        assertThat(SIZE_AFTER_CREATE).isEqualTo(getSizeMeals());
        assertEquals(newMeal, service.get(newId, USER_ID));
    }

    @Test
    public void updateNotFoundMeal() {
        assertThrows(NotFoundException.class, () -> service.update(MEAL_WITH_NOT_EXiST_ID, USER_ID));
    }

    @Test
    public void createDuplicate() {
        Meal duplicateMeal = new Meal(meal2.getDateTime(), "Duplicate", 300);
        assertThrows(DataIntegrityViolationException.class, () -> service.create(duplicateMeal, USER_ID));
    }

    @Test
    public void getNotFoundMeal() {
        assertThrows(NotFoundException.class, () -> service.get(INCORRECT_ID_MEAL, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(ID_MEAL_FROM_DB, USER_ID);
        assertThat(SIZE_AFTER_DELETE).isEqualTo(getSizeMeals());
        assertThrows(NotFoundException.class, () -> service.delete(ID_MEAL_FROM_DB, USER_ID));
    }

    @Test
    public void getOtherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.get(ID_MEAL_FROM_DB, OTHER_USER));
    }

    @Test
    public void deleteOtherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(ID_MEAL_FROM_DB, OTHER_USER));
    }

    @Test
    public void deleteNotFoundMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(INCORRECT_ID_MEAL, USER_ID));
    }

    private int getSizeMeals() {
        return service.getAll(USER_ID).size();
    }
}