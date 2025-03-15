package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import exceptions.HospedagemIndisponivelException;
import exceptions.ReservaNaoEncontradaException;
import exceptions.ServicoNaoPermitidoException;
import servicoAdicional.Transfer;

public class TransferDAO {
	public boolean cadastrarReservaServicoAdicional(String cpfCliente, int idServicoAdicional, int quantidadePessoas, double valorTotal) 
		    throws HospedagemIndisponivelException, ServicoNaoPermitidoException {
		    
		    if (!verificarDisponibilidade(idServicoAdicional)) {
		        throw new HospedagemIndisponivelException("Transfer não está disponível.");
		    }

		    String sqlInsert = "INSERT INTO ReservaServicoAdicional (cpf_cliente, tipoServico, quantidadeDePessoas, precoPorPessoa, valorTotal) VALUES (?, 'transfer', ?, ?, ?)";
		    String sqlUpdate = "UPDATE Transfer SET quantidadeDePessoas = quantidadeDePessoas - ? WHERE idServicoAdicional = ? AND quantidadeDePessoas >= ?";

		    try (Connection conn = Conexao.getConexao()) {
		        conn.setAutoCommit(false); // Inicia transação

		        try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
		             PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {

		            // Insere na tabela ReservaServicoAdicional
		            psInsert.setString(1, cpfCliente);
		            psInsert.setInt(2, quantidadePessoas);
		            psInsert.setDouble(3, valorTotal / quantidadePessoas); // Preço por pessoa
		            psInsert.setDouble(4, valorTotal);
		            psInsert.executeUpdate();

		            // Atualiza a quantidade de pessoas na tabela Transfer
		            psUpdate.setInt(1, quantidadePessoas);
		            psUpdate.setInt(2, idServicoAdicional);
		            psUpdate.setInt(3, quantidadePessoas);
		            int rowsUpdated = psUpdate.executeUpdate();

		            if (rowsUpdated > 0) {
		                conn.commit(); // Confirma a transação
		                return true;
		            } else {
		                conn.rollback(); // Desfaz a transação
		                throw new ServicoNaoPermitidoException("Não foi possível reservar o serviço adicional.");
		            }
		        } catch (Exception e) {
		            conn.rollback(); // Desfaz a transação em caso de erro
		            e.printStackTrace();
		            throw new ServicoNaoPermitidoException("Erro ao reservar o serviço adicional: " + e.getMessage());
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new ServicoNaoPermitidoException("Erro ao reservar o serviço adicional: " + e.getMessage());
		    }
		}

    public boolean cadastrarTransfer(Transfer transfer) {
        String sql = "INSERT INTO Transfer (destino, quantidadeDePessoas, precoPorPessoa) VALUES (?, ?, ?)";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, transfer.getDestino());
            ps.setInt(2, transfer.getQuantidadeDePessoas());
            ps.setDouble(3, transfer.getPrecoPorPessoa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean cancelarReserva(int idReservaServicoAdicional) throws ReservaNaoEncontradaException {
        String sqlDelete = "DELETE FROM ReservaServicoAdicional WHERE idReservaServicoAdicional = ?";
        String sqlUpdate = "UPDATE Transfer SET quantidadeDePessoas = quantidadeDePessoas + ? WHERE idServicoAdicional = ?";

        try (Connection conn = Conexao.getConexao()) {
            conn.setAutoCommit(false); // Inicia transação

            try (PreparedStatement psSelect = conn.prepareStatement("SELECT quantidadeDePessoas, idServicoAdicional FROM ReservaServicoAdicional WHERE idReservaServicoAdicional = ?");
                 PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
                 PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {

                // Obtém os dados da reserva
                psSelect.setInt(1, idReservaServicoAdicional);
                ResultSet rs = psSelect.executeQuery();
                if (rs.next()) {
                    int quantidadePessoas = rs.getInt("quantidadeDePessoas");
                    int idServicoAdicional = rs.getInt("idServicoAdicional");

                    // Exclui a reserva
                    psDelete.setInt(1, idReservaServicoAdicional);
                    psDelete.executeUpdate();

                    // Retorna a quantidade de pessoas ao valor original
                    psUpdate.setInt(1, quantidadePessoas);
                    psUpdate.setInt(2, idServicoAdicional);
                    int rowsUpdated = psUpdate.executeUpdate();

                    if (rowsUpdated > 0) {
                        conn.commit(); // Confirma a transação
                        return true;
                    } else {
                        conn.rollback(); // Desfaz a transação
                        throw new ReservaNaoEncontradaException("Reserva não encontrada ou já cancelada.");
                    }
                } else {
                    conn.rollback(); // Desfaz a transação se a reserva não for encontrada
                    throw new ReservaNaoEncontradaException("Reserva não encontrada.");
                }
            } catch (Exception e) {
                conn.rollback(); // Desfaz a transação em caso de erro
                e.printStackTrace();
                throw new ReservaNaoEncontradaException("Erro ao cancelar a reserva: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReservaNaoEncontradaException("Erro ao cancelar a reserva: " + e.getMessage());
        }
    }
    
    public double calcularValor(int idServicoAdicional, int quantidadePessoas) {
        String sql = "SELECT precoPorPessoa FROM Transfer WHERE idServicoAdicional = ?";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idServicoAdicional);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("precoPorPessoa") * quantidadePessoas;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public List<Transfer> listarTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM Transfer";
        try (Statement stmt = Conexao.getConexao().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transfers.add(new Transfer(
                        rs.getInt("idServicoAdicional"),
                        rs.getString("destino"),
                        rs.getInt("quantidadeDePessoas"),
                        rs.getDouble("precoPorPessoa")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transfers;
    }

    public boolean verificarDisponibilidade(int idServicoAdicional) {
        String sql = "SELECT reservado FROM Transfer WHERE idServicoAdicional = ?";
        boolean disponivel = false;

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idServicoAdicional);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                disponivel = !resultado.getBoolean("reservado"); // Retorna true se não estiver reservado
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disponivel;
    }
}