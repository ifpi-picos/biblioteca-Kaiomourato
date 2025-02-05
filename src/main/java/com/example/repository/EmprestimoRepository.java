package com.example.repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.example.model.*;
import com.example.service.ConnectionFactory;

public class EmprestimoRepository {

    public void addEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (livro_id, usuario_id, data_devolucao_prevista, devolvido) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, emprestimo.getLivro().getIdLivro());
            stmt.setInt(2, emprestimo.getUsuario().getIdUsuario());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataDevolucaoPrevista()));
            stmt.setBoolean(4, emprestimo.getDevolvido());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    emprestimo.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar empréstimo: " + e.getMessage());
        }
    }

    public List<Emprestimo> getAllEmprestimos() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                emprestimos.add(mapEmprestimo(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar empréstimos: " + e.getMessage());
        }
        return emprestimos;
    }

    public List<Emprestimo> getEmprestimosPorUsuario(Usuario usuario) {
        List<Emprestimo> emprestimosUsuario = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo WHERE usuario_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getIdUsuario());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    emprestimosUsuario.add(mapEmprestimo(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar empréstimos por usuário: " + e.getMessage());
        }
        return emprestimosUsuario;
    }

    public Emprestimo getEmprestimoPorIdLivro(int idLivro) {
        String sql = "SELECT * FROM emprestimo WHERE livro_id = ? AND devolvido = false";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLivro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapEmprestimo(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar empréstimo por ID do livro: " + e.getMessage());
        }
        return null;
    }

    private Emprestimo mapEmprestimo(ResultSet rs) throws SQLException {
        int livroId = rs.getInt("livro_id");
        int usuarioId = rs.getInt("usuario_id");

        
        Livro livro = new Livro("", "", "", 0, "", true);
        livro.setIdLivro(livroId);
        Usuario usuario = new Usuario("", "", "", "", null, null);
        usuario.setIdUsuario(usuarioId);

        LocalDate dataEmprestimo = rs.getDate("data_emprestimo").toLocalDate();
        LocalDate dataDevolucaoPrevista = rs.getDate("data_devolucao_prevista").toLocalDate();
        int prazoDias = (int) java.time.temporal.ChronoUnit.DAYS.between(dataEmprestimo, dataDevolucaoPrevista);
        Emprestimo emprestimo = new Emprestimo(usuario, livro, prazoDias);
        emprestimo.setId(rs.getInt("id"));
        emprestimo.setDevolvido(rs.getBoolean("devolvido"));
        Date dataReal = rs.getDate("data_devolucao_real");
        if (dataReal != null) {
            emprestimo.setDataDevolucaoReal(dataReal.toLocalDate());
        }
        return emprestimo;
    }
}
