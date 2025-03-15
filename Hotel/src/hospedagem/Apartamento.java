package hospedagem;

import DAO.ApartamentoDAO;
import exceptions.HospedagemIndisponivelException;
import exceptions.ReservaNaoEncontradaException;

public class Apartamento extends Hospedagem {
    private boolean reservado; // Indica se o apartamento está reservado ou não
    private ApartamentoDAO apartamentoDAO; // Objeto para acessar o banco de dados

    // Construtor
    public Apartamento(int Idhospedagem, int capacidadeMaxima, double precoDiaria) {
        super(Idhospedagem, capacidadeMaxima, precoDiaria);
        this.reservado = false; // Inicialmente, o apartamento não está reservado
        this.apartamentoDAO = new ApartamentoDAO(); // Inicializa o ApartamentoDAO
    }

    // Implementação dos métodos abstratos

    @Override
    public boolean verificaDisponibilidade() {
        // Chama o método do ApartamentoDAO para verificar a disponibilidade no banco de dados
        return apartamentoDAO.verificarDisponibilidade(getIdhospedagem());
    }

    @Override
    public boolean realizarReserva() throws HospedagemIndisponivelException {
        if (!verificaDisponibilidade()) {
            throw new HospedagemIndisponivelException("Apartamento " + getIdhospedagem() + " não está disponível.");
        }

        boolean sucesso = apartamentoDAO.realizarReserva(getIdhospedagem());
        if (sucesso) {
            reservado = true; // Atualiza o estado local do apartamento
        }
        return sucesso;
    }
    
    @Override
    public boolean cancelarReserva() throws ReservaNaoEncontradaException {
        boolean sucesso = apartamentoDAO.cancelarReserva(getIdhospedagem());
        if (sucesso) {
            reservado = false; // Atualiza o estado local do apartamento
        } else {
            throw new ReservaNaoEncontradaException("Reserva do apartamento " + getIdhospedagem() + " não encontrada.");
        }
        return sucesso;
    }

    @Override
    public double calcularValorHospedagem(int dias) {
        // Chama o método do ApartamentoDAO para calcular o valor da hospedagem
        return apartamentoDAO.calcularValorHospedagem(getIdhospedagem(), dias);
    }

    // Métodos específicos da classe Apartamento

    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    // Método toString para representação em string do objeto
    @Override
    public String toString() {
        return "Apartamento{" +
                "Idhospedagem=" + getIdhospedagem() +
                ", capacidadeMaxima=" + getCapacidadeMaxima() +
                ", precoDiaria=" + getPrecoDiaria() +
                ", reservado=" + reservado +
                '}';
    }
}