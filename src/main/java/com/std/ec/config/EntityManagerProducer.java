package com.std.ec.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

    private EntityManagerFactory emf;

    public EntityManagerProducer() {
        this.emf = Persistence.createEntityManagerFactory("CRUD_PU");
    }

    @Produces
    @RequestScoped
    public EntityManager getEntityManager() {
        EntityManager em = emf.createEntityManager();
        System.out.println("EntityManager creado: " + em.hashCode());
        return em;
    }

    public void close(@Disposes EntityManager em) {
        if (em.isOpen()) {
            System.out.println("Cerrando EntityManager: " + em.hashCode());
            em.close();
        }
    }
}