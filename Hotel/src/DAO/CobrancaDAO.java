package DAO;

import conexao.Conexao;

import java.sql.PreparedStatement;

public class CobrancaDAO {


    public static boolean cadastrarCobranca(String cpfCliente, double valorTotal) {
    String sql = "INSERT INTO Cobranca (cpf_cliente, valorTotal, dataGeracao) VALUES (?, ?, ?)";

    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
        ps.setString(1, cpfCliente);
        ps.setDouble(2, valorTotal);
        ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
        int linhasAfetadas = ps.executeUpdate();

        return linhasAfetadas > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
}
