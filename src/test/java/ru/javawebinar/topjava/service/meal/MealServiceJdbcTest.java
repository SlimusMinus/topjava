package ru.javawebinar.topjava.service.meal;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("jdbc")
public class MealServiceJdbcTest extends AbstractMealServiceTest{
}
