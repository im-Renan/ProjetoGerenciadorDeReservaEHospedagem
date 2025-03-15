package hospedagem;

import DAO.CabanaDAO;
import exceptions.HospedagemIndisponivelException;
import exceptions.ReservaNaoEncontradaException;

public class Cabana extends Hospedagem {
    private boolean reservado; // Indica se a cabana está reservada ou não
    private CabanaDAO cabanaDAO; // Objeto para acessar o banco de dados

    // Construtor
    public Cabana(int Idhospedagem, int capacidadeMaxima, double precoDiaria) {
        super(Idhospedagem, capacidadeMaxima, precoDiaria);
        this.reservado = false; // Inicialmente, a cabana não está reservada
        this.cabanaDAO = new CabanaDAO(); // Inicializa o CabanaDAO
    }

    // Implementação dos métodos abstratos

    @Override
    public boolean verificaDisponibilidade() {
        // Chama o método do CabanaDAO para verificar a disponibilidade no banco de dados
        return cabanaDAO.verificarDisponibilidade(getIdhospedagem());
    }

    @Override
    public boolean realizarReserva() throws HospedagemIndisponivelException {
        if (!verificaDisponibilidade()) {
            throw new HospedagemIndisponivelException("Cabana " + getIdhospedagem() + " não está disponível.");
        }

        boolean sucesso = cabanaDAO.realizarReserva(getIdhospedagem());
        if (sucesso) {
            reservado = true; // Atualiza o estado local da cabana
        }
        return sucesso;
    }

    @Override
    public boolean cancelarReserva() throws ReservaNaoEncontradaException {
        boolean sucesso = cabanaDAO.cancelarReserva(getIdhospedagem());
        if (sucesso) {
            reservado = false; // Atualiza o estado local da cabana
        } else {
            throw new ReservaNaoEncontradaException("Reserva da cabana " + getIdhospedagem() + " não encontrada.");
        }
        return sucesso;
    }

    @Override
    public double calcularValorHospedagem(int dias) {
        // Chama o método do CabanaDAO para calcular o valor da hospedagem
        return cabanaDAO.calcularValorHospedagem(getIdhospedagem(), dias);
    }

    // Métodos específicos da classe Cabana

    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    // Método toString para representação em string do objeto
    @Override
    public String toString() {
        return "Cabana{" +
                "Idhospedagem=" + getIdhospedagem() +
                ", capacidadeMaxima=" + getCapacidadeMaxima() +
                ", precoDiaria=" + getPrecoDiaria() +
                ", reservado=" + reservado +
                '}';
    }
}