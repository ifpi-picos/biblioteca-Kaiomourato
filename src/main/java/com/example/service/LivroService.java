package com.example.service;

import java.util.List;
import com.example.model.Livro;
import com.example.repository.*;

public class LivroService {
    private LivroRepository livroRepository;
    
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }
    
    public void adicionarLivro(Livro livro) {
        if (livro == null) {
            throw new IllegalArgumentException("O livro não pode ser nulo.");
        }
        if (livro.getIdLivro() != 0 && livroRepository.getLivroPorId(livro.getIdLivro()) != null) {
            throw new IllegalArgumentException("Já existe um livro com este ID.");
        }
        livroRepository.addLivro(livro);
        System.out.println("Livro adicionado com sucesso!");
    }
    
    public void removerLivro(int id) {
        Livro livro = livroRepository.getLivroPorId(id);
        if (livro == null) {
            throw new IllegalArgumentException("Livro com ID " + id + " não encontrado.");
        }
        livroRepository.removeLivro(livro);
        System.out.println("Livro removido com sucesso!");
    }
    
    public List<Livro> listarTodosLivros() {
        return livroRepository.getAllLivros();
    }
    
    public Livro buscarLivroPorId(int id) {
        Livro livro = livroRepository.getLivroPorId(id);
        if (livro == null) {
            throw new IllegalArgumentException("Livro com ID " + id + " não encontrado.");
        }
        return livro;
    }
    
    public List<Livro> listarLivrosDisponiveis() {
        return livroRepository.getLivrosDisponiveis();
    }
    
    public List<Livro> buscarLivrosPorAutor(String autor) {
        if (autor == null || autor.isEmpty()) {
            throw new IllegalArgumentException("O autor não pode ser nulo ou vazio.");
        }
        return livroRepository.getLivrosPorAutor(autor);
    }
    
    public List<Livro> buscarLivrosPorGenero(String genero) {
        if (genero == null || genero.isEmpty()) {
            throw new IllegalArgumentException("O gênero não pode ser nulo ou vazio.");
        }
        return livroRepository.getLivrosPorGenero(genero);
    }
    
    public void alterarDisponibilidadeLivro(int id, boolean disponivel) {
        Livro livro = livroRepository.getLivroPorId(id);
        if (livro == null) {
            throw new IllegalArgumentException("Livro com ID " + id + " não encontrado.");
        }
        livroRepository.atualizarDisponibilidade(id, disponivel);
        System.out.println("Disponibilidade do livro alterada com sucesso!");
    }
}
