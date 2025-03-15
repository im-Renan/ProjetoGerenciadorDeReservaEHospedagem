package hospedagem;

import DAO.QuartoDAO;
import exceptions.HospedagemIndisponivelException;
import exceptions.ReservaNaoEncontradaException;

public class Quarto extends Hospedagem {
    private boolean reservado; // Indica se o quarto está reservado ou não
    private QuartoDAO quartoDAO; // Objeto para acessar o banco de dados

    // Construtor
    public Quarto(int Idhospedagem, int capacidadeMaxima, double precoDiaria) {
        super(Idhospedagem, capacidadeMaxima, precoDiaria);
        this.reservado = false; // Inicialmente, o quarto não está reservado
        this.quartoDAO = new QuartoDAO(); // Inicializa o QuartoDAO
    }

    // Implementação dos métodos abstratos

    @Override
    public boolean verificaDisponibilidade() {
        // Chama o método do QuartoDAO para verificar a disponibilidade no banco de dados
        return quartoDAO.verificarDisponibilidade(getIdhospedagem());
    }

    @Override
    public boolean realizarReserva() throws HospedagemIndisponivelException {
        if (!verificaDisponibilidade()) {
            throw new HospedagemIndisponivelException("Quarto " + getIdhospedagem() + " não está disponível.");
        }

        boolean sucesso = quartoDAO.realizarReserva(getIdhospedagem());
        if (sucesso) {
            reservado = true; // Atualiza o estado local do quarto
        }
        return sucesso;
    }

    @Override
    public boolean cancelarReserva() throws ReservaNaoEncontradaException {
        boolean sucesso = quartoDAO.cancelarReserva(getIdhospedagem());
        if (sucesso) {
            reservado = false; // Atualiza o estado local do quarto
        } else {
            throw new ReservaNaoEncontradaException("Reserva do quarto " + getIdhospedagem() + " não encontrada.");
        }
        return sucesso;
    }

    @Override
    public double calcularValorHospedagem(int dias) {
        // Chama o método do QuartoDAO para calcular o valor da hospedagem
        return quartoDAO.calcularValorHospedagem(getIdhospedagem(), dias);
    }
    
    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }
    // Método toString para representação em string do objeto
    @Override
    public String toString() {
        return "Quarto{" +
                "Idhospedagem=" + getIdhospedagem() +
                ", capacidadeMaxima=" + getCapacidadeMaxima() +
                ", precoDiaria=" + getPrecoDiaria() +
                ", reservado=" + reservado +
                '}';
    }
}