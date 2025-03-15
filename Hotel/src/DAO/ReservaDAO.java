package DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexao.Conexao;
import exceptions.CheckInInvalidoException;
import exceptions.HospedagemIndisponivelException;
import exceptions.ReservaNaoEncontradaException;
import exceptions.ServicoNaoPermitidoException;
import reservaControle.Reserva;

/**
 * Classe responsável por realizar operações de banco de dados relacionadas à entidade Reserva.
 * Inclui métodos para cadastrar reservas, verificar disponibilidade, atualizar status de disponibilidade
 * e realizar reservas.
 *
 * @author Angelo Pacheco
 * @author Renan Moreira
 */
public class ReservaDAO {

    /**
     * Cadastra uma nova reserva no banco de dados.
     *
     * @param reserva A reserva a ser cadastrada.
     * @return boolean - True se o cadastro for bem-sucedido, False caso contrário.
     */
    public boolean cadastrarReserva(Reserva reserva) {
        String sql = "INSERT INTO Reserva (cliente_cpf, idHospedagem, escolhaHospedagem, dataCheckIn, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, reserva.getCliente().getCpf());
            ps.setInt(2, reserva.getIdHospedagem());
            ps.setInt(3, reserva.getEscolhaHospedagem());
            ps.setDate(4, new Date(reserva.getDataCheckIn().getTime()));
            ps.setString(5, reserva.getStatus());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Reserva cadastrada com sucesso!");
                return true;
            } else {
                System.out.println("Falha ao cadastrar a reserva.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Verifica a disponibilidade de um quarto, cabana ou apartamento pelo ID e tipo de hospedagem.
     *
     * @param idHospedagem O ID da hospedagem.
     * @param escolhaHospedagem O tipo de hospedagem (1 = Quarto, 2 = Cabana, 3 = Apartamento).
     * @return boolean - True se a hospedagem estiver disponível, False caso contrário.
     */
    public boolean verificarDisponibilidade(int idHospedagem, int escolhaHospedagem) {
        String tabela = "";
        switch (escolhaHospedagem) {
            case 1:
                tabela = "Quarto";
                break;
            case 2:
                tabela = "Cabana";
                break;
            case 3:
                tabela = "Apartamento";
                break;
            default:
                System.out.println("Tipo de hospedagem inválido.");
                return false;
        }

        String sql = "SELECT reservado FROM " + tabela + " WHERE Idhospedagem = ?";
        boolean disponivel = false;

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idHospedagem);
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
     * Realiza uma reserva, verificando a disponibilidade, cadastrando a reserva e atualizando o status de disponibilidade.
     *
     * @param reserva A reserva a ser realizada.
     * @return boolean - True se a reserva for bem-sucedida, False caso contrário.
     */
    public boolean realizarReserva(Reserva reserva) throws HospedagemIndisponivelException, ServicoNaoPermitidoException {
        // Verifica a disponibilidade da hospedagem
        if (!verificarDisponibilidade(reserva.getIdHospedagem(), reserva.getEscolhaHospedagem())) {
            throw new HospedagemIndisponivelException("Hospedagem " + reserva.getIdHospedagem() + " não está disponível.");
        }

        // Define o status da reserva como "Reservada"
        reserva.setStatus("Reservada");

        // Cadastra a reserva no banco de dados
        if (cadastrarReserva(reserva)) {
            // Atualiza o status de disponibilidade da hospedagem para "reservado"
            boolean reservaRealizada = false;

            switch (reserva.getEscolhaHospedagem()) {
                case 1: // Quarto
                    QuartoDAO quartoDAO = new QuartoDAO();
                    reservaRealizada = quartoDAO.realizarReserva(reserva.getIdHospedagem());
                    break;
                case 2: // Cabana
                    CabanaDAO cabanaDAO = new CabanaDAO();
                    reservaRealizada = cabanaDAO.realizarReserva(reserva.getIdHospedagem());
                    break;
                case 3: // Apartamento
                    ApartamentoDAO apartamentoDAO = new ApartamentoDAO();
                    reservaRealizada = apartamentoDAO.realizarReserva(reserva.getIdHospedagem());
                    break;
                default:
                    throw new ServicoNaoPermitidoException("Tipo de hospedagem inválido.");
            }

            if (reservaRealizada) {
                System.out.println("Reserva confirmada e status de disponibilidade atualizado.");
                return true;
            } else {
                throw new ServicoNaoPermitidoException("Falha ao atualizar o status de disponibilidade.");
            }
        } else {
            throw new ServicoNaoPermitidoException("Falha ao cadastrar a reserva.");
        }
    }

    /**
     * Cancela uma reserva, desde que o cancelamento seja feito 24 horas antes da data de check-in.
     * Caso o cliente não compareça no dia do check-in, a reserva será cancelada automaticamente.
     *
     * @param idReserva O ID da reserva a ser cancelada.
     * @return boolean - True se o cancelamento for bem-sucedido, False caso contrário.
     */
    public boolean cancelarReserva(int idReserva) throws ReservaNaoEncontradaException {
        String sql = "SELECT dataCheckIn FROM Reserva WHERE idReserva = ?";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                Date dataCheckIn = resultado.getDate("dataCheckIn");
                long diferencaHoras = (dataCheckIn.getTime() - System.currentTimeMillis()) / (1000 * 60 * 60);

                if (diferencaHoras >= 24) {
                    // Cancela a reserva
                    String sqlCancelar = "UPDATE Reserva SET status = 'Cancelada' WHERE idReserva = ?";
                    try (PreparedStatement psCancelar = Conexao.getConexao().prepareStatement(sqlCancelar)) {
                        psCancelar.setInt(1, idReserva);
                        int linhasAfetadas = psCancelar.executeUpdate();

                        if (linhasAfetadas > 0) {
                            System.out.println("Reserva cancelada com sucesso.");
                            return true;
                        } else {
                            throw new ReservaNaoEncontradaException("Falha ao cancelar a reserva.");
                        }
                    }
                } else {
                    throw new ReservaNaoEncontradaException("Cancelamento permitido apenas 24 horas antes do check-in.");
                }
            } else {
                throw new ReservaNaoEncontradaException("Reserva não encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReservaNaoEncontradaException("Erro ao cancelar a reserva: " + e.getMessage());
        }
    }

    /**
     * Realiza o check-in de uma reserva, desde que seja feito a partir das 14 horas do dia do check-in.
     *
     * @param idReserva O ID da reserva.
     * @return boolean - True se o check-in for bem-sucedido, False caso contrário.
     */
    public boolean fazerCheckIn(int idReserva) throws CheckInInvalidoException, ReservaNaoEncontradaException {
        String sql = "SELECT dataCheckIn FROM Reserva WHERE idReserva = ?";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                Date dataCheckIn = resultado.getDate("dataCheckIn");
                long horaAtual = System.currentTimeMillis() % (1000 * 60 * 60 * 24) / (1000 * 60 * 60); // Hora atual (0-23)

                // Verifica se o check-in está sendo feito a partir das 14 horas
                if (horaAtual >= 14) {
                    // Realiza o check-in
                    String sqlCheckIn = "UPDATE Reserva SET status = 'Confirmado' WHERE idReserva = ?";
                    try (PreparedStatement psCheckIn = Conexao.getConexao().prepareStatement(sqlCheckIn)) {
                        psCheckIn.setInt(1, idReserva);
                        int linhasAfetadas = psCheckIn.executeUpdate();

                        if (linhasAfetadas > 0) {
                            System.out.println("Check-in realizado com sucesso.");
                            return true;
                        } else {
                            throw new ReservaNaoEncontradaException("Falha ao realizar o check-in.");
                        }
                    }
                } else {
                    throw new CheckInInvalidoException("Check-in permitido apenas a partir das 14 horas.");
                }
            } else {
                throw new ReservaNaoEncontradaException("Reserva não encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReservaNaoEncontradaException("Erro ao realizar o check-in: " + e.getMessage());
        }
    }

    /**
     * Realiza o check-out de uma reserva, atualizando a data de check-out para a data e hora atuais.
     * O check-out só é permitido a partir das 12 horas do dia do check-out.
     *
     * @param idReserva O ID da reserva.
     * @return boolean - True se o check-out for bem-sucedido, False caso contrário.
     */
    public boolean fazerCheckOut(int idReserva) {
        String sql = "SELECT dataCheckOut FROM Reserva WHERE idReserva = ?";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                Date dataCheckOutAtual = resultado.getDate("dataCheckOut");
                long horaAtual = System.currentTimeMillis() % (1000 * 60 * 60 * 24) / (1000 * 60 * 60); // Hora atual (0-23)

                // Verifica se o check-out está sendo feito a partir das 12 horas
                if (horaAtual >= 12) {
                    // Atualiza a data de check-out para a data e hora atuais
                    java.sql.Date dataHoraAtual = new java.sql.Date(System.currentTimeMillis());

                    String sqlCheckOut = "UPDATE Reserva SET dataCheckOut = ?, status = 'Finalizada' WHERE idReserva = ?";
                    try (PreparedStatement psCheckOut = Conexao.getConexao().prepareStatement(sqlCheckOut)) {
                        psCheckOut.setDate(1, dataHoraAtual);
                        psCheckOut.setInt(2, idReserva);
                        int linhasAfetadas = psCheckOut.executeUpdate();

                        if (linhasAfetadas > 0) {
                            System.out.println("Check-out realizado com sucesso. Data de check-out atualizada.");
                            return true;
                        } else {
                            System.out.println("Falha ao realizar o check-out.");
                        }
                    }
                } else {
                    System.out.println("Check-out permitido apenas a partir das 12 horas.");
                }
            } else {
                System.out.println("Reserva não encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Verifica se há uma reserva ativa para o CPF fornecido.
     * Uma reserva é considerada ativa se o status for "Confirmada" e a data de check-out ainda não tiver passado.
     *
     * @param cpf O CPF do cliente.
     * @return boolean - True se houver uma reserva ativa, False caso contrário.
     */
    public boolean verificarReservaAtiva(String cpf) {
        String sql = "SELECT COUNT(*) FROM Reserva WHERE cliente_cpf = ? AND status = 'Confirmada' AND dataCheckOut >= CURDATE()";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                int count = resultado.getInt(1); // Obtém o número de reservas ativas
                return count > 0; // Retorna true se houver pelo menos uma reserva ativa
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Retorna false em caso de erro ou se nenhuma reserva ativa for encontrada
    }

    public List<Map<String, String>> listarReservasAtivasPorCpf(String cpf) {
        List<Map<String, String>> reservasAtivas = new ArrayList<>();
        String sql = "SELECT idReserva, dataCheckIn, dataCheckOut FROM Reserva WHERE cliente_cpf = ? AND status = 'Confirmada' AND dataCheckOut >= CURDATE()";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet resultado = ps.executeQuery();

            while (resultado.next()) {
                Map<String, String> reserva = new HashMap<>();
                reserva.put("idReserva", String.valueOf(resultado.getInt("idReserva")));
                reserva.put("dataCheckIn", resultado.getString("dataCheckIn"));
                reserva.put("dataCheckOut", resultado.getString("dataCheckOut"));
                reservasAtivas.add(reserva);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservasAtivas;
    }

    public List<Map<String, String>> listarReservasReservadasPorCpf(String cpf) {
        List<Map<String, String>> reservasReservadas = new ArrayList<>();
        String sql = "SELECT idReserva, dataCheckIn, dataCheckOut FROM Reserva WHERE cliente_cpf = ? AND status = 'Reservada'";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet resultado = ps.executeQuery();

            while (resultado.next()) {
                Map<String, String> reserva = new HashMap<>();
                reserva.put("idReserva", String.valueOf(resultado.getInt("idReserva")));
                reserva.put("dataCheckIn", resultado.getString("dataCheckIn"));
                reserva.put("dataCheckOut", resultado.getString("dataCheckOut"));
                reservasReservadas.add(reserva);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservasReservadas;
    }

    //Criado para calcular cobranca
    public Map<String, Object> recuperarInformacoesHospedagem(int idReserva) {
        String sql = "SELECT h.idHospedagem, h.tipoHospedagem, h.precoDiaria " +
                "FROM Hospedagem h " +
                "JOIN Reserva r ON h.idHospedagem = r.idHospedagem " +
                "WHERE r.idReserva = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                Map<String, Object> informacoes = new HashMap<>();
                informacoes.put("idHospedagem", resultado.getInt("idHospedagem"));
                informacoes.put("tipoHospedagem", resultado.getString("tipoHospedagem"));
                informacoes.put("precoDiaria", resultado.getDouble("precoDiaria"));
                return informacoes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public double calcularValorHospedagem(int idReserva) {
        String sql = "SELECT DATEDIFF(dataCheckOut, dataCheckIn) AS dias, precoDiaria " +
                "FROM Reserva r " +
                "JOIN Hospedagem h ON r.idHospedagem = h.idHospedagem " +
                "WHERE r.idReserva = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                int dias = resultado.getInt("dias");
                double precoDiaria = resultado.getDouble("precoDiaria");
                return dias * precoDiaria;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double recuperarValorServicosAdicionais(int idReserva) {
        String sql = "SELECT SUM(valor) AS totalServicos " +
                "FROM ReservaServicoAdicional " +
                "WHERE idReserva = ?";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                return resultado.getDouble("totalServicos");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}