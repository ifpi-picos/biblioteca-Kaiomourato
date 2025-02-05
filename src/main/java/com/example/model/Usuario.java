package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int idUsuario; 
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    public List<Emprestimo> historicoReservas;
    public List<Livro> livrosNaCesta;

    
    public Usuario(String nome, String email, String telefone, String senha, List<Emprestimo> historicoReservas, List<Livro> livrosNaCesta) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        
        this.historicoReservas = historicoReservas != null ? historicoReservas : new ArrayList<>();
        this.livrosNaCesta = livrosNaCesta != null ? livrosNaCesta : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Usuario [ID: " + idUsuario + ", Nome: " + nome + ", Email: " + email +
               ", Telefone: " + telefone + "]";
    }

   
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int id) {
        this.idUsuario = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public List<Emprestimo> getHistoricoReservas() {
        return historicoReservas;
    }
    public void setHistoricoReservas(List<Emprestimo> historicoReservas) {
        this.historicoReservas = historicoReservas;
    }
    public List<Livro> getLivrosNaCesta() {
        return livrosNaCesta;
    }
    public void setLivrosNaCesta(List<Livro> livrosNaCesta) {
        this.livrosNaCesta = livrosNaCesta;
    }

    // Métodos para manipular a cesta e o histórico
    public void adicionarLivroNaCesta(Livro livro){
        if (livro != null && !livrosNaCesta.contains(livro)){
            livrosNaCesta.add(livro);
        }
    }

    public void removerLivroDaCesta(Livro livro){
        livrosNaCesta.remove(livro);
    }

    public void adicionarEmprestimoAoHistorico(Emprestimo emprestimo){
        if (emprestimo != null){
            historicoReservas.add(emprestimo);
        }
    }

    public void exibirHistorico(){
        System.out.println("Histórico de Reservas:");
        for(Emprestimo emprestimo : historicoReservas){
            System.out.println(emprestimo);
        }
    }
}
