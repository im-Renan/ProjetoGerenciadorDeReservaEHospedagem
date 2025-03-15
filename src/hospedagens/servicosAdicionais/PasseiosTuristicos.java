package hospedagens.servicosAdicionais;

public class PasseiosTuristicos implements ServicoAdicional {
    private int vagasDisponiveis;

    public PasseiosTuristicos(String local, String horario, int vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    @Override
    public boolean verificarDisponibilidade() {
        return vagasDisponiveis > 0;
    }

    @Override
    public boolean realizarReserva() {
        if (verificarDisponibilidade()) {
            vagasDisponiveis--;
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelarReserva() {
        vagasDisponiveis++;
        return true;
    }

    @Override
    public double calcularPreco() {
        return 100.0; // Exemplo: definir um preço fixo para o passeio
    }
}
