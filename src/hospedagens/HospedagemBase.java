package hospedagens;

import exceptions.HospedagemIndisponivelException;

public abstract class HospedagemBase implements Hospedagem {
    private int numeroHospedagem;
    private String descricao;
    private int capacidadeMaxima;
    private boolean disponivel;

    public HospedagemBase(int numeroHospedagem, String descricao, int capacidadeMaxima) {
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("Capacidade máxima deve ser maior que zero.");
        }
        this.numeroHospedagem = numeroHospedagem;
        this.descricao = descricao;
        this.capacidadeMaxima = capacidadeMaxima;
        this.disponivel = true;
    }

    @Override
    public boolean verificarDisponibilidade(int numeroHospedagem) {
        return disponivel;
    }

    @Override
    public boolean realizarReserva(int numeroHospedagem) throws HospedagemIndisponivelException {
        if (!verificarDisponibilidade(numeroHospedagem)) {
            throw new HospedagemIndisponivelException("Hospedagem " + numeroHospedagem + " indisponível.");
        }
        this.disponivel = false;
        return true;
    }

    @Override
    public boolean cancelarReserva(int numeroHospedagem) {
        if (this.numeroHospedagem == numeroHospedagem && !disponivel) {
            this.disponivel = true;
            return true;
        }
        return false;
    }

    @Override
    public double calcularDiaria(double precoPorDia, int dias) {
        return precoPorDia * dias;
    }

    // Getters
    public int getNumeroHospedagem() {
        return numeroHospedagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}