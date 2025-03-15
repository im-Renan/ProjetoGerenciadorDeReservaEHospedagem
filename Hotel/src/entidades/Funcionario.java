package entidades;

/**
 * Classe que representa um funcionário no sistema.
 * Contém informações como código do funcionário, nome, cargo e senha.
 * 
 * @author Angelo Pacheco
 * @author Renan Moreira
 */
public class Funcionario {
    private int CodFuncionario; // Código único do funcionário
    private String nome;        // Nome do funcionário
    private String cargo;       // Cargo do funcionário
    private int senha;          // Senha do funcionário

    /**
     * Construtor padrão da classe Funcionario.
     */
    public Funcionario() {
    }

    /**
     * Construtor com parâmetros para inicializar um funcionário.
     * 
     * @param nome O nome do funcionário.
     * @param cargo O cargo do funcionário.
     * @param senha A senha do funcionário.
     */
    public Funcionario(String nome, String cargo, int senha) {
        this.nome = nome;
        this.cargo = cargo;
        this.senha = senha;
    }

    /**
     * Retorna o código do funcionário.
     * 
     * @return int - O código do funcionário.
     */
    public int getCodFuncionario() {
        return CodFuncionario;
    }

    /**
     * Define o código do funcionário.
     * 
     * @param CodFuncionario O código do funcionário.
     */
    public void setCodFuncionario(int CodFuncionario) {
        this.CodFuncionario = CodFuncionario;
    }

    /**
     * Retorna o nome do funcionário.
     * 
     * @return String - O nome do funcionário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do funcionário.
     * 
     * @param nome O nome do funcionário.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o cargo do funcionário.
     * 
     * @return String - O cargo do funcionário.
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Define o cargo do funcionário.
     * 
     * @param cargo O cargo do funcionário.
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Retorna a senha do funcionário.
     * 
     * @return int - A senha do funcionário.
     */
    public int getSenha() {
        return senha;
    }

    /**
     * Define a senha do funcionário.
     * 
     * @param senha A senha do funcionário.
     */
    public void setSenha(int senha) {
        this.senha = senha;
    }

    /**
     * Retorna uma representação em string do objeto Funcionario.
     * 
     * @return String - Representação do funcionário no formato "Funcionario{CodFuncionario=..., nome='...', cargo='...', senha=...}".
     */
    @Override
    public String toString() {
        return "Funcionario{" +
                "CodFuncionario=" + CodFuncionario +
                ", nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                ", senha=" + senha +
                '}';
    }
}