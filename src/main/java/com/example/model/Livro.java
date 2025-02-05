package com.example.model;

public class Livro {
    private int idLivro;
    private String titulo;
    private String autor;
    private String genero;
    private Integer anoPublicacao;
    private String idioma;
    private Boolean disponivel;

    
    public Livro(String titulo, String autor, String genero, Integer anoPublicacao, String idioma, Boolean disponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.idioma = idioma;
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Livro [ID: " + idLivro + ", Título: " + titulo + ", Autor: " + autor +
               ", Gênero: " + genero + ", Ano: " + anoPublicacao + ", Idioma: " + idioma +
               ", Disponível: " + (disponivel ? "Sim" : "Não") + "]";
    }

    // Getters e Setters
    public int getIdLivro() {
        return idLivro;
    }


    public void setIdLivro(int id) {
        this.idLivro = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }
    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public Boolean getDisponivel() {
        return disponivel;
    }
    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
}
