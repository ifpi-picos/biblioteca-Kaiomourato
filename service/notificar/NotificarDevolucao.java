package service.notificar;

public class NotificarDevolucao implements Notificacao {

    @Override
    public void enviarEmail() {
       System.out.println("Confimação de devolução enviada para o email");
    }
    
    
}
