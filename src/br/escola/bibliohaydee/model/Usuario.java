package br.escola.bibliohaydee.model;

public class Usuario {
    public static final int LIMITE_PADRAO = 3;

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
        if (!limiteValido(limiteInformado)) {
            throw new IllegalArgumentException("O limite deve ser 1 ou mais.");
        }
        this.nome = nomeInformado.trim();
        this.matricula = matriculaInformada;
        this.tipo = tipoInformado;
        this.limiteEmprestimos = limiteInformado;
        this.emprestimosAtivos = 0;
    }

    // Sem limite: reaproveita o construtor completo
    public Usuario(String nome, String matricula, String tipo) {
        this(nome, matricula, tipo, LIMITE_PADRAO);
    }

    public static boolean nomeValido(String nomeTestado) {
        return nomeTestado != null && !nomeTestado.isBlank();
    }

    public static boolean tipoValido(String tipoTestado) {
        return "ALUNO".equals(tipoTestado) || "PROFESSOR".equals(tipoTestado);
    }

    public static boolean limiteValido(Integer limiteTestado) {
        return limiteTestado != null && limiteTestado >= 1;
    }

    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getTipo() { return tipo; }
    public Integer getLimiteEmprestimos() { return limiteEmprestimos; }
    public Integer getEmprestimosAtivos() { return emprestimosAtivos; }

    public boolean setNome(String novoNome) {
        if (!nomeValido(novoNome)) {
            return false;
        }
        this.nome = novoNome.trim();
        return true;
    }

    public boolean setLimiteEmprestimos(int novoLimite) {
        if (!limiteValido(novoLimite)) {
            return false;
        }
        this.limiteEmprestimos = novoLimite;
        return true;
    }

    // Sem setter para matricula, tipo e emprestimosAtivos.

    @Override
    public String toString() {
        return matricula + " | " + nome + " | " + tipo
                + " | empréstimos: " + emprestimosAtivos + "/" + limiteEmprestimos;
    }
}