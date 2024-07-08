package ru.javawebinar.topjava.service.meal;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("datajpa")
public class MealServiceDataJpaTest extends AbstractMealServiceTest {
}
