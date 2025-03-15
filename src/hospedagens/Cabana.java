package hospedagens;

public class Cabana extends HospedagemBase {
    private boolean temVaranda;
    private boolean vistaParaMar;

    public Cabana(int numeroHospedagem, String descricao, int capacidadeMaxima, boolean temVaranda, boolean vistaParaMar) {
        super(numeroHospedagem, descricao, capacidadeMaxima);
        this.temVaranda = temVaranda;
        this.vistaParaMar = vistaParaMar;
    }

    public boolean isTemVaranda() {
        return temVaranda;
    }

    public boolean isVistaParaMar() {
        return vistaParaMar;
    }

    @Override
    public double calcularDiaria(double precoPorDia, int dias) {
        double ajusteTemporada = 1.2; // Ajuste de 20% para temporada alta
        return super.calcularDiaria(precoPorDia, dias) * ajusteTemporada;
    }
}