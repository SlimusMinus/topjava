package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.find(User.class, userId); // Загрузка пользователя жадно, если требуется
        meal.setUser(user);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            Query query = em.createNamedQuery(Meal.UPDATE);
            query.setParameter("date_time", meal.getDateTime());
            query.setParameter("description", meal.getDescription());
            query.setParameter("calories", meal.getCalories());
            query.setParameter("user_id", userId);
            query.setParameter("id", meal.getId());
            if (query.executeUpdate() == 0) {
                return null;
            }
            return meal;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User ref = em.find(User.class, userId);
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        try {
            return em.createNamedQuery(Meal.GET_BY_ID, Meal.class)
                    .setParameter("id", id)
                    .setParameter("user_id", userId)
                    .getSingleResult();
        } catch (NoResultException exception) {
            throw new NotFoundException("Meal not found with id=" + id + " and userId=" + userId);
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        User ref = em.find(User.class, userId);
        return em.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        User ref = em.find(User.class, userId);
        return em.createNamedQuery(Meal.GET_ALL_FILTERED, Meal.class)
                .setParameter("user_id", userId)
                .setParameter("start_time", startDateTime)
                .setParameter("end_time", endDateTime)
                .getResultList();
    }
}
