package ru.javawebinar.topjava.service.datajpa;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.DATAJPA)
public class JpaMealServiceDataTest extends AbstractMealServiceTest {
}
