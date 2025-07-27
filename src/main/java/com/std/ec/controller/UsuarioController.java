package com.std.ec.controller;

import com.std.ec.model.entity.Usuario;
import com.std.ec.service.IUsuarioService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

@Getter
@Setter
@Named(value = "usuarioMB")
@ViewScoped
public class UsuarioController implements Serializable {


    @Inject // Inyecta el servicio de usuarios
    private IUsuarioService usuarioService;

    /**
     * Objeto Usuario que representa el usuario actualmente seleccionado o el nuevo
     * usuario a crear.
     */
    private Usuario usuario; // Usuario actualmente seleccionado o nuevo
    /**
     * Lista de usuarios recuperados de la base de datos.
     */
    private List<Usuario> usuarios;

    /**
     * Método de inicialización que se ejecuta después de la construcción del bean.
     * Inicializa la lista de usuarios y carga todos los usuarios existentes.
     */
    @PostConstruct
    public void init() {
        usuarios = new ArrayList<>();
        loadUsuarios();
    }

    /**
     * Prepara un nuevo objeto Usuario para la creación de un nuevo registro.
     */
    public void nuevo() {
        usuario = new Usuario();
        System.out.println("Nuevo usuario inicializado");
    }

    /**
     * Obtiene la lista de todos los usuarios.
     * 
     * @return Una lista de objetos Usuario.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Carga todos los usuarios desde el servicio y actualiza la lista de usuarios.
     */
    private void loadUsuarios() {
        usuarios = usuarioService.listar();
        System.out.println("Usuarios cargados: " + (usuarios != null ? usuarios.size() : "null"));
        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente en la base de datos.
     * Muestra mensajes de éxito o error en la interfaz de usuario.
     */
    public void guardar() {
        try {
            if (usuario == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario no inicializado"));
                System.out.println("Error: usuario es null");
                return;
            }
            System.out.println("Guardando usuario: " + usuario.getNombre() + ", ID: " + usuario.getIdUsuario());
            if (usuario.getIdUsuario() == null) {
                usuarioService.guardar(usuario);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario agregado"));
            } else {
                usuarioService.editar(usuario);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario editado"));
            }
            loadUsuarios();
            nuevo();
            PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar", e.getMessage()));
            System.out.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    /**
     * Elimina un usuario existente de la base de datos.
     * Muestra mensajes de éxito o error en la interfaz de usuario.
     */
    public void eliminar() {
        try {
            if (usuario == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario no seleccionado"));
                System.out.println("Error: usuario es null para eliminar");
                return;
            }
            System.out.println("Eliminando usuario: " + usuario.getNombre() + ", ID: " + usuario.getIdUsuario());
            usuarioService.eliminar(usuario);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario eliminado"));
            loadUsuarios();
            PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar", e.getMessage()));
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
}
