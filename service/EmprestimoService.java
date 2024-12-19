package service;

import model.Emprestimo;
import model.Livro;
import model.Usuario;
import repository.EmprestimoRepository;
import repository.LivroRepository;

import java.util.List;

public class EmprestimoService {
    private EmprestimoRepository emprestimoRepository;
    private LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
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

    
    usuario.getHistoricoReservas().add(emprestimo);

    
    livro.setDisponivel(false);

    usuario.adicionarLivroNaCesta(livro);

    return emprestimo;
}


    
    public void realizarDevolucao(Emprestimo emprestimo) {
        if (emprestimo == null) {
            System.out.println("Empréstimo não encontrado.");
            return;
        }

     
        emprestimo.getLivro().setDisponivel(true);

        
        emprestimo.setDevolvido(true);

    }

    
    public List<Emprestimo> getHistoricoEmprestimos(Usuario usuario) {
        return usuario.getHistoricoReservas(); 
    }

    
    public List<Emprestimo> getEmprestimosPorUsuario(Usuario usuario) {
        return emprestimoRepository.getEmprestimosPorUsuario(usuario); 
    }
}
