package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.model.Emprestimo;
import com.example.model.Livro;
import com.example.model.Usuario;
import com.example.repository.EmprestimoRepository;
import com.example.repository.LivroRepository;
import com.example.repository.UsuarioRepository;
import com.example.service.EmprestimoService;
import com.example.service.LivroService;
import com.example.service.UsuarioService;

public class App {

    private static Usuario usuarioLogado = null;
    private static UsuarioService usuarioService = new UsuarioService(new UsuarioRepository());
    private static LivroService livroService = new LivroService(new LivroRepository());
    private static EmprestimoService emprestimoService = new EmprestimoService(new EmprestimoRepository(), new LivroRepository());

    public static void main(String[] args) {
        try {
            Connection conexao = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/biblioteca", 
                    "postgres", 
                    "KMMoura555!"
            );

            if (conexao != null) {
                System.out.println("BD conectado ==========================");

                Scanner scanner = new Scanner(System.in);
                try {
                    Usuario kaio = new Usuario(
                            "kaio", 
                            "kaiomourato@email.com",
                            "(89) 99988", 
                            "123", 
                            new ArrayList<>(), 
                            new ArrayList<>()
                    );
                    usuarioService.adicionarUsuario(kaio);
                } catch(Exception e) {
                    System.out.println("Usuário padrão já existe ou houve erro na criação.");
                }

                while (true) {
                    if (usuarioLogado == null) {
                        exibirMenuInicial();
                        int opcao = lerInteiro(scanner, "Escolha uma opção: ");
                        switch(opcao) {
                            case 1:
                                criarConta(scanner);
                                break;
                            case 2:
                                login(scanner);
                                break;
                            case 0:
                                System.out.println("Saindo... Até logo!");
                                System.exit(0);
                            default:
                                System.out.println("Opção inválida. Tente novamente.");
                        }
                    } else {
                        exibirMenuUsuario();
                        int opcao = lerInteiro(scanner, "Escolha uma opção: ");
                        switch(opcao) {
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
                                exibirInformacoesUsuario();
                                break;
                            case 7:
                                exibirCestaDeLivros();
                                break;
                            case 8:
                                exibirHistoricoDeReservas();
                                break;
                            case 9:
                                usuarioLogado = null;
                                System.out.println("Deslogado com sucesso!");
                                break;
                            case 0:
                                System.out.println("Saindo... Até logo!");
                                System.exit(0);
                            default:
                                System.out.println("Opção inválida. Tente novamente.");
                        }
                    }
                }
            } else {
                System.out.println("Erro de conexão =======================");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private static void exibirMenuInicial() {
        System.out.println("\n=======================================");
        System.out.println("         BIBLIOTECA DIGITAL            ");
        System.out.println("=======================================");
        System.out.println("1. Criar Conta");
        System.out.println("2. Login");
        System.out.println("0. Sair");
        System.out.println("=======================================");
    }

    private static void exibirMenuUsuario() {
        System.out.println("\n=======================================");
        System.out.println("      MENU DO USUÁRIO LOGADO           ");
        System.out.println("=======================================");
        System.out.println("1. Adicionar Livro");
        System.out.println("2. Remover Livro");
        System.out.println("3. Buscar Livro");
        System.out.println("4. Ações (Pegar/Devolver Livro)");
        System.out.println("5. Buscar Usuário");
        System.out.println("6. Exibir Informações do Usuário");
        System.out.println("7. Exibir Cesta de Livros");
        System.out.println("8. Exibir Histórico de Reservas");
        System.out.println("9. Deslogar");
        System.out.println("0. Sair");
        System.out.println("=======================================");
    }


    private static int lerInteiro(Scanner scanner, String mensagem) {
        int valor = -1;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensagem);
            try {
                valor = Integer.parseInt(scanner.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
            }
        }
        return valor;
    }

    

    
    private static void criarConta(Scanner scanner) {
        System.out.println("\n--- Criar Conta ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        Usuario usuario = new Usuario(nome, email, telefone, senha, new ArrayList<>(), new ArrayList<>());
        try {
            usuarioService.adicionarUsuario(usuario);
            System.out.println("Conta criada com sucesso!");
            usuarioLogado = usuario;  // Login automático após criar a conta
        } catch (Exception e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
        }
    }

    
    private static void login(Scanner scanner) {
        System.out.println("\n--- Login ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        usuarioLogado = usuarioService.fazerLogin(nome, senha);
        if (usuarioLogado != null) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Usuário ou senha incorretos.");
        }
    }

   
    private static void adicionarLivro(Scanner scanner) {
        System.out.println("\n--- Adicionar Livro ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();
        int anoPublicacao = lerInteiro(scanner, "Ano de Publicação: ");
        System.out.print("Idioma: ");
        String idioma = scanner.nextLine();
        Livro livro = new Livro(titulo, autor, genero, anoPublicacao, idioma, true);
        try {
            livroService.adicionarLivro(livro);
            System.out.println("Livro adicionado com sucesso!");
        } catch(Exception e) {
            System.out.println("Erro ao adicionar livro: " + e.getMessage());
        }
    }

   
    private static void removerLivro(Scanner scanner) {
        System.out.println("\n--- Remover Livro ---");
        int id = lerInteiro(scanner, "Digite o ID do livro a ser removido: ");
        try {
            livroService.removerLivro(id);
            System.out.println("Livro removido com sucesso!");
        } catch(Exception e) {
            System.out.println("Erro ao remover livro: " + e.getMessage());
        }
    }

    
    private static void buscarLivro(Scanner scanner) {
        System.out.println("\n--- Buscar Livro ---");
        System.out.println("1. Listar livros disponíveis");
        System.out.println("2. Buscar por ID");
        System.out.println("3. Buscar por Autor");
        System.out.println("4. Buscar por Gênero");
        int opcao = lerInteiro(scanner, "Escolha uma opção: ");
        switch(opcao) {
            case 1:
                List<Livro> disponiveis = livroService.listarLivrosDisponiveis();
                if (disponiveis.isEmpty()) {
                    System.out.println("Nenhum livro disponível.");
                } else {
                    disponiveis.forEach(System.out::println);
                }
                break;
            case 2:
                int id = lerInteiro(scanner, "Digite o ID do livro: ");
                try {
                    Livro livro = livroService.buscarLivroPorId(id);
                    System.out.println(livro);
                } catch(Exception e) {
                    System.out.println("Livro não encontrado.");
                }
                break;
            case 3:
                System.out.print("Digite o autor: ");
                String autor = scanner.nextLine();
                List<Livro> livrosPorAutor = livroService.buscarLivrosPorAutor(autor);
                if (livrosPorAutor.isEmpty()) {
                    System.out.println("Nenhum livro encontrado para o autor informado.");
                } else {
                    livrosPorAutor.forEach(System.out::println);
                }
                break;
            case 4:
                System.out.print("Digite o gênero: ");
                String genero = scanner.nextLine();
                List<Livro> livrosPorGenero = livroService.buscarLivrosPorGenero(genero);
                if (livrosPorGenero.isEmpty()) {
                    System.out.println("Nenhum livro encontrado para o gênero informado.");
                } else {
                    livrosPorGenero.forEach(System.out::println);
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    
    private static void acaoEmprestimo(Scanner scanner) {
        System.out.println("\n--- Ações de Empréstimo ---");
        System.out.println("1. Pegar Livro Emprestado");
        System.out.println("2. Devolver Livro");
        int opcao = lerInteiro(scanner, "Escolha uma opção: ");
        switch(opcao) {
            case 1:
                pegarEmprestado(scanner);
                break;
            case 2:
                devolverLivro(scanner);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    
    private static void pegarEmprestado(Scanner scanner) {
        System.out.println("\n--- Pegar Livro Emprestado ---");
        List<Livro> disponiveis = livroService.listarLivrosDisponiveis();
        if (disponiveis.isEmpty()) {
            System.out.println("Não há livros disponíveis para empréstimo.");
            return;
        }
        System.out.println("Livros disponíveis:");
        disponiveis.forEach(l -> System.out.println("ID: " + l.getIdLivro() + " | Título: " + l.getTitulo()));
        int livroId = lerInteiro(scanner, "Digite o ID do livro desejado: ");
        int prazo = lerInteiro(scanner, "Digite o prazo (em dias) para o empréstimo: ");
        Emprestimo emprestimo = emprestimoService.realizarEmprestimo(usuarioLogado, livroId, prazo);
        if (emprestimo != null) {
            System.out.println("Empréstimo realizado com sucesso!");
            System.out.println("Data prevista de devolução: " + emprestimo.getDataDevolucaoPrevista());
        }
    }

   
    private static void devolverLivro(Scanner scanner) {
        System.out.println("\n--- Devolver Livro ---");
        if (usuarioLogado.getLivrosNaCesta().isEmpty()) {
            System.out.println("Sua cesta está vazia, não há livros para devolver.");
            return;
        }
        System.out.println("Livros na sua cesta:");
        usuarioLogado.getLivrosNaCesta().forEach(l -> System.out.println("ID: " + l.getIdLivro() + " | Título: " + l.getTitulo()));
        int livroId = lerInteiro(scanner, "Digite o ID do livro que deseja devolver: ");
        Emprestimo emprestimo = emprestimoService.getHistoricoEmprestimos(usuarioLogado)
                .stream()
                .filter(e -> e.getLivro().getIdLivro() == livroId && !e.getDevolvido())
                .findFirst().orElse(null);
        if (emprestimo != null) {
            emprestimoService.realizarDevolucao(emprestimo);
            usuarioLogado.removerLivroDaCesta(emprestimo.getLivro());
            System.out.println("Livro devolvido com sucesso!");
        } else {
            System.out.println("Empréstimo não encontrado para o livro informado.");
        }
    }

    
    private static void buscarUsuario(Scanner scanner) {
        System.out.println("\n--- Buscar Usuário ---");
        System.out.println("1. Buscar por Nome");
        System.out.println("2. Buscar por ID");
        System.out.println("3. Listar Todos os Usuários");
        int opcao = lerInteiro(scanner, "Escolha uma opção: ");
        switch(opcao) {
            case 1:
                System.out.print("Digite o nome do usuário: ");
                String nome = scanner.nextLine();
                try {
                    Usuario usuario = usuarioService.buscarUsuarioPorNome(nome);
                    System.out.println(usuario);
                } catch(Exception e) {
                    System.out.println("Usuário não encontrado.");
                }
                break;
            case 2:
                int id = lerInteiro(scanner, "Digite o ID do usuário: ");
                try {
                    Usuario usuario = usuarioService.buscarUsuarioPorId(id);
                    System.out.println(usuario);
                } catch(Exception e) {
                    System.out.println("Usuário não encontrado.");
                }
                break;
            case 3:
                List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
                if (usuarios.isEmpty()) {
                    System.out.println("Nenhum usuário cadastrado.");
                } else {
                    usuarios.forEach(System.out::println);
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    
    private static void exibirInformacoesUsuario() {
        System.out.println("\n--- Informações do Usuário ---");
        System.out.println(usuarioLogado);
    }

    
    private static void exibirCestaDeLivros() {
        System.out.println("\n--- Cesta de Livros ---");
        if (usuarioLogado.getLivrosNaCesta().isEmpty()) {
            System.out.println("Sua cesta está vazia.");
        } else {
            usuarioLogado.getLivrosNaCesta().forEach(System.out::println);
        }
    }

    
    private static void exibirHistoricoDeReservas() {
        System.out.println("\n--- Histórico de Reservas ---");
        List<Emprestimo> historico = emprestimoService.getHistoricoEmprestimos(usuarioLogado);
        if (historico.isEmpty()) {
            System.out.println("Você não possui histórico de reservas.");
        } else {
            historico.forEach(System.out::println);
        }
    }
}
