package br.escola.bibliohaydee.model;

public class Usuario {
    public static final int LIMITE_PADRAO = 3; // Requisito 4.1

    private String nome;
    private String matricula;
    private String tipo;
    private Integer limiteEmprestimos;
    private Integer emprestimosAtivos;

    // Construtor completo original
    public Usuario(String nomeInformado, String matriculaInformada,
                   String tipoInformado, Integer limiteInformado) {
        if (!nomeValido(nomeInformado)) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        if (!tipoValido(tipoInformado)) {
            throw new IllegalArgumentException("O tipo deve ser ALUNO ou PROFESSOR.");
        }
        this.nome = nomeInformado.trim();
        this.matricula = matriculaInformada;
        this.tipo = tipoInformado;
        this.limiteEmprestimos = limiteInformado;
        this.emprestimosAtivos = 0;
    }

    // 4.1 Novo Construtor Alternativo (chama o completo usando 'this')
    public Usuario(String nomeInformado, String matriculaInformada, String tipoInformado) {
        this(nomeInformado, matriculaInformada, tipoInformado, LIMITE_PADRAO);
    }

    public static boolean nomeValido(String nomeTestado) {
        return nomeTestado != null && !nomeTestado.isBlank();
    }

    public static boolean tipoValido(String tipoTestado) {
        return "ALUNO".equals(tipoTestado) || "PROFESSOR".equals(tipoTestado);
    }

    // Adicionado para validar o limite (usado na Main linhas 224 e 230)
    public static boolean limiteValido(Integer limiteTestado) {
        return limiteTestado != null && limiteTestado >= 1;
    }

    // Adicionado para verificar se o usuário ainda pode pegar livros (linha 544)
    public boolean podeEmprestar() {
        return this.emprestimosAtivos < this.limiteEmprestimos;
    }

    // Adicionado para somar 1 empréstimo ativo (linha 560)
    public void registrarEmprestimo() {
        if (podeEmprestar()) {
            this.emprestimosAtivos++;
        }
    }

    // 5.1 Alterado para retornar boolean conforme requisito da Semana 3
    public boolean setNome(String novoNome) {
        if (!nomeValido(novoNome)) {
            return false;
        }
        this.nome = novoNome.trim();
        return true;
    }

    // 5.1 Novo Setter com validação para o limite
    public boolean setLimiteEmprestimos(int limiteEmprestimos) {
        if (!limiteValido(limiteEmprestimos)) {
            return false;
        }
        this.limiteEmprestimos = limiteEmprestimos;
        return true;
    }

    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getTipo() { return tipo; }
    public Integer getLimiteEmprestimos() { return limiteEmprestimos; }
    public Integer getEmprestimosAtivos() { return emprestimosAtivos; }

    @Override
    public String toString() {
        return matricula + " | " + nome + " | " + tipo
                + " | empréstimos: " + emprestimosAtivos + "/" + limiteEmprestimos;
    }
}
