/*
 * Repositorio EJB que gestiona operaciones CRUD para la entidad Usuario.
 * Extiende CrudRepository para heredar métodos genéricos (save, update, delete, findAll).
 * Incluye una consulta personalizada para buscar usuarios por nickName.
 */
package com.std.ec.repository;

import com.std.ec.model.entity.Usuario;
import com.std.ec.repository.persistence.CrudRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author WALTER ROSERO
 */
@ApplicationScoped // Reemplaza @Stateless
public class UsuarioRepository extends CrudRepository<Usuario> implements Serializable {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    @Inject
    private transient EntityManager em; // Usa transient para evitar serializar el EntityManager

    // Proporciona el EntityManager para operaciones de persistencia
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Usuario> findAllNickName() {
        System.out.println("Repositorio: Buscando usuarios con nickName ~ %W%");
        return em.createQuery("SELECT u FROM Usuario u WHERE u.nickName LIKE :pattern", Usuario.class)
                .setParameter("pattern", "%W%")
                .getResultList();
    }

    @Override
    public List<Usuario> findAll() {
        System.out.println("Repositorio: Listando todos los usuarios");
        List<Usuario> result = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        System.out.println("Repositorio: Encontrados " + result.size() + " usuarios");
        return result;
    }

     @Override
    public Usuario save(Usuario entity) {
        System.out.println("Repositorio: Guardando usuario: " + entity.getNombre());
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            em.flush();
            tx.commit();
            System.out.println("Usuario guardado con ID: " + entity.getIdUsuario());
            return entity;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error al guardar usuario: " + e.getMessage());
            throw new RuntimeException("Error al guardar usuario", e);
        }
    }

    @Override
    public Usuario update(Usuario entity) {
        System.out.println("Repositorio: Actualizando usuario: " + entity.getNombre() + ", ID: " + entity.getIdUsuario());
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Usuario updated = em.merge(entity);
            em.flush();
            tx.commit();
            System.out.println("Usuario actualizado: " + updated.getNombre());
            return updated;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    @Override
    public void delete(Usuario entity) {
        System.out.println("Repositorio: Eliminando usuario: " + entity.getNombre() + ", ID: " + entity.getIdUsuario());
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(entity));
            em.flush();
            tx.commit();
            System.out.println("Usuario eliminado: " + entity.getNombre());
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }

}
