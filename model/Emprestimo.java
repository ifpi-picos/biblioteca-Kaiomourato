package model;

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

    private static int contadorId = 0;
    private static final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Construtor
    public Emprestimo(Usuario usuario, Livro livro, int prazoDias) {
        this.id = gerarId(); 
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = LocalDate.now(); 
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(prazoDias);
        this.devolvido = false;
    }

    @Override
    public String toString() {
        return "Livro: " + livro.getTitulo() + " | Data de Empréstimo: " + dataEmprestimo + " | Data de Devolução: " + dataDevolucaoPrevista;
    }

    private int gerarId() {
        contadorId++;
        return contadorId;
    }

    // Getters e Setters
    public int getId() {
        return id;
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

    public Boolean getDevolvido() {
        return devolvido;
    }

    public void setDevolvido(Boolean devolvido) {
        this.devolvido = devolvido;
    }

    // Métodos Específicos

    public void registrarDevolucao() {
        this.dataDevolucaoReal = LocalDate.now();
        this.devolvido = true;
        this.livro.setDisponivel(true); 
    }

    public void exibirInformacoes() {
        System.out.println("ID do Empréstimo: " + id);
        System.out.println("Usuário: " + usuario.getNome());
        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Data do Empréstimo: " + dataEmprestimo.format(formatoData));
        System.out.println("Data Prevista de Devolução: " + dataDevolucaoPrevista.format(formatoData));
        if (devolvido) {
            System.out.println("Data Real de Devolução: " + dataDevolucaoReal.format(formatoData));
            System.out.println("Status: Devolvido");
        } else {
            System.out.println("Status: Em andamento");
        }
    }
}
