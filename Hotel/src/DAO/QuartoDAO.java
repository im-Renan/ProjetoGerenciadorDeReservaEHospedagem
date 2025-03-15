package DAO;

import hospedagem.Quarto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import conexao.Conexao;

/**
 * Classe responsável por realizar operações de banco de dados relacionadas à entidade Quarto.
 * Inclui métodos para listar, cadastrar, verificar disponibilidade, realizar reservas,
 * cancelar reservas e calcular o valor da hospedagem.
 * 
 * @author Angelo Pacheco
 * @author Renan Moreira
 */
public class QuartoDAO {

    /**
     * Lista todos os quartos cadastrados no banco de dados.
     * 
     * @return List<Quarto> - Lista de quartos.
     */
    public List<Quarto> listarQuartos() {
        String sql = "SELECT * FROM Quarto"; // Seleciona todos os quartos
        List<Quarto> quartos = new ArrayList<>();

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ResultSet resultado = ps.executeQuery();

            while (resultado.next()) {
                int idHospedagem = resultado.getInt("Idhospedagem");
                int capacidadeMaxima = resultado.getInt("capacidadeMaxima");
                double precoDiaria = resultado.getDouble("precoDiaria");
                boolean reservado = resultado.getBoolean("reservado"); // Corrigido para "reservado"

                // Cria um objeto Quarto e adiciona à lista
                Quarto quarto = new Quarto(idHospedagem, capacidadeMaxima, precoDiaria);
                quarto.setReservado(reservado); // Define o status de reserva
                quartos.add(quarto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quartos;
    }

    /**
     * Cadastra um novo quarto no banco de dados.
     * 
     * @param quarto O quarto a ser cadastrado.
     * @return boolean - True se o cadastro for bem-sucedido, False caso contrário.
     */
    public boolean cadastrarQuarto(Quarto quarto) {
        String sql = "INSERT INTO Quarto (capacidadeMaxima, precoDiaria, reservado) VALUES (?, ?, ?)";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, quarto.getCapacidadeMaxima());
            ps.setDouble(2, quarto.getPrecoDiaria());
            ps.setBoolean(3, quarto.isReservado());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Quarto cadastrado com sucesso!");
                return true;
            } else {
                System.out.println("Falha ao cadastrar o quarto.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Verifica a disponibilidade de um quarto pelo seu ID.
     * 
     * @param Idhospedagem O ID do quarto.
     * @return boolean - True se o quarto estiver disponível, False caso contrário.
     */
    public boolean verificarDisponibilidade(int Idhospedagem) {
        String sql = "SELECT reservado FROM Quarto WHERE Idhospedagem = ?";
        boolean disponivel = false;

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, Idhospedagem);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                disponivel = !resultado.getBoolean("reservado"); // Retorna true se não estiver reservado
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disponivel;
    }

    /**
     * Realiza a reserva de um quarto pelo seu ID.
     * 
     * @param Idhospedagem O ID do quarto.
     * @return boolean - True se a reserva for bem-sucedida, False caso contrário.
     */
    public boolean realizarReserva(int Idhospedagem) {
        if (verificarDisponibilidade(Idhospedagem)) {
            String sql = "UPDATE Quarto SET reservado = TRUE WHERE Idhospedagem = ?";
            try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
                ps.setInt(1, Idhospedagem);
                ps.executeUpdate();
                System.out.println("Reserva realizada com sucesso para o quarto " + Idhospedagem);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Quarto " + Idhospedagem + " já está reservado.");
        }
        return false;
    }

    /**
     * Cancela a reserva de um quarto pelo seu ID.
     * 
     * @param Idhospedagem O ID do quarto.
     * @return boolean - True se o cancelamento for bem-sucedido, False caso contrário.
     */
    public boolean cancelarReserva(int Idhospedagem) {
        String sql = "UPDATE Quarto SET reservado = FALSE WHERE Idhospedagem = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, Idhospedagem);
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Reserva cancelada com sucesso para o quarto " + Idhospedagem);
                return true;
            } else {
                System.out.println("Quarto " + Idhospedagem + " não estava reservado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Calcula o valor total da hospedagem com base no ID do quarto e no número de dias.
     * 
     * @param Idhospedagem O ID do quarto.
     * @param dias O número de dias da hospedagem.
     * @return double - O valor total da hospedagem.
     */
    public double calcularValorHospedagem(int Idhospedagem, int dias) {
        String sql = "SELECT precoDiaria FROM Quarto WHERE Idhospedagem = ?";
        double valorTotal = 0.0;

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, Idhospedagem);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                double precoDiaria = resultado.getDouble("precoDiaria");
                valorTotal = precoDiaria * dias; // Multiplica o preço da diária pela quantidade de dias
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valorTotal;
    }
}