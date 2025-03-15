package DAO;

import java.sql.PreparedStatement;

//import java.sql.ResultSet;
import conexao.Conexao;
import entidades.Funcionario;
import exceptions.FuncionarioInvalidoException;

/**
 * Classe responsável por realizar operações de banco de dados relacionadas à entidade Funcionário.
 * Inclui métodos para cadastrar, buscar, atualizar e excluir funcionários.
 * 
 * @author Angelo Pacheco
 * @author Renan Moreira
 */
public class FuncionarioDAO {

    /**
     * Cadastra um novo funcionário no banco de dados.
     * 
     * @param funcionario O funcionário a ser cadastrado.
     */
	public void cadastrarFuncionario(Funcionario funcionario) throws FuncionarioInvalidoException {
	    if (funcionario.getNome() == null || funcionario.getNome().isEmpty()) {
	        throw new FuncionarioInvalidoException("Nome do funcionário não pode ser vazio.");
	    }
	    if (funcionario.getCargo() == null || funcionario.getCargo().isEmpty()) {
	        throw new FuncionarioInvalidoException("Cargo do funcionário não pode ser vazio.");
	    }

	    String sql = "INSERT INTO Funcionario (nome, cargo, senha) VALUES (?, ?, ?)";

	    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
	        ps.setString(1, funcionario.getNome());
	        ps.setString(2, funcionario.getCargo());
	        ps.setInt(3, funcionario.getSenha());
	        ps.execute();
	        System.out.println("Funcionário cadastrado com sucesso!");
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new FuncionarioInvalidoException("Erro ao cadastrar o funcionário: " + e.getMessage());
	    }
	}

    /**
     * Busca um funcionário no banco de dados pelo código.
     * 
     * @param codFuncionario O código do funcionário a ser buscado.
     * @return Funcionario - O funcionário encontrado ou null se não existir.
     */
    /*
    public Funcionario buscarFuncionarioPorCodigo(int codFuncionario) {
        String sql = "SELECT * FROM Funcionario WHERE CodFuncionario = ?";
        Funcionario funcionario = null;

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, codFuncionario);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                funcionario = new Funcionario();
                funcionario.setCodFuncionario(resultado.getInt("CodFuncionario"));
                funcionario.setNome(resultado.getString("nome"));
                funcionario.setCargo(resultado.getString("cargo"));
                funcionario.setSenha(resultado.getInt("senha"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return funcionario;
    }
    */

    /**
     * Atualiza os dados de um funcionário no banco de dados.
     * 
     * @param funcionario O funcionário com os dados atualizados.
     */
    /*
    public void atualizarFuncionario(Funcionario funcionario) {
        String sql = "UPDATE Funcionario SET nome = ?, cargo = ?, senha = ? WHERE CodFuncionario = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCargo());
            ps.setInt(3, funcionario.getSenha());
            ps.setInt(4, funcionario.getCodFuncionario());
            ps.executeUpdate();
            System.out.println("Funcionário atualizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * Exclui um funcionário do banco de dados pelo código.
     * 
     * @param codFuncionario O código do funcionário a ser excluído.
     */
    /*
    public void excluirFuncionario(int codFuncionario) {
        String sql = "DELETE FROM Funcionario WHERE CodFuncionario = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, codFuncionario);
            ps.executeUpdate();
            System.out.println("Funcionário excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}