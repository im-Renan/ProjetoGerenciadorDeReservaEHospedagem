package entidades;

/**
 * Classe que representa um cliente no sistema.
 * Contém informações como nome, CPF e telefone.
 * 
 * @author Angelo Pacheco
 * @author Renan Moreira
 */
public class Cliente {
    private String nome;    // Nome do cliente
    private String cpf;     // CPF do cliente
    private String telefone; // Telefone do cliente

    /**
     * Construtor padrão da classe Cliente.
     */
    public Cliente() {
    }

    /**
     * Construtor com parâmetros para inicializar um cliente.
     * 
     * @param nome O nome do cliente.
     * @param cpf O CPF do cliente.
     * @param telefone O telefone do cliente.
     */
    public Cliente(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    /**
     * Retorna o nome do cliente.
     * 
     * @return String - O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     * 
     * @param nome O nome do cliente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o CPF do cliente.
     * 
     * @return String - O CPF do cliente.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define o CPF do cliente.
     * 
     * @param cpf O CPF do cliente.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna o telefone do cliente.
     * 
     * @return String - O telefone do cliente.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone do cliente.
     * 
     * @param telefone O telefone do cliente.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna uma representação em string do objeto Cliente.
     * 
     * @return String - Representação do cliente no formato "Cliente{nome='...', cpf='...', telefone='...'}".
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}