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

        // Etapa 5: acervo ainda vazio
        listarLivros(livros);

        // Etapa 3: criar e confirmar um Autor
        Autor autor = new Autor();
        autor.setNome("Machado de Assis");
        autor.setNacionalidade("Brasileira");
        autor.setAnoNascimento(1839);
        autores.add(autor);
        System.out.println("Autor criado: " + autor);

        // Etapa 4: criar Livro usando o mesmo objeto Autor
        Livro livro = new Livro("Dom Casmurro", "978-85-359-0277-5", autor, 1899, "Romance");
        livros.add(livro);
        System.out.println("Disponivel ao criar? " + livro.isDisponivel());

        // Etapa 5: listar o acervo
        listarLivros(livros);

        // Semana 2: cadastro de usuários
        Scanner entrada = new Scanner(System.in);
        List<Usuario> usuarios = new ArrayList<>();

        String continuar;
        do {
            cadastrarUsuario(entrada, usuarios);
            System.out.print("Cadastrar outro usuário? (S/N): ");
            continuar = entrada.nextLine().trim().toUpperCase();
        } while (continuar.equals("S"));

        listarUsuarios(usuarios);
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

        System.out.print("Limite de empréstimos: ");
        Integer limite = lerInteiro(entrada);

        // Só chegamos aqui com dados válidos.
        Usuario usuario = new Usuario(nome, matricula, tipo, limite);
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

    private static int lerInteiro(Scanner entrada) {
        while (true) {
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número inteiro: ");
            }
        }
    }
}