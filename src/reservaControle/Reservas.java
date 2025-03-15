package reservaControle;

import exceptions.CheckInInvalidoException;
import exceptions.HospedagemIndisponivelException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reservas {
    private int idReserva;
    private int idCliente;
    private int numeroQuarto;
    private boolean checkIn;
    private boolean checkOut;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private LocalTime horaRegistro;

    private static List<Integer> quartosDisponiveis = new ArrayList<>();
    private static List<Integer> quartosIndisponiveis = new ArrayList<>();
    
    Scanner scanner = new Scanner(System.in);
    
    public Reservas(int idReserva, int idCliente, int numeroQuarto) throws HospedagemIndisponivelException {
        if (!verificarDisponibilidadeQuarto(numeroQuarto)) {
            throw new HospedagemIndisponivelException("Quarto " + numeroQuarto + " indisponível.");
        }

        this.horaRegistro = LocalTime.now();

        System.out.print("Digite a data de check-in (yyyy-MM-dd): ");
        String dataCheckInStr = scanner.next();

        try {
            this.dataCheckIn = LocalDate.parse(dataCheckInStr);
            this.dataCheckOut = dataCheckIn.plusDays(1);

            reservarQuarto(numeroQuarto);

            this.checkIn = false;
            this.checkOut = false;

            System.out.println("\nReserva criada com sucesso!");
            System.out.println("Hora do registro: " + this.horaRegistro);
            System.out.println("Data de check-in: " + this.dataCheckIn + " às 14:00");
            System.out.println("Data de check-out: " + this.dataCheckOut + " às 12:00");

        } catch (Exception e) {
            System.out.println("Formato de data inválido. Use o formato yyyy-MM-dd.");
        }
    }

    private boolean verificarDisponibilidadeQuarto(int numeroQuarto) {
        return quartosDisponiveis.contains(numeroQuarto);
    }

    private void reservarQuarto(int numeroQuarto) {
        quartosDisponiveis.remove(Integer.valueOf(numeroQuarto));
        quartosIndisponiveis.add(numeroQuarto);
    }

    private void liberarQuarto(int numeroQuarto) {
        quartosIndisponiveis.remove(Integer.valueOf(numeroQuarto));
        quartosDisponiveis.add(numeroQuarto);
    }

    public static void adicionarQuartoDisponivel(int numeroQuarto) {
        quartosDisponiveis.add(numeroQuarto);
    }

    public boolean realizarCheckIn() throws CheckInInvalidoException {
        LocalTime agora = LocalTime.now();
        LocalTime checkInHora = LocalTime.of(14, 0);

        if (!checkIn && agora.isAfter(checkInHora)) {
            this.checkIn = true;
            System.out.println("Check-in realizado com sucesso para a reserva " + idReserva);
            return true;
        } else {
            throw new CheckInInvalidoException("O check-in só pode ser realizado a partir das 14h do dia da reserva.");
        }
    }

    public boolean realizarCheckOut() {
        LocalTime agora = LocalTime.now();
        LocalTime checkOutHora = LocalTime.of(12, 0);

        if (!LocalDate.now().equals(dataCheckIn)) {
            System.out.println("Check-in só pode ser realizado na data prevista!");
            return false;
        }

        if (checkIn && !checkOut && agora.isBefore(checkOutHora)) {
            this.checkOut = true;
            System.out.println("Check-out realizado com sucesso para a reserva " + idReserva);
            return true;
        } else {
            System.out.println("O check-out deve ser realizado até as 12h do dia de saída.");
            return false;
        }
    }

    public boolean cancelarReserva() {
        if (!checkIn) {
            liberarQuarto(this.numeroQuarto);
            System.out.println("Reserva " + idReserva + " cancelada com sucesso.");
            return true;
        } else {
            System.out.println("Não é possível cancelar a reserva " + idReserva + " porque o check-in já foi realizado.");
            return false;
        }
    }

    // Getters e Setters
    public int getIdReserva() {
        return idReserva;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getNumeroQuarto() {
        return numeroQuarto;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public LocalTime getHoraRegistro() {
        return horaRegistro;
    }
}