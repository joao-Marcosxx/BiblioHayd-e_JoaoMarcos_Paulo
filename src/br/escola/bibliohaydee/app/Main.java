package br.escola.bibliohaydee.app;

import br.escola.bibliohaydee.model.Autor;
import br.escola.bibliohaydee.model.Livro;
import br.escola.bibliohaydee.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Autor> autores = new ArrayList<>();
        List<Livro> livros = new ArrayList<>();

        listarLivros(livros);

        Autor autor = new Autor();
        autor.setNome("Machado de Assis");
        autor.setNacionalidade("Brasileira");
        autor.setAnoNascimento(1839);
        autores.add(autor);
        System.out.println("Autor criado: " + autor);

        // Livro com gênero e livro sem gênero (Semana 3)
        livros.add(new Livro("Dom Casmurro", "978-85-359-0277-5", autor, 1899));
        livros.add(new Livro("Memórias Póstumas", "978-85-359-0278-2", autor, 1881));
        listarLivros(livros);

        Scanner entrada = new Scanner(System.in);
        List<Usuario> usuarios = new ArrayList<>();

        int opcao;
        do {
            System.out.println("\n1 - Cadastrar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Atualizar usuário");
            System.out.println("0 - Sair");
            opcao = lerInteiro(entrada, "Opção: ");

            switch (opcao) {
                case 1 -> cadastrarUsuario(entrada, usuarios);
                case 2 -> listarUsuarios(usuarios);
                case 3 -> atualizarUsuario(entrada, usuarios);
                case 0 -> System.out.println("Encerrando.");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void listarLivros(List<Livro> livros) {
        if (livros.isEmpty()) {
            System.out.println("O acervo está vazio.");
            return;
        }
        for (Livro l : livros) {
            System.out.println(l);
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
        String matricula = entrada.nextLine();

        String tipo;
        do {
            System.out.print("Tipo (ALUNO ou PROFESSOR): ");
            tipo = entrada.nextLine().trim().toUpperCase();
            if (!Usuario.tipoValido(tipo)) {
                System.out.println("Tipo inválido: digite ALUNO ou PROFESSOR.");
            }
        } while (!Usuario.tipoValido(tipo));

        System.out.print("Definir o limite? (S/N): ");
        String resp = entrada.nextLine().trim();

        Usuario usuario;
        if (resp.equalsIgnoreCase("S")) {
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
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    private static Usuario buscarUsuario(List<Usuario> usuarios, String matricula) {
        for (Usuario u : usuarios) {
            if (u.getMatricula().equals(matricula)) {
                return u;
            }
        }
        return null;
    }

    private static void atualizarUsuario(Scanner entrada, List<Usuario> usuarios) {
        System.out.print("Matrícula: ");
        Usuario u = buscarUsuario(usuarios, entrada.nextLine().trim());
        if (u == null) {
            System.out.println("Usuário não localizado. Nenhuma alteração feita.");
            return;
        }

        System.out.println("Encontrado: " + u);
        System.out.println("1 - nome   2 - limite");
        String op = entrada.nextLine().trim();

        if (op.equals("1")) {
            System.out.print("Novo nome: ");
            boolean ok = u.setNome(entrada.nextLine());
            System.out.println(ok ? "Nome atualizado com sucesso."
                    : "Nome inválido. Nada foi alterado.");
        } else if (op.equals("2")) {
            int novo = lerInteiro(entrada, "Novo limite: ");
            boolean ok = u.setLimiteEmprestimos(novo);
            System.out.println(ok ? "Limite atualizado com sucesso."
                    : "Limite inválido. Nada foi alterado.");
        } else {
            System.out.println("Opção inválida. Nenhuma alteração feita.");
        }
    }

    private static int lerInteiro(Scanner entrada, String mensagem) {
        System.out.print(mensagem);
        while (true) {
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número inteiro: ");
            }
        }
    }
}