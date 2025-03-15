package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import java.sql.ResultSet;
import conexao.Conexao;
import entidades.Cliente;
import exceptions.ServicoNaoPermitidoException;

/**
 * Classe responsável por realizar operações de banco de dados relacionadas à entidade Cliente.
 * Inclui métodos para cadastrar, atualizar, buscar e excluir clientes.
 * 
 * @author Angelo Pacheco
 * @author Renan Moreira
 */
public class ClientesDAO {

    /**
     * Cadastra um novo cliente no banco de dados.
     * 
     * @param cliente O cliente a ser cadastrado.
     */
    public void cadastrarUsuario(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, telefone) VALUES (?, ?, ?)";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.execute();
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Verifica se um cliente existe no banco de dados pelo CPF.
     * 
     * @param cpf O CPF do cliente.
     * @return boolean - True se o cliente existir, False caso contrário.
     * @throws ServicoNaoPermitidoException Se o cliente não tiver uma reserva ativa.
     */
    public boolean verificarExistenciaCliente(String cpf) throws ServicoNaoPermitidoException {
        String sql = "SELECT COUNT(*) FROM cliente WHERE cpf = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                int count = resultado.getInt(1); // Obtém o número de registros encontrados
                if (count == 0) {
                    throw new ServicoNaoPermitidoException("Cliente não encontrado ou sem reserva ativa.");
                }
                return count > 0; // Retorna true se pelo menos um registro for encontrado
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Retorna false em caso de erro ou se nenhum registro for encontrado
    }


    public Cliente buscarClientePorCpf(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        Cliente cliente = null;

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                cliente = new Cliente();
                cliente.setNome(resultado.getString("nome"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setTelefone(resultado.getString("telefone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;
    }

    /**
     * Atualiza os dados de um cliente no banco de dados.
     * 
     * @param cliente O cliente com os dados atualizados.
     */
    /*
    public void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, telefone = ? WHERE cpf = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getCpf());
            ps.executeUpdate();
            System.out.println("Cliente atualizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * Busca um cliente no banco de dados pelo CPF.
     * 
     * @param cpf O CPF do cliente a ser buscado.
     * @return Cliente - O cliente encontrado ou null se não existir.
     */


    /**
     * Exclui um cliente do banco de dados pelo CPF.
     * 
     * @param cpf O CPF do cliente a ser excluído.
     */
    /*
    public void excluirCliente(String cpf) {
        String sql = "DELETE FROM cliente WHERE cpf = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ps.executeUpdate();
            System.out.println("Cliente excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}