

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.*;
import repository.*;
import service.*;

public class Main {

    private static Usuario usuarioLogado = null;  // Para armazenar o usuário logado
    private static UsuarioRepository usuarioRepository = new UsuarioRepository();
    private static LivroRepository livroRepository = new LivroRepository();
    private static EmprestimoRepository emprestimoRepository = new EmprestimoRepository();
    private static UsuarioService usuarioService = new UsuarioService(usuarioRepository);
    private static LivroService livroService = new LivroService(livroRepository);
    private static EmprestimoService emprestimoService = new EmprestimoService(emprestimoRepository, livroRepository);

    
    public static void main(String[] args) {
        inicializarLivrosPadrao();
        Scanner scanner = new Scanner(System.in);
        Usuario kaio = new Usuario("kaio", "kaiomourato@email", "(89) 99988", "123", null, null);
        usuarioService.adicionarUsuario(kaio);

        while (true) {
            if (usuarioLogado == null) {
                System.out.println("Bem-vindo ao Sistema da Biblioteca!");
                System.out.println("1. Criar Conta");
                System.out.println("2. Login");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consome a linha extra

                switch (opcao) {
                    case 1:
                        criarConta(scanner);
                        break;
                    case 2:
                        login(scanner);
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } else {
                menuUsuarioLogado(scanner);
            }
        }
    }

    public static void inicializarLivrosPadrao() {
        Livro livro1 = new Livro("1984", "George Orwell", "Distopia", 1949, "Inglês", true);
        Livro livro2 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "Fantasia", 1954, "Inglês", true);
        Livro livro3 = new Livro("Dom Casmurro", "Machado de Assis", "Literatura Brasileira", 1899, "Português", true);
        Livro livro4 = new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", "Infantil", 1943, "Francês", true);
        Livro livro5 = new Livro("A Arte da Guerra", "Sun Tzu", "Estratégia", -500, "Chinês", true);
        
        livroService.adicionarLivro(livro1);
        livroService.adicionarLivro(livro2);
        livroService.adicionarLivro(livro3);
        livroService.adicionarLivro(livro4);
        livroService.adicionarLivro(livro5);
    }
    // Método para criar conta
    public static void criarConta(Scanner scanner) {
        System.out.println("\n--- Criar Conta ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        // Cria um novo usuário
        Usuario usuario = new Usuario(nome, email, telefone, senha, new ArrayList<>(), new ArrayList<>());
        usuarioService.adicionarUsuario(usuario);
        System.out.println("Conta criada com sucesso!");
    }

    // Método para login
    public static void login(Scanner scanner) {
        System.out.println("\n--- Login ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        usuarioLogado = usuarioService.fazerLogin(nome, senha);
        if (usuarioLogado != null) {
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Usuário ou senha incorretos.");
        }
    }

    // Menu de funcionalidades após o login
    public static void menuUsuarioLogado(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Remover Livro");
            System.out.println("3. Buscar Livro");
            System.out.println("4. Ações (Pegar Livro Emprestado / Devolver)");
            System.out.println("5. Buscar Usuário");
            System.out.println("6. Exibir Informações do Usuário");
            System.out.println("7. Cesta de Livros");
            System.out.println("8. Histórico de Reservas");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a linha extra

            switch (opcao) {
                case 1:
                    adicionarLivro(scanner);
                    break;
                case 2:
                    removerLivro(scanner);
                    break;
                case 3:
                    buscarLivro(scanner);
                    break;
                case 4:
                    acaoEmprestimo(scanner);
                    break;
                case 5:
                    buscarUsuario(scanner);
                    break;
                case 6:
                    exibirInformacoesUsuario(scanner);
                    break;
                case 7:
                    exibirCestaDeLivros();
                    break;
                case 8:
                    exibirHistoricoDeReservas();
                    break;
                case 9:
                    usuarioLogado = null; // Sair e voltar para o menu inicial
                    System.out.println("Deslogado com sucesso!");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // Adicionar Livro
    public static void adicionarLivro(Scanner scanner) {
        System.out.println("\n--- Adicionar Livro ---");
    
        // Validação para o título
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        if (titulo.trim().isEmpty()) {
            System.out.println("Título não pode ser vazio!");
            return; 
        }
    
        // Validação para o autor
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        if (autor.trim().isEmpty()) {
            System.out.println("Autor não pode ser vazio!");
            return;  
        }
    
        // Validação para o gênero
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();
        if (genero.trim().isEmpty()) {
            System.out.println("Gênero não pode ser vazio!");
            return;  
        }
    
        // Validação para o ano de publicação
        int anoPublicacao = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Ano de Publicação: ");
                anoPublicacao = scanner.nextInt();
                scanner.nextLine(); // Consome a linha extra
                if (anoPublicacao <= 0) {
                    System.out.println("Ano de publicação deve ser um valor positivo.");
                } else {
                    entradaValida = true;
                }
            } catch (Exception e) {
                System.out.println("Por favor, insira um número válido para o ano de publicação.");
                scanner.nextLine();
            }
        }
    
        // Validação para o idioma
        System.out.print("Idioma: ");
        String idioma = scanner.nextLine();
        try {
            Integer.parseInt(idioma.trim());  
            System.out.println("Entrada Inválida! O idioma não pode ser um número.");
            return; 
        } catch (NumberFormatException e) {
            if (idioma.trim().isEmpty()) {
                System.out.println("Entrada Inválida! O idioma não pode ser vazio.");
                return; 
            }
        }
    
        
        Livro livro = new Livro(titulo, autor, genero, anoPublicacao, idioma, true);
        livroService.adicionarLivro(livro);
    }
    

    // Remover Livro
    public static void removerLivro(Scanner scanner) {
        System.out.println("\n--- Remover Livro ---");
        System.out.print("ID do Livro: ");
        int idLivro = scanner.nextInt();
        scanner.nextLine();

        boolean livroEncontrado = false;
        for (Livro livro : livroService.listarLivrosDisponiveis()) {  
            if (livro.getIdLivro() == idLivro) {
                livroEncontrado = true;
                break; 
            }
        }
    
        if (!livroEncontrado) {
            System.out.println("Erro: O livro com o ID " + idLivro + " não foi encontrado.");
            return; 
        }
    
        
        livroService.removerLivro(idLivro);
        System.out.println("Livro removido com sucesso!");
    }
    

    
    public static void buscarLivro(Scanner scanner) {
        System.out.println("\n--- Buscar Livro ---");
        System.out.println("1. Listar livros disponíveis");
        System.out.println("2. Buscar por Id ");
        System.out.println("3. Buscar por Autor");
        System.out.println("4. Buscar por Gênero");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                livroService.listarLivrosDisponiveis().forEach(System.out::println);
                break;
            case 2:
                System.out.print("ID do Livro: ");
                int idLivro = scanner.nextInt();
                scanner.nextLine(); // Consome a linha extra
                Livro livro = livroService.buscarLivroPorId(idLivro);
                if (livro != null) {
                    System.out.println(livro);
                } else {
                    System.out.println("Livro não encontrado.");
                }
                break;
            case 3:
                System.out.print("Autor: ");
                String autor = scanner.nextLine();
                livroService.buscarLivrosPorAutor(autor).forEach(System.out::println);
                break;
            case 4:
                System.out.print("Gênero: ");
                String genero = scanner.nextLine();
                livroService.buscarLivrosPorGenero(genero).forEach(System.out::println);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Ações: Pegar Livro Emprestado e Devolver Livro
    public static void acaoEmprestimo(Scanner scanner) {
        System.out.println("\n--- Ações ---");
        System.out.println("1. Pegar Livro Emprestado");
        System.out.println("2. Devolver Livro");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consome a linha extra

        switch (opcao) {
            case 1:
                pegarEmprestado(scanner, usuarioLogado);
                break;
            case 2:
                devolverLivro(scanner, usuarioLogado);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
    public static void pegarEmprestado(Scanner scanner, Usuario usuario) {
        System.out.println("\n--- Pegar Emprestado ---");
    
        // Listar os livros disponíveis
        List<Livro> livrosDisponiveis = livroService.listarLivrosDisponiveis();
        
        if (livrosDisponiveis.isEmpty()) {
            System.out.println("Não há livros disponíveis para empréstimo.");
            return; // Encerra o método se não houver livros disponíveis
        }
    
        // Exibir os livros disponíveis antes de solicitar o ID
        System.out.println("\n--- Livros Disponíveis ---");
        for (Livro livro : livrosDisponiveis) {
            System.out.println("ID: " + livro.getIdLivro() + " | Título: " + livro.getTitulo() + " | Autor: " + livro.getAutor());
        }
    
        // Solicitar o ID do livro para o empréstimo
        System.out.print("Digite o ID do livro que deseja pegar emprestado: ");
        int livroIdEscolhido = scanner.nextInt();
        scanner.nextLine(); // Consome a linha extra
    
        // Solicitar o número de dias para o empréstimo
        System.out.print("Quantos dias deseja pegar emprestado? ");
        int prazoDias = scanner.nextInt();
        scanner.nextLine(); // Consome a linha extra
    
        // Chamar o método de realizar o empréstimo
        Emprestimo emprestimo = emprestimoService.realizarEmprestimo(usuario, livroIdEscolhido, prazoDias);
        if (emprestimo != null) {
            System.out.println("Empréstimo realizado com sucesso!");
            System.out.println("Data de devolução: " + emprestimo.getDataDevolucaoPrevista());
        }
    }
    
    
    
    

    public static void devolverLivro(Scanner scanner, Usuario usuarioLogado) {
        System.out.println("\n--- Devolver Livro ---");
    
        // Verifique se a cesta do usuário não está vazia
        List<Livro> cesta = usuarioLogado.getLivrosNaCesta();
        if (cesta.isEmpty()) {
            System.out.println("Sua cesta está vazia. Não há livros para devolver.");
            return;
        }
    
        // Exiba a cesta com os livros para o usuário
        System.out.println("Escolha o livro a ser devolvido:");
        for (Livro livro : cesta) {
            System.out.println("ID: " + livro.getIdLivro() + " | Título: " + livro.getTitulo());
        }
    
        // Solicite ao usuário o ID do livro
        System.out.print("Digite o ID do livro que deseja devolver: ");
        int idLivro = scanner.nextInt();
        scanner.nextLine(); // Consome a linha extra
    
        // Procure o livro na cesta pelo ID
        Livro livroEscolhido = null;
        for (Livro livro : cesta) {
            if (livro.getIdLivro() == idLivro) {
                livroEscolhido = livro;
                break;
            }
        }
    
        // Verifique se o livro foi encontrado
        if (livroEscolhido == null) {
            System.out.println("Livro não encontrado na sua cesta. Tente novamente.");
            return;
        }
    
        // Agora que temos o livro, busque o empréstimo associado ao ID do livro
        Emprestimo emprestimo = emprestimoRepository.getEmprestimoPorIdLivro(idLivro); // Alterado para buscar pelo ID do livro
        if (emprestimo != null) {
            // Realize a devolução do livro
            emprestimoService.realizarDevolucao(emprestimo);
            // Remova o livro da cesta do usuário após a devolução
            usuarioLogado.getLivrosNaCesta().remove(livroEscolhido);
            System.out.println("Livro devolvido com sucesso!");
        } else {
            System.out.println("Empréstimo não encontrado para o livro selecionado.");
        }
    }
    


    // Buscar Usuário
    public static void buscarUsuario(Scanner scanner) {
        System.out.println("\n--- Buscar Usuário ---");
        System.out.println("1. Buscar por Nome");
        System.out.println("2. Buscar por ID");
        System.out.println("3. Listar Todos os Usuários");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consome a linha extra

        switch (opcao) {
            case 1:
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                Usuario usuario = usuarioService.buscarUsuarioPorNome(nome);
                if (usuario != null) {
                    System.out.println(usuario);
                } else {
                    System.out.println("Usuário não encontrado.");
                }
                break;
            case 2:
                System.out.print("ID: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consome a linha extra
                usuario = usuarioService.buscarUsuarioPorId(id);
                if (usuario != null) {
                    System.out.println(usuario);
                } else {
                    System.out.println("Usuário não encontrado.");
                }
                break;
            case 3:
                usuarioService.listarTodosUsuarios().forEach(System.out::println);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Exibir informações do usuário
    public static void exibirInformacoesUsuario(Scanner scanner) {
        System.out.println(usuarioLogado);
    }

    // Exibir Cesta de Livros
    public static void exibirCestaDeLivros() {
        if (usuarioLogado.getLivrosNaCesta().isEmpty()) {
            System.out.println("Sua cesta de livros está vazia.");
        } else {
            System.out.println("\nSua Cesta de Livros:");
            usuarioLogado.getLivrosNaCesta().forEach(System.out::println);
        }
    }

    // Exibir Histórico de Reservas
    public static void exibirHistoricoDeReservas() {
        if (usuarioLogado.getHistoricoReservas().isEmpty()) {
            System.out.println("Você ainda não tem histórico de reservas.");
        } else {
            System.out.println("\nSeu Histórico de Reservas:");
            usuarioLogado.getHistoricoReservas().forEach(System.out::println);
        }
    }
}
