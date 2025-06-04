/*
 * Repositorio genérico que proporciona operaciones CRUD básicas para cualquier entidad JPA.
 * Utiliza EntityManager para interactuar con la base de datos.
 * Es una clase abstracta que debe ser extendida por repositorios específicos.
 */
package com.std.ec.repository.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 *
 * @author WALTER ROSERO
 */
public abstract class CrudRepository<T> implements Repository<T> {

    private final Class<T> entityClass; // Clase de la entidad gestionada

    public CrudRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @PersistenceContext(unitName = "CRUD_PU") // Inyecta el EntityManager de la unidad de persistencia CRUD_PU
    protected EntityManager em;

    // Método abstracto que deben implementar las subclases para proporcionar el EntityManager
    protected abstract EntityManager getEntityManager();

    /**
     * Persiste una nueva entidad en la base de datos.
     * @param entity Entidad a guardar.
     * @return Entidad persistida.
     */
    @Override
    public T save(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    /**
     * Actualiza una entidad existente en la base de datos.
     * @param entity Entidad a actualizar.
     * @return Entidad actualizada.
     */
    @Override
    public T update(T entity) {
        getEntityManager().merge(entity);
        return entity;
    }

    /**
     * Busca una entidad por su identificador.
     * @param entityId Identificador de la entidad.
     * @return Entidad encontrada o null si no existe.
     */
    @Override
    public T findById(Object entityId) {
        return getEntityManager().find(entityClass, entityId);
    }

    /**
     * Elimina una entidad de la base de datos.
     * @param entity Entidad a eliminar.
     */
    @Override
    public void delete(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Recupera todas las entidades de la clase especificada.
     * @return Lista de todas las entidades.
     */
    @Override
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

}
