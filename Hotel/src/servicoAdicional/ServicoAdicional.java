package servicoAdicional;

public abstract class ServicoAdicional {
    private int IdservicoAdicional;
    private String destino;
    private int quantidadeDePessoas;
    private double precoPorPessoa; // Corrigido o nome do atributo

    // Construtor
    public ServicoAdicional(int IdservicoAdicional, String destino, int quantidadeDePessoas, double precoPorPessoa) {
        this.IdservicoAdicional = IdservicoAdicional;
        this.destino = destino;
        this.quantidadeDePessoas = quantidadeDePessoas;
        this.precoPorPessoa = precoPorPessoa; // Corrigido o nome do atributo
    }

    // Getters e Setters
    public int getIdservicoAdicional() {
        return IdservicoAdicional;
    }

    public void setIdservicoAdicional(int IdservicoAdicional) {
        this.IdservicoAdicional = IdservicoAdicional;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getQuantidadeDePessoas() {
        return quantidadeDePessoas;
    }

    public void setQuantidadeDePessoas(int quantidadeDePessoas) {
        this.quantidadeDePessoas = quantidadeDePessoas;
    }

    public double getPrecoPorPessoa() { // Corrigido o nome do método
        return precoPorPessoa;
    }

    public void setPrecoPorPessoa(double precoPorPessoa) { // Corrigido o nome do método
        this.precoPorPessoa = precoPorPessoa;
    }

    // Métodos abstratos
    public abstract boolean verificaDisponibilidade();

    public abstract boolean realizarReserva();

    public abstract boolean cancelarReserva();
}