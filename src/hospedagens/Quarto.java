package hospedagens;

public class Quarto extends HospedagemBase {
    private String tipoDeCama;

    public Quarto(int numeroHospedagem, String descricao, int capacidadeMaxima, String tipoDeCama) {
        super(numeroHospedagem, descricao, capacidadeMaxima);
        this.tipoDeCama = tipoDeCama;
    }

    // Getter e Setter
    public String getTipoDeCama() {
        return tipoDeCama;
    }
}