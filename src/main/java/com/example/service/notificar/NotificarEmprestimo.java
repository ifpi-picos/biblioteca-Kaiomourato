package com.example.service.notificar;

public class NotificarEmprestimo implements Notificacao {

    @Override
    public void enviarEmail() {
        System.out.println("Confimação de emprestimo enviada para o email");
    }
    
}
