package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import exceptions.HospedagemIndisponivelException;
import exceptions.ReservaNaoEncontradaException;
import hospedagem.Apartamento;

/**
 * Classe responsável por realizar operações de banco de dados relacionadas à entidade Apartamento.
 * Inclui métodos para listar, cadastrar, verificar disponibilidade, realizar reservas,
 * cancelar reservas e calcular o valor da hospedagem.
 * 
 * @author Angelo Pacheco
 * @author Renan Moreira
 */
public class ApartamentoDAO {

    /**
     * Lista todos os apartamentos cadastrados no banco de dados.
     * 
     * @return List<Apartamento> - Lista de apartamentos.
     */
    public List<Apartamento> listarApartamentos() {
        String sql = "SELECT * FROM Apartamento"; // Seleciona todos os apartamentos
        List<Apartamento> apartamentos = new ArrayList<>();

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ResultSet resultado = ps.executeQuery();

            while (resultado.next()) {
                int idHospedagem = resultado.getInt("Idhospedagem");
                int capacidadeMaxima = resultado.getInt("capacidadeMaxima");
                double precoDiaria = resultado.getDouble("precoDiaria");
                boolean reservado = resultado.getBoolean("reservado");

                // Cria um objeto Apartamento e adiciona à lista
                Apartamento apartamento = new Apartamento(idHospedagem, capacidadeMaxima, precoDiaria);
                apartamento.setReservado(reservado); // Define o status de reserva
                apartamentos.add(apartamento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return apartamentos;
    }

    /**
     * Cadastra um novo apartamento no banco de dados.
     * 
     * @param apartamento O apartamento a ser cadastrado.
     * @return boolean - True se o cadastro for bem-sucedido, False caso contrário.
     */
    public boolean cadastrarApartamento(Apartamento apartamento) {
        String sql = "INSERT INTO Apartamento (capacidadeMaxima, precoDiaria, reservado) VALUES (?, ?, ?)";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, apartamento.getCapacidadeMaxima());
            ps.setDouble(2, apartamento.getPrecoDiaria());
            ps.setBoolean(3, apartamento.isReservado());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Apartamento cadastrado com sucesso!");
                return true;
            } else {
                System.out.println("Falha ao cadastrar o apartamento.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Verifica a disponibilidade de um apartamento pelo seu ID.
     * 
     * @param Idhospedagem O ID do apartamento.
     * @return boolean - True se o apartamento estiver disponível, False caso contrário.
     */
    public boolean verificarDisponibilidade(int Idhospedagem) {
        String sql = "SELECT reservado FROM Apartamento WHERE Idhospedagem = ?";
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
     * Realiza a reserva de um apartamento pelo seu ID.
     * 
     * @param Idhospedagem O ID do apartamento.
     * @return boolean - True se a reserva for bem-sucedida, False caso contrário.
     * @throws HospedagemIndisponivelException Se o apartamento já estiver reservado.
     */
    public boolean realizarReserva(int Idhospedagem) throws HospedagemIndisponivelException {
        if (verificarDisponibilidade(Idhospedagem)) {
            String sql = "UPDATE Apartamento SET reservado = TRUE WHERE Idhospedagem = ?";
            try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
                ps.setInt(1, Idhospedagem);
                ps.executeUpdate();
                System.out.println("Reserva realizada com sucesso para o apartamento " + Idhospedagem);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new HospedagemIndisponivelException("Apartamento " + Idhospedagem + " já está reservado.");
        }
        return false;
    }

    /**
     * Cancela a reserva de um apartamento pelo seu ID.
     * 
     * @param Idhospedagem O ID do apartamento.
     * @return boolean - True se o cancelamento for bem-sucedido, False caso contrário.
     * @throws ReservaNaoEncontradaException Se a reserva não for encontrada.
     */
    public boolean cancelarReserva(int Idhospedagem) throws ReservaNaoEncontradaException {
        String sql = "UPDATE Apartamento SET reservado = FALSE WHERE Idhospedagem = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, Idhospedagem);
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Reserva cancelada com sucesso para o apartamento " + Idhospedagem);
                return true;
            } else {
                throw new ReservaNaoEncontradaException("Apartamento " + Idhospedagem + " não estava reservado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Calcula o valor total da hospedagem com base no ID do apartamento e no número de dias.
     * 
     * @param Idhospedagem O ID do apartamento.
     * @param dias O número de dias da hospedagem.
     * @return double - O valor total da hospedagem.
     */
    public double calcularValorHospedagem(int Idhospedagem, int dias) {
        String sql = "SELECT precoDiaria FROM Apartamento WHERE Idhospedagem = ?";
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