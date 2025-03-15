package cobranca;

import java.time.LocalDate;

public class Cobranca {
    private int idCobranca;          // ID da cobrança
    private String cpfCliente;       // CPF do cliente
    private double valorTotal;       // Valor total devido
    private LocalDate dataGeracao;   // Data de geração da cobrança

    // Construtor com todos os campos
    public Cobranca(int idCobranca, String cpfCliente, double valorTotal, LocalDate dataGeracao) {
        this.idCobranca = idCobranca;
        this.cpfCliente = cpfCliente;
        this.valorTotal = valorTotal;
        this.dataGeracao = dataGeracao;
    }

    // Getters e Setters
    public int getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(int idCobranca) {
        this.idCobranca = idCobranca;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        if (cpfCliente == null || !cpfCliente.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.cpfCliente = cpfCliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        if (valorTotal < 0) {
            throw new IllegalArgumentException("Valor total não pode ser negativo");
        }
        this.valorTotal = valorTotal;
    }

    public LocalDate getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDate dataGeracao) {
        if (dataGeracao == null || dataGeracao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de geração inválida");
        }
        this.dataGeracao = dataGeracao;
    }

    // Método toString para exibir os dados da cobrança
    @Override
    public String toString() {
        return "Cobranca{" +
                "idCobranca=" + idCobranca +
                ", cpfCliente='" + cpfCliente + '\'' +
                ", valorTotal=" + valorTotal +
                ", dataGeracao=" + dataGeracao +
                '}';
    }
}