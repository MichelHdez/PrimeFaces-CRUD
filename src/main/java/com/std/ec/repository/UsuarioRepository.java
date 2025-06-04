/*
 * Repositorio EJB que gestiona operaciones CRUD para la entidad Usuario.
 * Extiende CrudRepository para heredar métodos genéricos (save, update, delete, findAll).
 * Incluye una consulta personalizada para buscar usuarios por nickName.
 */
package com.std.ec.repository;

import com.std.ec.model.entity.Usuario;
import com.std.ec.repository.persistence.CrudRepository;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

/**
 *
 * @author WALTER ROSERO
 */
@Stateless // Define un EJB sin estado
public class UsuarioRepository extends CrudRepository<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    // Proporciona el EntityManager para operaciones de persistencia
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Busca usuarios cuyo nickName contiene la letra "W".
     * @return Lista de usuarios que coinciden con el criterio.
     */
    public List<Usuario> findAllNickName() {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.nickName LIKE :pattern", Usuario.class)
                 .setParameter("pattern", "%W%")
                 .getResultList();
    }

    /**
     * @return Lista de usuarios que coinciden con el criterio.
     */
    public List<Usuario> findAll() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class)
                 .getResultList();
    }

}
