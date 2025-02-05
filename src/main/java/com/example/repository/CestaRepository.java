package com.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import com.example.model.*;
import com.example.service.ConnectionFactory;;

public class CestaRepository {

    public void adicionarLivroNaCesta(Usuario usuario, Livro livro) {
        String sql = "INSERT INTO usuario_cesta (usuario_id, livro_id) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setInt(2, livro.getIdLivro());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar livro na cesta: " + e.getMessage());
        }
    }

    public void removerLivroDaCesta(Usuario usuario, Livro livro) {
        String sql = "DELETE FROM usuario_cesta WHERE usuario_id = ? AND livro_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setInt(2, livro.getIdLivro());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao remover livro da cesta: " + e.getMessage());
        }
    }
}
