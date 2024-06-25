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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
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
    public void update() {
        service.update(getUpdated(), USER_ID);
        assertEquals(getUpdated(), service.get(ID_MEAL_1, USER_ID));
    }

    @Test
    public void updateNotFoundMeal() {
        assertThrows(NotFoundException.class, () -> service.update(getMealWithNotExistId(), USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getCreated(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getCreated();
        newMeal.setId(newId);
        assertThat(newMeal).usingRecursiveComparison().isEqualTo(created);
        assertThat(newMeal).usingRecursiveComparison().isEqualTo(service.get(newId, USER_ID));
    }

    @Test
    public void createDuplicate() {
        Meal duplicateMeal = new Meal(userMeal1.getDateTime(), "Duplicate", 300);
        assertThrows(DataIntegrityViolationException.class, () -> service.create(duplicateMeal, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(ID_MEAL_1, USER_ID);
        assertThat(SIZE_AFTER_DELETE).isEqualTo(getSizeMeals());
        assertThrows(NotFoundException.class, () -> service.delete(ID_MEAL_1, USER_ID));
    }

    @Test
    public void deleteOtherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(ID_MEAL_1, ADMIN_ID));
    }

    @Test
    public void deleteNotFoundMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void get() {
        assertThat(MealTestData.userMeal1).usingRecursiveComparison().isEqualTo(service.get(ID_MEAL_1, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate start = MealTestData.userMeal1.getDate();
        LocalDate end = MealTestData.userMeal1.getDate();
        final List<Meal> betweenInclusive = service.getBetweenInclusive(start, end, USER_ID);
        assertThat(betweenInclusive).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(filteredUserMeals);
    }

    @Test
    public void getAll() {
        List<Meal> allMeals = service.getAll(USER_ID);
        assertThat(allMeals).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(allUserMeal);
    }

    @Test
    public void getNotFoundMeal() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getOtherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.get(ID_MEAL_1, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusiveWithEmptyBoundaries() {
        final List<Meal> allMeals = service.getBetweenInclusive(null, null, USER_ID);
        assertThat(allMeals).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(allUserMeal);
    }

    private int getSizeMeals() {
        return service.getAll(USER_ID).size();
    }
}