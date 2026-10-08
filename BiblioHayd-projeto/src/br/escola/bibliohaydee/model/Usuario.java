package br.escola.bibliohaydee.model;

public class Usuario {
    private String nome;
    private String matricula;
    private String tipo;
    private Integer limiteEmprestimos;
    private Integer emprestimosAtivos;

    public Usuario(String nomeInformado, String matriculaInformada,
                   String tipoInformado, Integer limiteInformado) {
        if (!nomeValido(nomeInformado)) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        if (!tipoValido(tipoInformado)) {
            throw new IllegalArgumentException("O tipo deve ser ALUNO ou PROFESSOR.");
        }
        nome = nomeInformado;
        matricula = matriculaInformada;
        tipo = tipoInformado;
        limiteEmprestimos = limiteInformado;
        emprestimosAtivos = 0;
    }

    // Regras de validação em um único lugar: a própria classe.
    public static boolean nomeValido(String nomeTestado) {
        return nomeTestado != null && !nomeTestado.isBlank();
    }

    public static boolean tipoValido(String tipoTestado) {
        return "ALUNO".equals(tipoTestado) || "PROFESSOR".equals(tipoTestado);
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public Integer getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    public void setNome(String novoNome) {
        if (!nomeValido(novoNome)) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        nome = novoNome;
    }

    public void setTipo(String novoTipo) {
        if (!tipoValido(novoTipo)) {
            throw new IllegalArgumentException("O tipo deve ser ALUNO ou PROFESSOR.");
        }
        tipo = novoTipo;
    }

    // Sem setter público para emprestimosAtivos: será controlado
    // pelas regras de empréstimo em semanas futuras.

    @Override
    public String toString() {
        return matricula + " | " + nome + " | " + tipo
                + " | empréstimos: " + emprestimosAtivos + "/" + limiteEmprestimos;
    }
}