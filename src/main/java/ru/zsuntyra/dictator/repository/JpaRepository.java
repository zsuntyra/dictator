package ru.zsuntyra.dictator.repository;

import ru.zsuntyra.dictator.config.HibernateConfig;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Objects;

public abstract class JpaRepository<T> {

    private Class<T> entityClass;

    private EntityManagerFactory entityManagerFactory = HibernateConfig.getSessionFactory();
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public JpaRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public static <T> T getElementOrNull(List<T> resultList) {
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityManagerFactory, entityManager);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @PreDestroy
    public void preDestroy() {
        if (entityManagerFactory.isOpen() && entityManagerFactory != null) {
            entityManagerFactory.close();
        }
        if (entityManager.isOpen() && entityManager != null) {
            entityManager.close();
        }
    }

    public void create(T entity) {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
        getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();
    }

    public void update(T entity) {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
        getEntityManager().merge(entity);
        getEntityManager().getTransaction().commit();
    }

    public T findById(long id) {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
        T entity = getEntityManager().find(entityClass, id);
        getEntityManager().getTransaction().commit();
        return entity;
    }

    public void removeById(long id) {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
        T entityRef = getEntityManager().find(entityClass, id);
        getEntityManager().remove(entityRef);
        getEntityManager().getTransaction().commit();
    }

    public List<T> findAll() {
        String query = "SELECT p FROM " + entityClass.getSimpleName() + " p";
        return getEntityManager().createQuery(query, entityClass).getResultList();
    }

}
