package reservaControle;

import entidades.Cliente;
import java.util.Date;
import DAO.ReservaDAO;

/**
 * Classe que representa uma reserva no sistema.
 * Contém informações como ID da reserva, cliente associado, ID da hospedagem,
 * tipo de hospedagem, datas de check-in e check-out, e status da reserva.
 * 
 * @author Angelo Pacheco
 * @author Renan Moreira
 */
public class Reserva {
    private int idReserva; // Identificador único da reserva
    private Cliente cliente; // Cliente associado à reserva (usando o CPF)
    private int idHospedagem; // ID da hospedagem (Quarto, Cabana ou Apartamento)
    private int escolhaHospedagem; // Tipo de hospedagem (Quarto, Cabana ou Apartamento)
    private Date dataCheckIn; // Data de check-in
    private Date dataCheckOut; // Data de check-out
    private String status; // Status da reserva (Confirmada, Pendente, Cancelada, Concluída)

    /**
     * Construtor da classe Reserva.
     * 
     * @param idReserva O ID da reserva.
     * @param cliente O cliente associado à reserva.
     * @param idHospedagem O ID da hospedagem.
     * @param escolhaHospedagem O tipo de hospedagem (1 = Quarto, 2 = Cabana, 3 = Apartamento).
     * @param dataCheckIn A data de check-in.
     * @param status O status da reserva.
     */
    public Reserva(int idReserva, Cliente cliente, int idHospedagem, int escolhaHospedagem, Date dataCheckIn, String status) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.idHospedagem = idHospedagem;
        this.escolhaHospedagem = escolhaHospedagem;
        this.dataCheckIn = dataCheckIn;
        this.status = status;
        this.dataCheckOut = null; // Inicializa dataCheckOut como null
    }

    /**
     * Retorna o ID da reserva.
     * 
     * @return int - O ID da reserva.
     */
    public int getIdReserva() {
        return idReserva;
    }

    /**
     * Define o ID da reserva.
     * 
     * @param idReserva O ID da reserva.
     */
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    /**
     * Retorna o cliente associado à reserva.
     * 
     * @return Cliente - O cliente associado à reserva.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o cliente associado à reserva.
     * 
     * @param cliente O cliente associado à reserva.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna o ID da hospedagem.
     * 
     * @return int - O ID da hospedagem.
     */
    public int getIdHospedagem() {
        return idHospedagem;
    }

    /**
     * Define o ID da hospedagem.
     * 
     * @param idHospedagem O ID da hospedagem.
     */
    public void setIdHospedagem(int idHospedagem) {
        this.idHospedagem = idHospedagem;
    }

    /**
     * Retorna o tipo de hospedagem.
     * 
     * @return int - O tipo de hospedagem (1 = Quarto, 2 = Cabana, 3 = Apartamento).
     */
    public int getEscolhaHospedagem() {
        return escolhaHospedagem;
    }

    /**
     * Define o tipo de hospedagem.
     * 
     * @param escolhaHospedagem O tipo de hospedagem (1 = Quarto, 2 = Cabana, 3 = Apartamento).
     */
    public void setEscolhaHospedagem(int escolhaHospedagem) {
        this.escolhaHospedagem = escolhaHospedagem;
    }

    /**
     * Retorna a data de check-in.
     * 
     * @return Date - A data de check-in.
     */
    public Date getDataCheckIn() {
        return dataCheckIn;
    }

    /**
     * Define a data de check-in.
     * 
     * @param dataCheckIn A data de check-in.
     */
    public void setDataCheckIn(Date dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    /**
     * Retorna a data de check-out.
     * 
     * @return Date - A data de check-out.
     */
    public Date getDataCheckOut() {
        return dataCheckOut;
    }

    /**
     * Define a data de check-out.
     * 
     * @param dataCheckOut A data de check-out.
     */
    public void setDataCheckOut(Date dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    /**
     * Retorna o status da reserva.
     * 
     * @return String - O status da reserva (Confirmada, Pendente, Cancelada, Concluída).
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define o status da reserva.
     * 
     * @param status O status da reserva (Confirmada, Pendente, Cancelada, Concluída).
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Realiza a reserva utilizando o ReservaDAO.
     * 
     * @return boolean - True se a reserva for bem-sucedida, False caso contrário.
     */
    public boolean realizarReserva() {
        ReservaDAO reservaDAO = new ReservaDAO();
        try {
            return reservaDAO.realizarReserva(this);
        } catch (Exception e) {
            e.printStackTrace(); // Trata exceções, como problemas de banco de dados
            return false;
        }
    }

    /**
     * Retorna uma representação em string do objeto Reserva.
     * 
     * @return String - Representação da reserva no formato "Reserva{idReserva=..., cliente=..., idHospedagem=..., escolhaHospedagem=..., dataCheckIn=..., dataCheckOut=..., status=...}".
     */
    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", cliente=" + cliente +
                ", idHospedagem=" + idHospedagem +
                ", escolhaHospedagem=" + escolhaHospedagem +
                ", dataCheckIn=" + dataCheckIn +
                ", dataCheckOut=" + dataCheckOut +
                ", status='" + status + '\'' +
                '}';
    }
}