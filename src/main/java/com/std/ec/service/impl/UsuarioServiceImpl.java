/*
 /*
 * Implementación del servicio EJB para la gestión de usuarios.
 * Actúa como intermediario entre el controlador y el repositorio.
 * Proporciona métodos para guardar, editar, eliminar y listar usuarios.
 */
package com.std.ec.service.impl;

import com.std.ec.model.entity.Usuario;
import com.std.ec.repository.UsuarioRepository;
import com.std.ec.service.IUsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class UsuarioServiceImpl implements IUsuarioService, Serializable {

    @Inject
    private transient UsuarioRepository usuarioRepository;

    @Override
    public Usuario guardar(Usuario usuario) {
        System.out.println("Servicio: Guardando usuario: " + usuario.getNombre());
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario editar(Usuario usuario) {
        System.out.println("Servicio: Editando usuario: " + usuario.getNombre() + ", ID: " + usuario.getIdUsuario());
        return usuarioRepository.update(usuario);
    }

    @Override
    public void eliminar(Usuario usuario) {
        System.out.println("Servicio: Eliminando usuario: " + usuario.getNombre() + ", ID: " + usuario.getIdUsuario());
        usuarioRepository.delete(usuario);
    }

    @Override
    public List<Usuario> listar() {
        System.out.println("Servicio: Listando usuarios");
        return usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> findAllNickName() {
        System.out.println("Servicio: Buscando usuarios por nickName");
        return usuarioRepository.findAllNickName();
    }
}
