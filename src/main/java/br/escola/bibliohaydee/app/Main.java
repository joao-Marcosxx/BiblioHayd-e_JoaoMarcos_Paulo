package br.escola.bibliohaydee.app;

import br.escola.bibliohaydee.model.Autor;
import br.escola.bibliohaydee.model.Emprestimo;
import br.escola.bibliohaydee.model.Livro;
import br.escola.bibliohaydee.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<Autor> autores = new ArrayList<>();
        List<Livro> livros = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();
        List<Emprestimo> emprestimos = new ArrayList<>();

        Autor machado = criarAutor("Machado de Assis", "Brasileira", 1839);
        Autor graciliano = criarAutor("Graciliano Ramos", "Brasileira", 1892);
        Autor aluisio = criarAutor("Aluísio Azevedo", "Brasileira", 1857);
        Autor jorge = criarAutor("Jorge Amado", "Brasileira", 1912);

        autores.addAll(List.of(machado, graciliano, aluisio, jorge));

        livros.add(new Livro("Dom Casmurro", "978-85-359-0277-5", machado, 1899));
        livros.add(new Livro("Memórias Póstumas de Brás Cubas", "978-85-359-0278-2", machado, 1881));
        livros.add(new Livro("Quincas Borba", "ISBN-0003", machado, 1891));
        livros.add(new Livro("Vidas Secas", "ISBN-0004", graciliano, 1938));
        livros.add(new Livro("O Cortiço", "ISBN-0005", aluisio, 1890));
        livros.add(new Livro("Capitães da Areia", "ISBN-0006", jorge, 1937));

        Scanner entrada = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== BIBLIOHAYDEE =====");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Atualizar usuário");
            System.out.println("4 - Pesquisar livro por título");
            System.out.println("5 - Pesquisar livro por autor");
            System.out.println("6 - Gerar relatório do acervo");
            System.out.println("7 - Registrar empréstimo");
            System.out.println("8 - Listar empréstimos");
            System.out.println("0 - Sair");

            opcao = lerInteiro(entrada, "Opção: ");

            switch (opcao) {
                case 1 -> cadastrarUsuario(entrada, usuarios);
                case 2 -> listarUsuarios(usuarios);
                case 3 -> atualizarUsuario(entrada, usuarios);
                case 4 -> pesquisarPorTitulo(livros, entrada);
                case 5 -> pesquisarPorAutor(livros, entrada);
                case 6 -> gerarRelatorio(livros);
                case 7 -> registrarEmprestimo(entrada, livros, usuarios, emprestimos);
                case 8 -> listarEmprestimos(emprestimos);
                case 0 -> System.out.println("Encerrando o sistema.");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        entrada.close();
    }

    private static Autor criarAutor(String nome, String nacionalidade, int anoNascimento) {
        return new Autor(nome, nacionalidade, anoNascimento);
    }

    private static void listarLivros(List<Livro> livros) {
        if (livros.isEmpty()) {
            System.out.println("O acervo está vazio.");
            return;
        }
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    private static void cadastrarUsuario(Scanner entrada, List<Usuario> usuarios) {
        String nome;
        do {
            System.out.print("Nome: ");
            nome = entrada.nextLine();
            if (!Usuario.nomeValido(nome)) {
                System.out.println("Nome inválido: informe pelo menos um caractere.");
            }
        } while (!Usuario.nomeValido(nome));

        System.out.print("Matrícula: ");
        String matricula = entrada.nextLine().trim();

        String tipo;
        do {
            System.out.print("Tipo (ALUNO ou PROFESSOR): ");
            tipo = entrada.nextLine().trim().toUpperCase();
            if (!Usuario.tipoValido(tipo)) {
                System.out.println("Tipo inválido: digite ALUNO ou PROFESSOR.");
            }
        } while (!Usuario.tipoValido(tipo));

        System.out.print("Definir o limite? (S/N): ");
        String resposta = entrada.nextLine().trim();

        Usuario usuario;
        if (resposta.equalsIgnoreCase("S")) {
            int limite;
            do {
                limite = lerInteiro(entrada, "Limite de empréstimos: ");
                if (!Usuario.limiteValido(limite)) {
                    System.out.println("Limite inválido: use 1 ou mais.");
                }
            } while (!Usuario.limiteValido(limite));
            usuario = new Usuario(nome, matricula, tipo, limite);
        } else {
            usuario = new Usuario(nome, matricula, tipo);
        }

        usuarios.add(usuario);
        System.out.println("Usuário cadastrado: " + usuario);
    }

    private static void listarUsuarios(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private static Usuario buscarUsuario(List<Usuario> usuarios, String matricula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getMatricula().equals(matricula)) {
                return usuario;
            }
        }
        return null;
    }

    private static Livro buscarLivroPorIsbn(List<Livro> livros, String isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equalsIgnoreCase(isbn)) {
                return livro;
            }
        }
        return null;
    }
    private static void atualizarUsuario(Scanner entrada, List<Usuario> usuarios) {
        System.out.print("Matrícula: ");
        Usuario usuario = buscarUsuario(usuarios, entrada.nextLine().trim());

        if (usuario == null) {
            System.out.println("Usuário não localizado. Nenhuma alteração feita.");
            return;
        }

        System.out.println("Encontrado: " + usuario);
        System.out.println("1 - Alterar nome");
        System.out.println("2 - Alterar limite");
        String opcao = entrada.nextLine().trim();

        if (opcao.equals("1")) {
            System.out.print("Novo nome: ");
            if (usuario.setNome(entrada.nextLine())) {
                System.out.println("Nome atualizado: " + usuario);
            } else {
                System.out.println("Nome inválido. Nenhuma alteração feita.");
            }
        } else if (opcao.equals("2")) {
            int novoLimite = lerInteiro(entrada, "Novo limite: ");
            if (usuario.setLimiteEmprestimos(novoLimite)) {
                System.out.println("Limite atualizado: " + usuario);
            } else {
                System.out.println("Limite inválido. Nenhuma alteração feita.");
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void pesquisarPorTitulo(List<Livro> livros, Scanner entrada) {
        System.out.print("Título (ou parte): ");
        String termo = entrada.nextLine().trim().toLowerCase();
        boolean encontrou = false;

        for (Livro livro : livros) {
            if (livro.getTitulo().toLowerCase().contains(termo)) {
                System.out.println(livro);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum livro encontrado com esse título.");
        }
    }

    private static void pesquisarPorAutor(List<Livro> livros, Scanner entrada) {
        System.out.print("Autor (ou parte): ");
        String termo = entrada.nextLine().trim().toLowerCase();
        boolean encontrou = false;

        for (Livro livro : livros) {
            if (livro.getAutor().getNome().toLowerCase().contains(termo)) {
                System.out.println(livro);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum livro encontrado para esse autor.");
        }
    }

    private static void gerarRelatorio(List<Livro> livros) {
        int total = livros.size();
        int disponiveis = 0;

        for (Livro livro : livros) {
            if (livro.estaDisponivel()) {
                disponiveis++;
            }
        }

        System.out.println("\n--- Relatório do Acervo ---");
        System.out.println("Total de livros: " + total);
        System.out.println("Disponíveis: " + disponiveis);
        System.out.println("Emprestados: " + (total - disponiveis));
        listarLivros(livros);
    }

    private static void registrarEmprestimo(Scanner entrada, List<Livro> livros, List<Usuario> usuarios, List<Emprestimo> emprestimos) {
        System.out.print("Matrícula do usuário: ");
        Usuario usuario = buscarUsuario(usuarios, entrada.nextLine().trim());

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        if (!usuario.podeEmprestar()) {
            System.out.println("Usuário atingiu o limite de empréstimos.");
            return;
        }

        System.out.print("ISBN do livro: ");
        Livro livro = buscarLivroPorIsbn(livros, entrada.nextLine().trim());

        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return;
        }
        if (!livro.estaDisponivel()) {
            System.out.println("Livro indisponível para empréstimo.");
            return;
        }

        if (livro.emprestar()) {
            usuario.registrarEmprestimo();
            Emprestimo novoEmprestimo = new Emprestimo(livro, usuario);
            emprestimos.add(novoEmprestimo);
            System.out.println("Empréstimo registrado: " + novoEmprestimo);
        }
    }

    private static void listarEmprestimos(List<Emprestimo> emprestimos) {
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo registrado.");
            return;
        }
        for (Emprestimo emprestimo : emprestimos) {
            System.out.println(emprestimo);
        }
    }

    private static int lerInteiro(Scanner entrada, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = entrada.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }
}
