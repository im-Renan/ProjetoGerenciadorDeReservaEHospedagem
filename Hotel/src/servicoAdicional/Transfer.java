package servicoAdicional;

public class Transfer extends ServicoAdicional {
    private boolean reservado; // Novo atributo para verificar disponibilidade

    // Construtor
    public Transfer(int IdservicoAdicional, String destino, int quantidadeDePessoas, double precoPorPessoa) {
        super(IdservicoAdicional, destino, quantidadeDePessoas, precoPorPessoa);
        this.reservado = false; // Inicialmente, o transfer não está reservado
    }

    // Método para verificar se o transfer está reservado
    public boolean isReservado() {
        return reservado;
    }

    // Método para definir o status de reserva
    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    // Implementação dos métodos abstratos
    @Override
    public boolean verificaDisponibilidade() {
        return !reservado; // Verifica se o transfer não está reservado
    }

    @Override
    public boolean realizarReserva() {
        if (verificaDisponibilidade()) {
            setReservado(true); // Reserva o transfer
            System.out.println("Reserva realizada com sucesso para o transfer: " + getDestino());
            return true;
        } else {
            System.out.println("Transfer " + getDestino() + " não está disponível.");
            return false;
        }
    }

    @Override
    public boolean cancelarReserva() {
        setReservado(false); // Cancela a reserva do transfer
        System.out.println("Reserva cancelada com sucesso para o transfer: " + getDestino());
        return true;
    }

    // Método adicional para calcular o valor total do transfer
    public double calcularValorTotal(int quantidadeDePessoas) {
        return getPrecoPorPessoa() * quantidadeDePessoas;
    }
}