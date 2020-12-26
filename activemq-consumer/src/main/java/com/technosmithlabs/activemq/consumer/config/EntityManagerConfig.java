package com.technosmithlabs.activemq.consumer.config;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Component
public class EntityManagerConfig<T> {

    private EntityManager entityManager = null;

    public Boolean persist(T object) {
        final EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(object);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (entityManager != null) {
                closeEntityManager();
            }
            return false;
        }
    }

    private EntityManager getEntityManager() {
        if (this.entityManager == null) {
            this.entityManager = Persistence.createEntityManagerFactory("TechnoSmithLabs-PersistenceUnit").createEntityManager();
        }
        return this.entityManager;
    }

    @PreDestroy
    private void closeEntityManager() {
        getEntityManager().close();
    }

}
