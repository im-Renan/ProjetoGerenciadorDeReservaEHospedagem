package hospedagens.servicosAdicionais;

import exceptions.ServicoNaoPermitidoException;

public class Transfer implements ServicoAdicional {
    private boolean disponivel;

    public Transfer(String destino, String horarioPartida) {
        this.disponivel = true;
    }

    @Override
    public boolean verificarDisponibilidade() {
        return disponivel;
    }

    @Override
    public boolean realizarReserva() throws ServicoNaoPermitidoException {
        if (!disponivel) {
            throw new ServicoNaoPermitidoException("Transfer não disponível para reserva.");
        }
        disponivel = false;
        return true;
    }


    @Override
    public boolean cancelarReserva() {
        disponivel = true;
        return true;
    }

    @Override
    public double calcularPreco() {
        return 50.0; // Exemplo: definir um preço fixo para o transfer
    }
}
