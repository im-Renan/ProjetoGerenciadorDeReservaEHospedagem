package hospedagem;

import exceptions.HospedagemIndisponivelException;
import exceptions.ReservaNaoEncontradaException;

public abstract class Hospedagem {
    private int Idhospedagem;
    private int capacidadeMaxima;
    private double precoDiaria;
    
    // Construtor
    public Hospedagem(int Idhospedagem, int capacidadeMaxima, double precoDiaria) {
        this.Idhospedagem = Idhospedagem;
        this.capacidadeMaxima = capacidadeMaxima;
        this.precoDiaria = precoDiaria;
    }

    // Getters e Setters
    public int getIdhospedagem() {
        return Idhospedagem;
    }

    public void setIdhospedagem(int Idhospedagem) {
        this.Idhospedagem = Idhospedagem;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public double calcularValorHospedagem(int dias){
        return getPrecoDiaria() * dias;
    }

    // Métodos abstratos
    public abstract boolean verificaDisponibilidade();

    public abstract boolean realizarReserva() throws HospedagemIndisponivelException;

    public abstract boolean cancelarReserva() throws ReservaNaoEncontradaException;


    // Método toString para representação em string do objeto
    @Override
    public String toString() {
        return "Hospedagem{" +
                "Idhospedagem=" + Idhospedagem +
                ", capacidadeMaxima=" + capacidadeMaxima +
                ", precoDiaria=" + precoDiaria +
                '}';
    }
}