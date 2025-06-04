/*
 /*
 * Controlador JSF que gestiona la interacción entre la vista (index.xhtml) y el servicio de usuarios.
 * Maneja las operaciones CRUD (crear, leer, actualizar, eliminar) y actualiza la vista mediante PrimeFaces.
 * Utiliza el ámbito ViewScoped para mantener el estado durante la interacción con la página.
 */
package com.std.ec.controller;

import com.std.ec.model.entity.Usuario;
import com.std.ec.service.IUsuarioService;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

/**
 * @author WALTER ROSERO
 */
@Getter
@Setter
@Named(value = "usuarioMB")
@ViewScoped
public class UsuarioController implements Serializable {

    @EJB // Inyecta el servicio de usuarios
    private IUsuarioService usuarioService;

    private Usuario usuario; // Usuario actualmente seleccionado o nuevo
    private List<Usuario> usuarios;

    /**
     * Inicializa un nuevo usuario para el formulario.
     */
    public void nuevo() {
        usuario = new Usuario();
    }

    public List<Usuario> getUsuarios() {
        return usuarios = usuarioService.listar();
    }

    /**
     * Guarda o actualiza un usuario y actualiza la vista.
     */
    public void guardar() {
        if (usuario.getIdUsuario() == null) {
            usuarioService.guardar(usuario); // Guarda nuevo usuario
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario agregado"));
        } else {
            usuarioService.editar(usuario); // Actualiza usuario existente
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario editado"));
        }
        usuarios = usuarioService.listar(); // Recarga la lista
        nuevo(); // Limpia el formulario
        PrimeFaces.current().executeScript("PF('dlgUsuarioRegistro').hide()"); // Cierra el diálogo
        PrimeFaces.current().ajax().update("form:messages","form:dt-usuarios"); // Actualiza tabla y mensajes
    }

    /**
     * Elimina un usuario y actualiza la vista.
     */
    public void eliminar() {
        usuarioService.eliminar(usuario);
        usuarios = usuarioService.listar(); // Recarga la lista
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario eliminado"));
        PrimeFaces.current().executeScript("PF('dlgEliminarUsuario').hide()"); // Cierra el diálogo de confirmación
        PrimeFaces.current().ajax().update("form:messages","form:dt-usuarios"); // Actualiza tabla y mensajes
    }
}
