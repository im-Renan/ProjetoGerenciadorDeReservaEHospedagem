package hospedagens;

import exceptions.HospedagemIndisponivelException;

public interface Hospedagem {
	
    boolean verificarDisponibilidade(int numeroHospedagem);
    boolean realizarReserva(int numeroHospedagem) throws HospedagemIndisponivelException;
    boolean cancelarReserva(int numeroHospedagem);
    double calcularDiaria(double precoPorDia, int dias);
    String getDescricao(); 
    int getCapacidadeMaxima(); 
}