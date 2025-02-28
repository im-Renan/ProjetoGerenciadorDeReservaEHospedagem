package hospedagens;

public class Apartamento extends HospedagemBase {
    private int numeroQuartos;
    private double area;

    public Apartamento(int numeroHospedagem, String descricao, int capacidadeMaxima, int numeroQuartos, double area) {
        super(numeroHospedagem, descricao, capacidadeMaxima);
        this.numeroQuartos = numeroQuartos;
        this.area = area;
    }

    // Getters e Setters
    public int getNumeroQuartos() {
        return numeroQuartos;
    }

    public double getArea() {
        return area;
    }

    @Override
    public double calcularDiaria(double precoPorDia, int dias) {
        double taxaAdicional = 50.0; // Taxa adicional para apartamentos
        return super.calcularDiaria(precoPorDia, dias) + (taxaAdicional * dias);
    }
}