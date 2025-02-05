package com.example.service;

import java.util.List;
import com.example.model.*;
import com.example.repository.*;

public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public void adicionarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }
        if (usuarioRepository.existeUsuarioPorNome(usuario.getNome())) {
            throw new IllegalArgumentException("Já existe um usuário com este nome.");
        }
        usuarioRepository.addUsuario(usuario);
        System.out.println("Usuário adicionado com sucesso!");
    }
    
    public Usuario fazerLogin(String nome, String senha) {
        for (Usuario usuario : usuarioRepository.getAllUsuarios()) {
            if (usuario.getNome().equalsIgnoreCase(nome) && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null;
    }
    
    public Usuario buscarUsuarioPorId(int id) {
        Usuario usuario = usuarioRepository.getUsuarioPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário com ID " + id + " não encontrado.");
        }
        return usuario;
    }
    
    public Usuario buscarUsuarioPorNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        Usuario usuario = usuarioRepository.getUsuarioPorNome(nome);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário com nome " + nome + " não encontrado.");
        }
        return usuario;
    }
    
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.getAllUsuarios();
    }
    
    public void atualizarUsuario(int id, String novoNome, String novaSenha) {
        Usuario usuario = usuarioRepository.getUsuarioPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário com ID " + id + " não encontrado.");
        }
        if (novoNome != null && !novoNome.isEmpty()) {
            if (usuarioRepository.existeUsuarioPorNome(novoNome)) {
                throw new IllegalArgumentException("Já existe outro usuário com o nome " + novoNome + ".");
            }
            usuario.setNome(novoNome);
        }
        if (novaSenha != null && !novaSenha.isEmpty()) {
            usuario.setSenha(novaSenha);
        }
        System.out.println("Usuário atualizado com sucesso!");
    }
}
