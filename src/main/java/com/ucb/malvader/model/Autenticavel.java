package com.ucb.malvader.model;

import com.ucb.malvader.model.Usuario.TipoUsuario;

public interface Autenticavel {
    String getCpf();
    String getSenhaHash();
    TipoUsuario getTipo_usuario();
}


