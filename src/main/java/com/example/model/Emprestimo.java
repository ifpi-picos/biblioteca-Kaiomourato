package com.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Emprestimo {
    private int id; 
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private Boolean devolvido;
    
    
    public Emprestimo(Usuario usuario, Livro livro, int prazoDias) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(prazoDias);
        this.devolvido = false;
    }
    
    @Override
    public String toString() {
        return "Livro: " + livro.getTitulo() + " | Data de Empréstimo: " + dataEmprestimo +
               " | Data de Devolução: " + dataDevolucaoPrevista;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Livro getLivro() {
        return livro;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }
    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }
    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }
    public Boolean getDevolvido() {
        return devolvido;
    }
    public void setDevolvido(Boolean devolvido) {
        this.devolvido = devolvido;
    }
    
    public void registrarDevolucao() {
        this.dataDevolucaoReal = LocalDate.now();
        this.devolvido = true;
        this.livro.setDisponivel(true);
    }
    
    public void exibirInformacoes() {
        System.out.println("ID do Empréstimo: " + id);
        System.out.println("Usuário: " + usuario.getNome());
        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Data do Empréstimo: " + dataEmprestimo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Data Prevista de Devolução: " + dataDevolucaoPrevista.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        if (devolvido) {
            System.out.println("Data Real de Devolução: " + dataDevolucaoReal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Status: Devolvido");
        } else {
            System.out.println("Status: Em andamento");
        }
    }
}
