package usuarios;

public class Funcionarios {
    private String nome;
    private int id;
    private String cargo;

    public Funcionarios(String nome, int id, String cargo) {
        if (id < 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo inválido.");
        }
        this.nome = nome;
        this.id = id;
        this.cargo = cargo;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public String getCargo() {
        return cargo;
    }
}