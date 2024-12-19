package repository;

import java.util.ArrayList;
import java.util.List;

import model.*;

public class EmprestimoRepository {
    private List<Emprestimo> emprestimos;

    public EmprestimoRepository() {
        this.emprestimos = new ArrayList<>();
    }

    
    public void addEmprestimo(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }


    public List<Emprestimo> getAllEmprestimos() {
        return emprestimos;
    }


    public List<Emprestimo> getEmprestimosPorUsuario(Usuario usuario) {
        List<Emprestimo> emprestimosUsuario = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getUsuario().equals(usuario)) {
                emprestimosUsuario.add(emprestimo);
            }
        }
        return emprestimosUsuario;
    }

    
    public Emprestimo getEmprestimoPorIdLivro(int idLivro) {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getLivro().getIdLivro() == idLivro) {
                return emprestimo;
            }
        }
        return null;  
    }
}
