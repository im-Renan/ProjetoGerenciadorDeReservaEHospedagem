package DAO;

import hospedagem.Cabana;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import conexao.Conexao;

/**
 * Classe responsável por realizar operações de banco de dados relacionadas à entidade Cabana.
 * Inclui métodos para listar, cadastrar, verificar disponibilidade, realizar reservas,
 * cancelar reservas e calcular o valor da hospedagem.
 * 
 * @author Angelo Pacheco
 * @author Renan Moreira
 */
public class CabanaDAO {

    /**
     * Lista todas as cabanas cadastradas no banco de dados.
     * 
     * @return List<Cabana> - Lista de cabanas.
     */
    public List<Cabana> listarCabanas() {
        String sql = "SELECT * FROM Cabana"; // Seleciona todas as cabanas
        List<Cabana> cabanas = new ArrayList<>();

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ResultSet resultado = ps.executeQuery();

            while (resultado.next()) {
                int idHospedagem = resultado.getInt("Idhospedagem");
                int capacidadeMaxima = resultado.getInt("capacidadeMaxima");
                double precoDiaria = resultado.getDouble("precoDiaria");
                boolean reservado = resultado.getBoolean("reservado"); // Corrigido para "reservado"

                // Cria um objeto Cabana e adiciona à lista
                Cabana cabana = new Cabana(idHospedagem, capacidadeMaxima, precoDiaria);
                cabana.setReservado(reservado); // Define o status de reserva
                cabanas.add(cabana);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cabanas;
    }

    /**
     * Cadastra uma nova cabana no banco de dados.
     * 
     * @param cabana A cabana a ser cadastrada.
     * @return boolean - True se o cadastro for bem-sucedido, False caso contrário.
     */
    public boolean cadastrarCabana(Cabana cabana) {
        String sql = "INSERT INTO Cabana (capacidadeMaxima, precoDiaria, reservado) VALUES (?, ?, ?)";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, cabana.getCapacidadeMaxima());
            ps.setDouble(2, cabana.getPrecoDiaria());
            ps.setBoolean(3, cabana.isReservado());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Cabana cadastrada com sucesso!");
                return true;
            } else {
                System.out.println("Falha ao cadastrar a cabana.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Verifica a disponibilidade de uma cabana pelo seu ID.
     * 
     * @param Idhospedagem O ID da cabana.
     * @return boolean - True se a cabana estiver disponível, False caso contrário.
     */
    public boolean verificarDisponibilidade(int Idhospedagem) {
        String sql = "SELECT reservado FROM Cabana WHERE Idhospedagem = ?";
        boolean disponivel = false;

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, Idhospedagem);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                disponivel = !resultado.getBoolean("reservado"); // Retorna true se não estiver reservada
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disponivel;
    }

    /**
     * Realiza a reserva de uma cabana pelo seu ID.
     * 
     * @param Idhospedagem O ID da cabana.
     * @return boolean - True se a reserva for bem-sucedida, False caso contrário.
     */
    public boolean realizarReserva(int Idhospedagem) {
        if (verificarDisponibilidade(Idhospedagem)) {
            String sql = "UPDATE Cabana SET reservado = TRUE WHERE Idhospedagem = ?";
            try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
                ps.setInt(1, Idhospedagem);
                ps.executeUpdate();
                System.out.println("Reserva realizada com sucesso para a cabana " + Idhospedagem);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Cabana " + Idhospedagem + " já está reservada.");
        }
        return false;
    }

    /**
     * Cancela a reserva de uma cabana pelo seu ID.
     * 
     * @param Idhospedagem O ID da cabana.
     * @return boolean - True se o cancelamento for bem-sucedido, False caso contrário.
     */
    public boolean cancelarReserva(int Idhospedagem) {
        String sql = "UPDATE Cabana SET reservado = FALSE WHERE Idhospedagem = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, Idhospedagem);
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Reserva cancelada com sucesso para a cabana " + Idhospedagem);
                return true;
            } else {
                System.out.println("Cabana " + Idhospedagem + " não estava reservada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Calcula o valor total da hospedagem com base no ID da cabana e no número de dias.
     * 
     * @param Idhospedagem O ID da cabana.
     * @param dias O número de dias da hospedagem.
     * @return double - O valor total da hospedagem.
     */
    public double calcularValorHospedagem(int Idhospedagem, int dias) {
        String sql = "SELECT precoDiaria FROM Cabana WHERE Idhospedagem = ?";
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