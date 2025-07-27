/*
 * Repositorio genérico que proporciona operaciones CRUD básicas para cualquier entidad JPA.
 * Utiliza EntityManager para interactuar con la base de datos.
 * Es una clase abstracta que debe ser extendida por repositorios específicos.
 */
package com.std.ec.repository.persistence;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author WALTER ROSERO
 */
public abstract class CrudRepository<T> implements Repository<T>, Serializable {

    private final Class<T> entityClass; // Clase de la entidad gestionada

    public CrudRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Inject
    protected EntityManager em;

    // Método abstracto que deben implementar las subclases para proporcionar el
    // EntityManager
    protected abstract EntityManager getEntityManager();

    @Override
    public T save(T entity) {
        System.out.println("CrudRepository: Guardando entidad: " + entity);
        EntityTransaction tx = getEntityManager().getTransaction();
        try {
            tx.begin();
            getEntityManager().persist(entity);
            getEntityManager().flush();
            tx.commit();
            System.out.println("Entidad guardada: " + entity);
            return entity;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error al guardar entidad: " + e.getMessage());
            throw new RuntimeException("Error al guardar entidad", e);
        }
    }

    @Override
    public T update(T entity) {
        System.out.println("CrudRepository: Actualizando entidad: " + entity);
        EntityTransaction tx = getEntityManager().getTransaction();
        try {
            tx.begin();
            T updated = getEntityManager().merge(entity);
            getEntityManager().flush();
            tx.commit();
            System.out.println("Entidad actualizada: " + updated);
            return updated;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error al actualizar entidad: " + e.getMessage());
            throw new RuntimeException("Error al actualizar entidad", e);
        }
    }

    @Override
    public T findById(Object entityId) {
        System.out.println("CrudRepository: Buscando entidad por ID: " + entityId);
        return getEntityManager().find(entityClass, entityId);
    }

    @Override
    public void delete(T entity) {
        System.out.println("CrudRepository: Eliminando entidad: " + entity);
        EntityTransaction tx = getEntityManager().getTransaction();
        try {
            tx.begin();
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
            tx.commit();
            System.out.println("Entidad eliminada: " + entity);
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error al eliminar entidad: " + e.getMessage());
            throw new RuntimeException("Error al eliminar entidad", e);
        }
    }

    @Override
    public List<T> findAll() {
        System.out.println("CrudRepository: Listando todas las entidades");
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        List<T> result = getEntityManager().createQuery(cq).getResultList();
        System.out.println("CrudRepository: Encontradas " + result.size() + " entidades");
        return result;
    }

}
