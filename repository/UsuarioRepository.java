package repository;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class UsuarioRepository {
    private List<Usuario> usuarios;

    public UsuarioRepository() {
        this.usuarios = new ArrayList<>();
    }

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    
    public Usuario getUsuarioPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdUsuario() == id) {
                return usuario;
            }
        }
        return null;
    }

    
    public Usuario getUsuarioPorNome(String nome) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
                return usuario;
            }
        }
        return null;
    }

    
    public List<Usuario> getAllUsuarios() {
        return usuarios;
    }

 
    public boolean existeUsuarioPorNome(String nome) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }
}
