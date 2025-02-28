package hospedagens.servicosAdicionais;

import exceptions.ServicoNaoPermitidoException;

public interface ServicoAdicional {
    boolean verificarDisponibilidade();
    boolean realizarReserva() throws ServicoNaoPermitidoException;
    boolean cancelarReserva();
    double calcularPreco();
}