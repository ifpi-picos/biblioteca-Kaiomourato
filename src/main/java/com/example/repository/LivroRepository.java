package com.example.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.model.Livro;
import com.example.service.ConnectionFactory;

public class LivroRepository {

    
    public void addLivro(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autor, genero, ano_publicacao, idioma, disponivel) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setObject(4, livro.getAnoPublicacao());
            stmt.setString(5, livro.getIdioma());
            stmt.setBoolean(6, livro.getDisponivel());
            
            stmt.executeUpdate();
            
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    livro.setIdLivro(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar livro: " + e.getMessage());
        }
    }

   
    public void removeLivro(Livro livro) {
        String sql = "DELETE FROM livro WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, livro.getIdLivro());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao remover livro: " + e.getMessage());
        }
    }

    
    public List<Livro> getAllLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                livros.add(mapLivro(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livros: " + e.getMessage());
        }
        return livros;
    }

    
    public Livro getLivroPorId(int id) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapLivro(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro: " + e.getMessage());
        }
        return null;
    }

    
    public List<Livro> getLivrosDisponiveis() {
        List<Livro> livrosDisponiveis = new ArrayList<>();
        String sql = "SELECT * FROM livro WHERE disponivel = true";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                livrosDisponiveis.add(mapLivro(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livros disponíveis: " + e.getMessage());
        }
        return livrosDisponiveis;
    }

    
    public List<Livro> getLivrosPorAutor(String autor) {
        List<Livro> livrosPorAutor = new ArrayList<>();
        String sql = "SELECT * FROM livro WHERE LOWER(autor) = LOWER(?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, autor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    livrosPorAutor.add(mapLivro(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livros por autor: " + e.getMessage());
        }
        return livrosPorAutor;
    }

    
    public List<Livro> getLivrosPorGenero(String genero) {
        List<Livro> livrosPorGenero = new ArrayList<>();
        String sql = "SELECT * FROM livro WHERE LOWER(genero) = LOWER(?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genero);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    livrosPorGenero.add(mapLivro(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livros por gênero: " + e.getMessage());
        }
        return livrosPorGenero;
    }

    
    private Livro mapLivro(ResultSet rs) throws SQLException {
        Livro livro = new Livro(
            rs.getString("titulo"),
            rs.getString("autor"),
            rs.getString("genero"),
            rs.getInt("ano_publicacao"),
            rs.getString("idioma"),
            rs.getBoolean("disponivel")
        );
        livro.setIdLivro(rs.getInt("id"));
        return livro;
    }

    public void atualizarDisponibilidade(int id, boolean disponivel) {
        String sql = "UPDATE livro SET disponivel = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setBoolean(1, disponivel);
             stmt.setInt(2, id);
             stmt.executeUpdate();
        } catch (SQLException e) {
             System.err.println("Erro ao atualizar disponibilidade: " + e.getMessage());
        }
    }
    
}
