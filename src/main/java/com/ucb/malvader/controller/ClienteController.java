package com.ucb.malvader.controller;

import com.ucb.malvader.model.Cliente;
import com.ucb.malvader.model.Usuario;
import com.ucb.malvader.repository.ClienteRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


import java.io.IOException;


@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/clientes/cadastrar")
    public void cadastrarCliente(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("usuarioLogado") == null) {
                response.sendRedirect("/Login.html?erro=login_necessario&pagina=clientes_cadastrar");
                return;
            }

            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

            if (usuarioLogado.getTipo_usuario() != Usuario.TipoUsuario.CLIENTE) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Apenas clientes podem acessar esta página");
                return;
            }


            Cliente cliente = new Cliente();
            cliente.setId_usuario(usuarioLogado.getId_usuario());
            cliente.setNome(usuarioLogado.getNome());
            cliente.setCpf(usuarioLogado.getCpf());
            cliente.setData_nascimento(usuarioLogado.getData_nascimento());
            cliente.setTelefone(usuarioLogado.getTelefone());
            cliente.setTipo_usuario(usuarioLogado.getTipo_usuario());
            cliente.setSenhaHash(usuarioLogado.getSenhaHash());
            cliente.setOtp_ativo(usuarioLogado.getOtp_ativo());
            cliente.setOtp_expiracao(usuarioLogado.getOtp_expiracao());
            cliente.setScore_credito(700);

            clienteRepository.save(cliente);
            response.sendRedirect("/MenuCliente.html");
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar cliente: " + e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @GetMapping("/usuario/cadastro")
public String mostrarFormularioCadastro() {
    return "CadastroUsuario.html"; 
}
}


