package DAO;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RelatorioDAO {

    public List<Map<String, String>> relatorioReservasConfirmadas() {
        List<Map<String, String>> reservasConfirmadas = new ArrayList<>();
        String sql = "SELECT * FROM Reserva WHERE status = 'Confirmada'"; // Consulta SQL

        try (Connection conn = Conexao.getConexao(); // Obtém a conexão
             PreparedStatement ps = conn.prepareStatement(sql); // Prepara a declaração
             ResultSet rs = ps.executeQuery()) { // Executa a consulta

            // Itera sobre o ResultSet e adiciona os dados à lista
            while (rs.next()) {
                Map<String, String> reserva = new HashMap<>();
                reserva.put("idReserva", String.valueOf(rs.getInt("idReserva")));
                reserva.put("cliente_cpf", rs.getString("cliente_cpf"));
                reserva.put("idHospedagem", String.valueOf(rs.getInt("idHospedagem")));
                reserva.put("escolhaHospedagem", String.valueOf(rs.getInt("escolhaHospedagem")));
                reserva.put("dataCheckIn", rs.getDate("dataCheckIn").toString());
                reserva.put("dataCheckOut", rs.getDate("dataCheckOut") != null ? rs.getDate("dataCheckOut").toString() : "N/A");
                reserva.put("status", rs.getString("status"));

                reservasConfirmadas.add(reserva); // Adiciona o mapa à lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservasConfirmadas; // Retorna a lista de reservas confirmadas
    }

    /**
     * Recupera todas as reservas canceladas dentro de um intervalo de datas.
     *
     * @param dataInicio Data de início do intervalo (formato: yyyy-MM-dd).
     * @param dataFim    Data de fim do intervalo (formato: yyyy-MM-dd).
     * @return Lista de mapas contendo os dados das reservas canceladas.
     */
    public List<Map<String, String>> relatorioCancelamento(String dataInicio, String dataFim) {
        List<Map<String, String>> reservasCanceladas = new ArrayList<>();
        String sql = "SELECT * FROM Reserva WHERE status = 'Cancelada' AND dataCancelamento BETWEEN ? AND ?"; // Consulta SQL

        try (Connection conn = Conexao.getConexao(); // Obtém a conexão
             PreparedStatement ps = conn.prepareStatement(sql)) { // Prepara a declaração

            // Define os parâmetros da consulta
            ps.setString(1, dataInicio);
            ps.setString(2, dataFim);

            try (ResultSet rs = ps.executeQuery()) { // Executa a consulta
                // Itera sobre o ResultSet e adiciona os dados à lista
                while (rs.next()) {
                    Map<String, String> reserva = new HashMap<>();
                    reserva.put("idReserva", String.valueOf(rs.getInt("idReserva")));
                    reserva.put("cliente_cpf", rs.getString("cliente_cpf"));
                    reserva.put("idHospedagem", String.valueOf(rs.getInt("idHospedagem")));
                    reserva.put("escolhaHospedagem", String.valueOf(rs.getInt("escolhaHospedagem")));
                    reserva.put("dataCheckIn", rs.getDate("dataCheckIn").toString());
                    reserva.put("dataCheckOut", rs.getDate("dataCheckOut") != null ? rs.getDate("dataCheckOut").toString() : "N/A");
                    reserva.put("dataCancelamento", rs.getDate("dataCancelamento").toString());
                    reserva.put("status", rs.getString("status"));

                    reservasCanceladas.add(reserva); // Adiciona o mapa à lista
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservasCanceladas; // Retorna a lista de reservas canceladas
    }

    /**
     * Gera um relatório de ocupação das hospedagens (Cabana, Apartamento, Quarto).
     *
     * @return Mapa contendo a quantidade total de hospedagens, a quantidade de hospedagens reservadas e a porcentagem de ocupação.
     */
    public Map<String, Double> relatorioOcupacao() {
        Map<String, Double> relatorio = new HashMap<>();
        double totalHospedagens = 0;
        double totalReservadas = 0;

        // Consulta as três tabelas para calcular o total de hospedagens e as reservadas
        String[] tabelas = {"Cabana", "Apartamento", "Quarto"};
        for (String tabela : tabelas) {
            String sqlTotal = "SELECT COUNT(*) AS total FROM " + tabela;
            String sqlReservadas = "SELECT COUNT(*) AS reservadas FROM " + tabela + " WHERE reservado = true";

            try (Connection conn = Conexao.getConexao();
                 PreparedStatement psTotal = conn.prepareStatement(sqlTotal);
                 PreparedStatement psReservadas = conn.prepareStatement(sqlReservadas);
                 ResultSet rsTotal = psTotal.executeQuery();
                 ResultSet rsReservadas = psReservadas.executeQuery()) {

                // Soma o total de hospedagens
                if (rsTotal.next()) {
                    totalHospedagens += rsTotal.getDouble("total");
                }

                // Soma o total de hospedagens reservadas
                if (rsReservadas.next()) {
                    totalReservadas += rsReservadas.getDouble("reservadas");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Calcula a porcentagem de ocupação
        double porcentagemOcupacao = (totalReservadas / totalHospedagens) * 100;

        // Adiciona os resultados ao mapa
        relatorio.put("totalHospedagens", totalHospedagens);
        relatorio.put("totalReservadas", totalReservadas);
        relatorio.put("porcentagemOcupacao", porcentagemOcupacao);

        return relatorio;
    }

    public Map<String, Double> relatorioFinanceiro() {
        Map<String, Double> relatorio = new HashMap<>();
        double totalCobrancas = 0;

        // Consulta para somar o valor total das cobranças
        String sql = "SELECT SUM(valorTotal) AS totalCobrancas FROM Cobranca";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Recupera a soma total das cobranças
            if (rs.next()) {
                totalCobrancas = rs.getDouble("totalCobrancas");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Adiciona o resultado ao mapa
        relatorio.put("totalCobrancas", totalCobrancas);

        return relatorio;
    }

    public Map<String, Double> relatorioCliente(String cpfCliente) {
        Map<String, Double> relatorio = new HashMap<>();
        double totalGasto = 0;

        // Consulta para somar o valor total das cobranças do cliente
        String sql = "SELECT SUM(valorTotal) AS totalGasto FROM Cobranca WHERE cpf_cliente = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Define o parâmetro da consulta
            ps.setString(1, cpfCliente);

            try (ResultSet rs = ps.executeQuery()) {
                // Recupera a soma total das cobranças do cliente
                if (rs.next()) {
                    totalGasto = rs.getDouble("totalGasto");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Adiciona o resultado ao mapa
        relatorio.put(cpfCliente, totalGasto);

        return relatorio;
    }
}