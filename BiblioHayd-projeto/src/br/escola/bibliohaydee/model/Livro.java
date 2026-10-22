package br.escola.bibliohaydee.model;

public class Livro {
    private String titulo;
    private String isbn;
    private Autor autor;
    private int anoPublicacao;
    private String genero;
    private boolean disponivel;

    public Livro(String titulo, String isbn, Autor autor, int anoPublicacao, String genero) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.disponivel = true; // todo livro novo começa disponível
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public Autor getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getGenero() {
        return genero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    @Override
    public String toString() {
        return titulo + " | ISBN: " + isbn + " | Autor: " + autor.getNome()
                + " | " + anoPublicacao + " | " + genero
                + " | " + (disponivel ? "Disponível" : "Indisponível");
    }
}