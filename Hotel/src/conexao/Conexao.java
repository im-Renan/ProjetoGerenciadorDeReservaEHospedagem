package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados MySQL.
 * Utiliza o padrão Singleton para garantir uma única instância de conexão.
 * 
 * @author Angelo Pacheco
 * @author Renan Moreira
 */
public class Conexao {
    private static final String url = "jdbc:mysql://localhost:3306/gerenciador_reserva_e_hospedagem"; // URL do banco de dados
    private static final String user = "root"; // Usuário do banco de dados
    private static final String password = "ramonfiles32"; // Senha do banco de dados

    private static Connection conn; // Instância única da conexão

    /**
     * Retorna uma conexão ativa com o banco de dados.
     * Se a conexão não existir ou estiver fechada, uma nova conexão é criada.
     * 
     * @return Connection - Objeto de conexão com o banco de dados.
     */
    public static Connection getConexao() {
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Verifica se a conexão está nula ou fechada
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password);
            }
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
