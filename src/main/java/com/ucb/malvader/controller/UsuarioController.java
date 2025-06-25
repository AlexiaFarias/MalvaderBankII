package com.ucb.malvader.controller;

import com.ucb.malvader.model.Usuario;
import com.ucb.malvader.model.Funcionario;
import com.ucb.malvader.model.Cargo;
import com.ucb.malvader.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.bcrypt.BCrypt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/usuarios/cadastrar")
    public void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("/Login.html");
            return;
        }

        String cpf = request.getParameter("cpf");

        if (usuarioRepository.findByCpf(cpf).isPresent()) {
            response.sendRedirect("/CadastroUsuario.html?erro=cpfExistente");
            return;
        }

        String tipo = request.getParameter("tipo_usuario");
        String senha = request.getParameter("senha");

        if (senha == null || senha.isEmpty()) {
            response.sendRedirect("/CadastroUsuario.html?erro=senhaVazia");
            return;
        }

        String senhaCriptografada = BCrypt.hashpw(senha, BCrypt.gensalt());

        Usuario usuario;

        if ("FUNCIONARIO".equals(tipo)) {
            Funcionario funcionario = new Funcionario();
            funcionario.setCodigoFuncionario(request.getParameter("codigo_funcionario"));
            funcionario.setCargo(Cargo.valueOf(request.getParameter("cargo")));
            funcionario.setCodigoEmpresa(request.getParameter("codigo_empresa")); // cuidado: esse campo não está no HTML atual
            usuario = funcionario;
        } else {
            usuario = new Usuario();
        }

        usuario.setNome(request.getParameter("nome"));
        usuario.setCpf(cpf);

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            usuario.setData_nascimento(formatter.parse(request.getParameter("data_nascimento")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        usuario.setTelefone(request.getParameter("telefone"));
        usuario.setTipo_usuario(Usuario.TipoUsuario.valueOf(tipo));
        usuario.setSenhaHash(senhaCriptografada);

        usuarioRepository.save(usuario);

        response.sendRedirect(tipo.equals("FUNCIONARIO") ? "/MenuFuncionario.html" : "/MenuCliente.html");
    }
}
