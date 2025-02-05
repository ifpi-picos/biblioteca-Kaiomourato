package com.example.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import com.example.model.*;
import com.example.repository.*;
import com.example.service.notificar.*;

public class EmprestimoService {
    private EmprestimoRepository emprestimoRepository;
    private LivroRepository livroRepository;
    private CestaRepository cestaRepository;
    
    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.cestaRepository = new CestaRepository();
    }
    
    public Emprestimo realizarEmprestimo(Usuario usuario, int livroId, int prazoDias) {
        Livro livro = livroRepository.getLivroPorId(livroId);
        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return null;
        }
        if (!livro.getDisponivel()) {
            System.out.println("O livro não está disponível para empréstimo.");
            return null;
        }
        Emprestimo emprestimo = new Emprestimo(usuario, livro, prazoDias);
        emprestimoRepository.addEmprestimo(emprestimo);
        livroRepository.atualizarDisponibilidade(livro.getIdLivro(), false);
        usuario.adicionarEmprestimoAoHistorico(emprestimo);
        livro.setDisponivel(false);
        cestaRepository.adicionarLivroNaCesta(usuario, livro);
        Notificacao notificacao = new NotificarEmprestimo();
        notificacao.enviarEmail();
        return emprestimo;
    }
    
    public void realizarDevolucao(Emprestimo emprestimo) {
        if (emprestimo == null) {
            System.out.println("Empréstimo não encontrado.");
            return;
        }
        emprestimo.registrarDevolucao();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "UPDATE emprestimo SET devolvido = ?, data_devolucao_real = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, true);
                stmt.setDate(2, java.sql.Date.valueOf(emprestimo.getDataDevolucaoReal()));
                stmt.setInt(3, emprestimo.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar devolução: " + e.getMessage());
        }
        livroRepository.atualizarDisponibilidade(emprestimo.getLivro().getIdLivro(), true);
        cestaRepository.removerLivroDaCesta(emprestimo.getUsuario(), emprestimo.getLivro());
        Notificacao notificacao = new NotificarDevolucao();
        notificacao.enviarEmail();
    }
    
    public List<Emprestimo> getHistoricoEmprestimos(Usuario usuario) {
        return emprestimoRepository.getEmprestimosPorUsuario(usuario);
    }
}
