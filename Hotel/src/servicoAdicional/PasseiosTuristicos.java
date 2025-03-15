package servicoAdicional;

public class PasseiosTuristicos extends ServicoAdicional {
    private boolean disponivel; // Novo atributo para verificar disponibilidade

    // Construtor
    public PasseiosTuristicos(int IdservicoAdicional, String destino, int quantidadeDePessoas, double precoPorPessoa) {
        super(IdservicoAdicional, destino, quantidadeDePessoas, precoPorPessoa);
        this.disponivel = true; // Inicialmente, o passeio está disponível
    }

    // Método para verificar se o passeio está disponível
    public boolean isDisponivel() {
        return disponivel;
    }

    // Método para definir o status de disponibilidade
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    // Implementação dos métodos abstratos
    @Override
    public boolean verificaDisponibilidade() {
        return disponivel; // Verifica se o passeio está disponível
    }

    @Override
    public boolean realizarReserva() {
        if (verificaDisponibilidade()) {
            setDisponivel(false); // Reserva o passeio
            System.out.println("Reserva realizada com sucesso para o passeio turístico: " + getDestino());
            return true;
        } else {
            System.out.println("Passeio turístico " + getDestino() + " não está disponível.");
            return false;
        }
    }

    @Override
    public boolean cancelarReserva() {
        setDisponivel(true); // Cancela a reserva do passeio
        System.out.println("Reserva cancelada com sucesso para o passeio turístico: " + getDestino());
        return true;
    }

    // Método adicional para calcular o valor total do passeio
    public double calcularValorTotal(int quantidadeDePessoas) {
        return getPrecoPorPessoa() * quantidadeDePessoas;
    }
}