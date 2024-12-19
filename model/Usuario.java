package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private static int contadorIdUsuario = 0;
    private Integer idUsuario;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    public List<Emprestimo> historicoReservas;
    public List<Livro> livrosNaCesta;

    // CONSTRUTOR COM PARÂMETROS
    public Usuario(String nome, String email,String telefone, String senha, List<Emprestimo> historicoReservas, List<Livro> livrosNaCesta) {
        this.idUsuario = gerarIdUsuario();
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.historicoReservas = historicoReservas != null ? historicoReservas : new ArrayList<>();
        this.livrosNaCesta = livrosNaCesta != null ? livrosNaCesta : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Usuario [ID: " + idUsuario + ", Nome: " + nome + ", email: " + email +
               ", Telefone: " + telefone + "]";
    }


    // GET & SET

    public Integer getIdUsuario() {return idUsuario;}
    private int gerarIdUsuario(){
        contadorIdUsuario++;
        return contadorIdUsuario;
    }

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public String getSenha() {return senha;}
    public void setSenha(String senha) {this.senha = senha;}

    public List<Emprestimo> getHistoricoReservas() {return historicoReservas;}
    public void setHistoricoReservas(List<Emprestimo> historicoReservas) {this.historicoReservas = historicoReservas;}

    public List<Livro> getLivrosNaCesta() {return livrosNaCesta;}
    public void setLivrosNaCesta(List<Livro> livrosNaCesta) {this.livrosNaCesta = livrosNaCesta;}

    // METODOS
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
