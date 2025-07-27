package com.std.ec.service;

import com.std.ec.model.entity.Usuario;
import java.util.List;

public interface IUsuarioService {
    Usuario guardar(Usuario usuario);
    Usuario editar(Usuario usuario);
    void eliminar(Usuario usuario);
    List<Usuario> listar();
    List<Usuario> findAllNickName();
}