import java.util.List;
import java.util.Map;
import java.util.Scanner;

import DAO.ApartamentoDAO;
import DAO.CabanaDAO;
import DAO.ClientesDAO;
import DAO.CobrancaDAO;
import DAO.FuncionarioDAO;
import DAO.PasseiosTuristicosDAO;
import DAO.QuartoDAO;
import DAO.RelatorioDAO;
import DAO.ReservaDAO;
import DAO.TransferDAO;
import entidades.Cliente;
import entidades.Funcionario;
import exceptions.CheckInInvalidoException;
import exceptions.FuncionarioInvalidoException;
import exceptions.HospedagemIndisponivelException;
import exceptions.ReservaNaoEncontradaException;
import exceptions.ServicoNaoPermitidoException;
import hospedagem.Apartamento;
import hospedagem.Cabana;
import hospedagem.Quarto;
import reservaControle.Reserva;
import servicoAdicional.PasseiosTuristicos;
import servicoAdicional.Transfer;

public class App {
    public static void main(String[] args) throws ServicoNaoPermitidoException, ReservaNaoEncontradaException {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("===== MENU =====");
            System.out.println("1 - Cadastrar Cliente"); //Ok, mas falta exceções
            System.out.println("2 - Cadastrar Funcionário");//Ok, mas falta exceções
            System.out.println("3 - Cadastro e gerenciamento de hospedagens e serviços adicionais");//Ok, mas falta exceções e serviços adicionas
            System.out.println("4 - Realização de reservas de hospedagem e serviços adicionais");
            System.out.println("5 - Consulta de disponibilidade de hospedagem e serviços");
            System.out.println("6 - Cancelamento de reservas");
            System.out.println("7 - Realização de check-in");
            System.out.println("8 - Realização de check-out e fechamento da conta");
            System.out.println("9 - Geração de relatórios");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarCliente(scanner);
                    break;
                case 2:
                    try {
                        cadastrarFuncionario(scanner);
                    } catch (FuncionarioInvalidoException e) {
                        System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
                    }
                    break;
                case 3:
                    gerenciarHospedagens(scanner);
                    break;
                case 4:
                    try {
                        realizarReservaHospedagem(scanner);
                    } catch (HospedagemIndisponivelException | ReservaNaoEncontradaException e) {
                        System.out.println("Erro ao realizar reserva: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        consultarDisponibilidade(scanner);
                    } catch (HospedagemIndisponivelException e) {
                        System.out.println("Erro ao consultar disponibilidade: " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        cancelarReserva(scanner);
                    } catch (ReservaNaoEncontradaException e) {
                        System.out.println("Erro ao cancelar reserva: " + e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        fazerCheckIn(scanner);
                    } catch (CheckInInvalidoException e) {
                        System.out.println("Erro ao realizar check-in: " + e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        fazerCheckOut(scanner);
                    } catch (ReservaNaoEncontradaException e) {
                        System.out.println("Erro ao realizar check-out: " + e.getMessage());
                    }
                    break;
                case 9:
                    gerenciarRelatorio();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
    //cadastrar cliente
    public static void cadastrarCliente(Scanner scanner) {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine();

        Cliente u = new Cliente();
        u.setNome(nome);
        u.setCpf(cpf);
        u.setTelefone(telefone);

        new ClientesDAO().cadastrarUsuario(u);

    }
    //cadastrar funcionario
    public static void cadastrarFuncionario(Scanner scanner) throws FuncionarioInvalidoException {
        System.out.print("Digite o nome do funcionário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o cargo do funcionário: ");
        String cargo = scanner.nextLine();

        System.out.print("Digite a senha do funcionário: ");
        int senha = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        Funcionario f = new Funcionario();
        f.setNome(nome);
        f.setCargo(cargo);
        f.setSenha(senha);

        new FuncionarioDAO().cadastrarFuncionario(f);
    }
    //gerenciar quarto, listar, cadastro e verificação de disponibilidade
    public static void gerenciarHospedagens(Scanner scanner) {
        int opcaoHospedagem;

        do {
            System.out.println("===== GESTÃO DE HOSPEDAGENS =====");
            System.out.println("1 - Apartamento");
            System.out.println("2 - Cabana");
            System.out.println("3 - Quarto");
            System.out.println("4 - Serviços Adicionais");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcaoHospedagem = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcaoHospedagem) {
                case 1:
                    gerenciarApartamento(scanner);
                    break;
                case 2:
                    gerenciarCabana(scanner);
                    break;
                case 3:
                    gerenciarQuarto(scanner);
                    break;
                case 4:
                    gerenciarServicoAdicional(scanner); // Novo método para gerenciar serviços adicionais
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoHospedagem != 0);
    }
    //gerenciar quarto, listar, cadastro e verificação de disponibilidade
    public static void gerenciarApartamento(Scanner scanner) {
        int opcaoApartamento;

        do {
            System.out.println("===== GESTÃO DE APARTAMENTOS =====");
            System.out.println("1 - Listar Apartamentos");
            System.out.println("2 - Cadastrar Apartamento");
            System.out.println("3 - Verificar Disponibilidade");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcaoApartamento = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcaoApartamento) {
                case 1:
                    listarApartamentos(scanner);
                    break;
                case 2:
                    cadastrarApartamento(scanner);
                    break;
                case 3:
                    verificarDisponibilidadeApartamento(scanner);
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoApartamento != 0);
    }
    //gerenciar quarto, listar, cadastro e verificação de disponibilidade
    public static void gerenciarCabana(Scanner scanner) {
        int opcaoCabana;

        do {
            System.out.println("===== GESTÃO DE CABANAS =====");
            System.out.println("1 - Listar Cabanas");
            System.out.println("2 - Cadastrar Cabana");
            System.out.println("3 - Verificar Disponibilidade");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcaoCabana = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcaoCabana) {
                case 1:
                    listarCabanas(scanner);
                    break;
                case 2:
                    cadastrarCabana(scanner);
                    break;
                case 3:
                    verificarDisponibilidadeCabana(scanner);
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoCabana != 0);
    }
    //cadastrar, listar e verificar disponibilidade
    private static void gerenciarServicoAdicional(Scanner scanner) {
        int opcaoServico;

        do {
            System.out.println("===== GESTÃO DE SERVIÇOS ADICIONAIS =====");
            System.out.println("1 - Transfers");
            System.out.println("2 - Passeios");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcaoServico = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcaoServico) {
                case 1:
                    gerenciarTransfers(scanner); // Método para gerenciar transfers
                    break;
                case 2:
                    gerenciarPasseios(scanner); // Método para gerenciar passeios
                    break;
                case 0:
                    System.out.println("Voltando ao menu anterior...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoServico != 0);
    }
    //cadastrar, listar e verificar disponibilidade
    private static void gerenciarTransfers(Scanner scanner) {
        int opcaoTransfer;

        do {
            System.out.println("===== GESTÃO DE TRANSFERS =====");
            System.out.println("1 - Listar Transfers");
            System.out.println("2 - Cadastrar Transfer");
            System.out.println("3 - Verificar Disponibilidade de Transfer");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcaoTransfer = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            TransferDAO transferDAO = new TransferDAO();

            switch (opcaoTransfer) {
                case 1:
                    // Listar todos os transfers
                    List<Transfer> transfers = transferDAO.listarTransfers();
                    for (Transfer transfer : transfers) {
                        System.out.println("Transfer ID: " + transfer.getIdservicoAdicional() +
                                ", Destino: " + transfer.getDestino() +
                                ", Quantidade de Pessoas: " + transfer.getQuantidadeDePessoas() +
                                ", Preço por Pessoa: " + transfer.getPrecoPorPessoa());
                    }
                    break;
                case 2:
                    // Cadastrar um novo transfer
                    System.out.print("Digite o destino do transfer: ");
                    String destino = scanner.nextLine();
                    System.out.print("Digite a quantidade de pessoas: ");
                    int quantidadeDePessoas = scanner.nextInt();
                    System.out.print("Digite o preço por pessoa: ");
                    double precoPorPessoa = scanner.nextDouble();
                    scanner.nextLine(); // Consumir a quebra de linha

                    Transfer novoTransfer = new Transfer(0, destino, quantidadeDePessoas, precoPorPessoa);
                    if (transferDAO.cadastrarTransfer(novoTransfer)) {
                        System.out.println("Transfer cadastrado com sucesso!");
                    } else {
                        System.out.println("Falha ao cadastrar o transfer.");
                    }
                    break;
                case 3:
                    // Verificar disponibilidade de um transfer pelo ID
                    System.out.print("Digite o ID do transfer: ");
                    int idTransfer = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha

                    if (transferDAO.verificarDisponibilidade(idTransfer)) {
                        System.out.println("Transfer " + idTransfer + " está disponível.");
                    } else {
                        System.out.println("Transfer " + idTransfer + " não está disponível.");
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao menu anterior...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoTransfer != 0);
    }
    //cadastrar, listar e verificar disponibilidade
    private static void gerenciarPasseios(Scanner scanner) {
        int opcaoPasseio;

        do {
            System.out.println("===== GESTÃO DE PASSEIOS TURÍSTICOS =====");
            System.out.println("1 - Listar Passeios");
            System.out.println("2 - Cadastrar Passeio");
            System.out.println("3 - Verificar Disponibilidade de Passeio");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcaoPasseio = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            PasseiosTuristicosDAO passeioDAO = new PasseiosTuristicosDAO();

            switch (opcaoPasseio) {
                case 1:
                    // Listar todos os passeios
                    List<PasseiosTuristicos> passeios = passeioDAO.listarPasseios();
                    for (PasseiosTuristicos passeio : passeios) {
                        System.out.println("Passeio ID: " + passeio.getIdservicoAdicional() +
                                ", Destino: " + passeio.getDestino() +
                                ", Quantidade de Pessoas: " + passeio.getQuantidadeDePessoas() +
                                ", Preço por Pessoa: " + passeio.getPrecoPorPessoa());
                    }
                    break;
                case 2:
                    // Cadastrar um novo passeio
                    System.out.print("Digite o destino do passeio: ");
                    String destino = scanner.nextLine();
                    System.out.print("Digite a quantidade de pessoas: ");
                    int quantidadeDePessoas = scanner.nextInt();
                    System.out.print("Digite o preço por pessoa: ");
                    double precoPorPessoa = scanner.nextDouble();
                    scanner.nextLine(); // Consumir a quebra de linha

                    PasseiosTuristicos novoPasseio = new PasseiosTuristicos(0, destino, quantidadeDePessoas, precoPorPessoa);
                    if (passeioDAO.cadastrarPasseio(novoPasseio)) {
                        System.out.println("Passeio cadastrado com sucesso!");
                    } else {
                        System.out.println("Falha ao cadastrar o passeio.");
                    }
                    break;
                case 3:
                    // Verificar disponibilidade de um passeio pelo ID
                    System.out.print("Digite o ID do passeio: ");
                    int idPasseio = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha

                    if (passeioDAO.verificarDisponibilidade(idPasseio)) {
                        System.out.println("Passeio " + idPasseio + " está disponível.");
                    } else {
                        System.out.println("Passeio " + idPasseio + " não está disponível.");
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao menu anterior...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoPasseio != 0);
    }
    //gerenciar quarto, listar, cadastro e verificação de disponibilidade
    public static void gerenciarQuarto(Scanner scanner) {
        int opcaoQuarto;

        do {
            System.out.println("===== GESTÃO DE QUARTOS =====");
            System.out.println("1 - Listar Quartos");
            System.out.println("2 - Cadastrar Quarto");
            System.out.println("3 - Verificar Disponibilidade");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcaoQuarto = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcaoQuarto) {
                case 1:
                    listarQuartos(scanner);
                    break;
                case 2:
                    cadastrarQuarto(scanner);
                    break;
                case 3:
                    verificarDisponibilidadeQuarto(scanner);
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoQuarto != 0);
    }
    // Métod de cadastro
    public static void cadastrarApartamento(Scanner scanner) {
        System.out.print("Digite a capacidade máxima do apartamento: ");
        int capacidadeMaxima = scanner.nextInt();

        System.out.print("Digite o preço da diária do apartamento: ");
        double precoDiaria = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Apartamento apartamento = new Apartamento(0, capacidadeMaxima, precoDiaria); // ID será auto-incrementado
        ApartamentoDAO apartamentoDAO = new ApartamentoDAO();
        apartamentoDAO.cadastrarApartamento(apartamento);
    }
    // Mtodo de cadastro
    public static void cadastrarCabana(Scanner scanner) {
        System.out.print("Digite a capacidade máxima da cabana: ");
        int capacidadeMaxima = scanner.nextInt();

        System.out.print("Digite o preço da diária da cabana: ");
        double precoDiaria = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Cabana cabana = new Cabana(0, capacidadeMaxima, precoDiaria); // ID será auto-incrementado
        CabanaDAO cabanaDAO = new CabanaDAO();
        cabanaDAO.cadastrarCabana(cabana);
    }
    // Métodos de cadastro
    public static void cadastrarQuarto(Scanner scanner) {
        System.out.print("Digite a capacidade máxima do quarto: ");
        int capacidadeMaxima = scanner.nextInt();

        System.out.print("Digite o preço da diária do quarto: ");
        double precoDiaria = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Quarto quarto = new Quarto(0, capacidadeMaxima, precoDiaria); // ID será auto-incrementado
        QuartoDAO quartoDAO = new QuartoDAO();
        quartoDAO.cadastrarQuarto(quarto);
    }
    // Métodos fictícios para verificar a disponibilidade
    public static void verificarDisponibilidadeApartamento(Scanner scanner) {
        System.out.print("Digite o ID do apartamento: ");
        int idHospedagem = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        ApartamentoDAO apartamentoDAO = new ApartamentoDAO();
        boolean disponivel = apartamentoDAO.verificarDisponibilidade(idHospedagem);

        if (disponivel) {
            System.out.println("O apartamento " + idHospedagem + " está disponível.");
        } else {
            System.out.println("O apartamento " + idHospedagem + " não está disponível.");
        }
    }
    //listar apartamentosd cabana
    public static void verificarDisponibilidadeCabana(Scanner scanner) {
        System.out.print("Digite o ID da cabana: ");
        int idHospedagem = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        CabanaDAO cabanaDAO = new CabanaDAO();
        boolean disponivel = cabanaDAO.verificarDisponibilidade(idHospedagem);

        if (disponivel) {
            System.out.println("A cabana " + idHospedagem + " está disponível.");
        } else {
            System.out.println("A cabana " + idHospedagem + " não está disponível.");
        }
    }
    //verificar disponibilidade quarto
    public static void verificarDisponibilidadeQuarto(Scanner scanner) {
        System.out.print("Digite o ID do quarto: ");
        int idHospedagem = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        QuartoDAO quartoDAO = new QuartoDAO();
        boolean disponivel = quartoDAO.verificarDisponibilidade(idHospedagem);

        if (disponivel) {
            System.out.println("O quarto " + idHospedagem + " está disponível.");
        } else {
            System.out.println("O quarto " + idHospedagem + " não está disponível.");
        }
    }
    //listarquarto
    public static void listarQuartos(Scanner scanner) {
        QuartoDAO quartoDAO = new QuartoDAO();
        List<Quarto> quartos = quartoDAO.listarQuartos();

        if (quartos.isEmpty()) {
            System.out.println("Nenhum quarto cadastrado.");
        } else {
            System.out.println("Lista de Quartos:");
            for (Quarto quarto : quartos) {
                System.out.println(quarto); // Depende do método toString() da classe Quarto
            }
        }
    }
    //listar cabanas
    public static void listarCabanas(Scanner scanner) {
        CabanaDAO cabanaDAO = new CabanaDAO();
        List<Cabana> cabanas = cabanaDAO.listarCabanas();

        if (cabanas.isEmpty()) {
            System.out.println("Nenhuma cabana cadastrada.");
        } else {
            System.out.println("Lista de Cabanas:");
            for (Cabana cabana : cabanas) {
                System.out.println(cabana); // Depende do método toString() da classe Cabana
            }
        }
    }
    //listar apartamentosd
    public static void listarApartamentos(Scanner scanner) {
        ApartamentoDAO apartamentoDAO = new ApartamentoDAO();
        List<Apartamento> apartamentos = apartamentoDAO.listarApartamentos();

        if (apartamentos.isEmpty()) {
            System.out.println("Nenhum apartamento cadastrado.");
        } else {
            System.out.println("Lista de Apartamentos:");
            for (Apartamento apt : apartamentos) {
                System.out.println(apt); // Depende do método toString() da classe Apartamento
            }
        }
    }
    //Menu reserva
    public static void realizarReserva(Scanner scanner) throws HospedagemIndisponivelException, ReservaNaoEncontradaException, ServicoNaoPermitidoException {
        int opcaoReserva;

        do {
            System.out.println("===== REALIZAR RESERVA =====");
            System.out.println("1 - Realizar reserva de hospedagem");
            System.out.println("2 - Realizar reserva de serviço adicional");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcaoReserva = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcaoReserva) {
                case 1:
                    realizarReservaHospedagem(scanner);
                    break;
                case 2:
                    realizarReservaServicoAdicional(scanner);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoReserva != 0);
    }
    // Metodo para realizar reserva
    public static void realizarReservaHospedagem(Scanner scanner) throws HospedagemIndisponivelException, ReservaNaoEncontradaException, ServicoNaoPermitidoException {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        // Verifica se o cliente existe
        ClientesDAO clientesDAO = new ClientesDAO();
        if (!clientesDAO.verificarExistenciaCliente(cpf)) {
            System.out.println("Cliente não encontrado. Cadastre o cliente primeiro.");
            return;
        }

        // Busca o cliente pelo CPF para associá-lo à reserva
        Cliente cliente = clientesDAO.buscarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("Erro ao buscar dados do cliente.");
            return;
        }

        // Escolha do tipo de hospedagem
        System.out.println("Escolha o tipo de hospedagem:");
        System.out.println("1 - Quarto");
        System.out.println("2 - Cabana");
        System.out.println("3 - Apartamento");
        System.out.print("Escolha uma opção: ");
        int tipoHospedagem = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        // Escolha do ID da hospedagem
        System.out.print("Digite o ID da hospedagem: ");
        int idHospedagem = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        // Data de check-in
        System.out.print("Digite a data de check-in (AAAA-MM-DD): ");
        String dataCheckInStr = scanner.nextLine();
        java.sql.Date dataCheckIn = java.sql.Date.valueOf(dataCheckInStr); // Usa java.sql.Date

        // Cria o objeto Reserva usando o construtor apropriado
        Reserva reserva = new Reserva(0, cliente, idHospedagem, tipoHospedagem, dataCheckIn, "Reservada");

        // Realiza a reserva no banco de dados
        ReservaDAO reservaDAO = new ReservaDAO();
        if (reservaDAO.realizarReserva(reserva)) {
            System.out.println("Reserva realizada com sucesso!");
        } else {
            System.out.println("Erro ao realizar reserva.");
        }
    }
    // Metodo para realizar reserva de serviço adicional
    public static void realizarReservaServicoAdicional(Scanner scanner)  throws ServicoNaoPermitidoException, HospedagemIndisponivelException {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        ReservaDAO reservaDAO = new ReservaDAO();
        if (!reservaDAO.verificarReservaAtiva(cpf)) {
            System.out.println("Nenhuma reserva ativa encontrada para este CPF.");
            return;
        }

        System.out.println("Escolha o tipo de serviço adicional:");
        System.out.println("1 - Transfer");
        System.out.println("2 - Passeios Turísticos");
        System.out.print("Escolha uma opção: ");
        int tipoServico = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        if (tipoServico == 1) {
            realizarReservaTransfer(scanner, cpf);
        } else if (tipoServico == 2) {
            realizarReservaPasseio(scanner, cpf);
        } else {
            System.out.println("Opção inválida.");
        }
    }
    // Metodo para realizar reserva de transfer
    public static void realizarReservaTransfer(Scanner scanner, String cpf) throws HospedagemIndisponivelException, ServicoNaoPermitidoException {
        System.out.print("Digite o ID do serviço de transfer: ");
        int idServico = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        System.out.print("Digite a quantidade de pessoas: ");
        int quantidadePessoas = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        TransferDAO transferDAO = new TransferDAO();
        double valorTotal = transferDAO.calcularValor(idServico, quantidadePessoas);

        if (valorTotal > 0) {
            // Cadastra a reserva na tabela ReservaServicoAdicional
            boolean reservaRealizada = transferDAO.cadastrarReservaServicoAdicional(cpf, idServico, quantidadePessoas, valorTotal);
            if (reservaRealizada) {
                System.out.println("Reserva de transfer realizada com sucesso! Valor total: " + valorTotal);
            } else {
                System.out.println("Erro ao realizar reserva de transfer.");
            }
        } else {
            System.out.println("Erro ao calcular o valor total ou serviço não encontrado.");
        }
    }
    // Metodo para realizar reserva de passeio turístico
    public static void realizarReservaPasseio(Scanner scanner, String cpf) throws HospedagemIndisponivelException, ServicoNaoPermitidoException {
        System.out.print("Digite o ID do passeio turístico: ");
        int idServico = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        System.out.print("Digite a quantidade de pessoas: ");
        int quantidadePessoas = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        PasseiosTuristicosDAO passeiosDAO = new PasseiosTuristicosDAO();
        double valorTotal = passeiosDAO.calcularValor(idServico, quantidadePessoas);

        if (valorTotal > 0) {
            // Cadastra a reserva na tabela ReservaServicoAdicional
            boolean reservaRealizada = passeiosDAO.cadastrarReservaServicoAdicional(cpf, idServico, quantidadePessoas, valorTotal);
            if (reservaRealizada) {
                System.out.println("Reserva de passeio turístico realizada com sucesso! Valor total: " + valorTotal);
            } else {
                System.out.println("Erro ao realizar reserva de passeio turístico.");
            }
        } else {
            System.out.println("Erro ao calcular o valor total ou passeio não encontrado.");
        }
    }
    // Função auxiliar para cancelar reserva
    public static void cancelarReserva(Scanner scanner) throws ReservaNaoEncontradaException {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        ReservaDAO reservaDAO = new ReservaDAO();
        List<Map<String, String>> reservasAtivas = reservaDAO.listarReservasAtivasPorCpf(cpf);

        if (reservasAtivas.isEmpty()) {
            System.out.println("Nenhuma reserva ativa encontrada para o CPF informado.");
            return;
        }

        // Mostra as reservas ativas
        System.out.println("Reservas ativas para o CPF " + cpf + ":");
        for (Map<String, String> reserva : reservasAtivas) {
            System.out.println(
                    "ID da Reserva: " + reserva.get("idReserva") +
                            ", Check-in: " + reserva.get("dataCheckIn") +
                            ", Check-out: " + reserva.get("dataCheckOut")
            );
        }

        // Solicita o ID da reserva para cancelar
        System.out.print("Digite o ID da reserva que deseja cancelar: ");
        int idReserva = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        if (reservaDAO.cancelarReserva(idReserva)) {
            System.out.println("Reserva cancelada com sucesso.");
        } else {
            throw new ReservaNaoEncontradaException("Reserva não encontrada ou já cancelada.");
        }
    }
    // Fazer check-in
    public static void fazerCheckIn(Scanner scanner) throws CheckInInvalidoException, ReservaNaoEncontradaException {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        ReservaDAO reservaDAO = new ReservaDAO();
        List<Map<String, String>> reservasReservadas = reservaDAO.listarReservasReservadasPorCpf(cpf);

        if (reservasReservadas.isEmpty()) {
            System.out.println("Nenhuma reserva com status 'Reservada' encontrada para o CPF informado.");
            return;
        }

        // Mostra as reservas com status 'Reservada'
        System.out.println("Reservas com status 'Reservada' para o CPF " + cpf + ":");
        for (Map<String, String> reserva : reservasReservadas) {
            System.out.println(
                    "ID da Reserva: " + reserva.get("idReserva") +
                            ", Check-in: " + reserva.get("dataCheckIn") +
                            ", Check-out: " + reserva.get("dataCheckOut")
            );
        }

        // Solicita o ID da reserva para realizar o check-in
        System.out.print("Digite o ID da reserva para realizar o check-in: ");
        int idReserva = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        if (reservaDAO.fazerCheckIn(idReserva)) {
            System.out.println("Check-in realizado com sucesso.");
        } else {
            throw new CheckInInvalidoException("Não foi possível realizar o check-in.");
        }
    }
    // Fazer checkout
    public static void fazerCheckOut(Scanner scanner) throws ReservaNaoEncontradaException {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        ReservaDAO reservaDAO = new ReservaDAO();
        List<Map<String, String>> reservasAtivas = reservaDAO.listarReservasAtivasPorCpf(cpf);

        if (reservasAtivas.isEmpty()) {
            System.out.println("Nenhuma reserva ativa encontrada para o CPF informado.");
            return;
        }

        // Mostra as reservas ativas
        System.out.println("Reservas ativas para o CPF " + cpf + ":");
        for (Map<String, String> reserva : reservasAtivas) {
            System.out.println(
                    "ID da Reserva: " + reserva.get("idReserva") +
                            ", Check-in: " + reserva.get("dataCheckIn") +
                            ", Check-out: " + reserva.get("dataCheckOut")
            );
        }

        // Solicita o ID da reserva para check-out
        System.out.print("Digite o ID da reserva para realizar o check-out: ");
        int idReserva = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        if (reservaDAO.fazerCheckOut(idReserva)) {
            // Recupera informações da hospedagem
            Map<String, Object> hospedagem = reservaDAO.recuperarInformacoesHospedagem(idReserva);
            double valorHospedagem = reservaDAO.calcularValorHospedagem(idReserva);
            double valorServicosAdicionais = reservaDAO.recuperarValorServicosAdicionais(idReserva);
            double valorTotal = valorHospedagem + valorServicosAdicionais;

            // Cadastra a cobrança
            if (CobrancaDAO.cadastrarCobranca(cpf, valorTotal)) {
                System.out.println("Cobrança gerada com sucesso. Valor total: " + valorTotal);
            } else {
                System.out.println("Erro ao gerar cobrança.");
            }
        } else {
            throw new ReservaNaoEncontradaException("Não foi possível realizar o check-out.");
        }
    }
    // Consutar (case 5)
    public static void consultarDisponibilidade(Scanner scanner) throws HospedagemIndisponivelException {
        System.out.println("===== CONSULTAR DISPONIBILIDADE =====");
        System.out.println("1 - Apartamento");
        System.out.println("2 - Cabana");
        System.out.println("3 - Quarto");
        System.out.println("4 - Transfer");
        System.out.println("5 - Passeios turísticos");
        System.out.print("Escolha uma opção: ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        switch (tipo) {
            case 1:
                consultarDisponibilidadeApartamento(scanner);
                break;
            case 2:
                consultarDisponibilidadeCabana(scanner);
                break;
            case 3:
                consultarDisponibilidadeQuarto(scanner);
                break;
            case 4:
                consultarDisponibilidadeTransfer(scanner);
                break;
            case 5:
                consultarDisponibilidadePasseioTuristico(scanner);
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
    // Consultar cado apartamento
    private static void consultarDisponibilidadeApartamento(Scanner scanner) {
        System.out.println("===== CONSULTAR DISPONIBILIDADE DE APARTAMENTO =====");
        System.out.println("1 - Listar todos os apartamentos disponíveis");
        System.out.println("2 - Consultar disponibilidade pelo ID");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        ApartamentoDAO apartamentoDAO = new ApartamentoDAO();

        switch (opcao) {
            case 1:
                List<Apartamento> apartamentos = apartamentoDAO.listarApartamentos();
                for (Apartamento apartamento : apartamentos) {
                    if (!apartamento.isReservado()) {
                        System.out.println("Apartamento ID: " + apartamento.getIdhospedagem() +
                                ", Capacidade Máxima: " + apartamento.getCapacidadeMaxima() +
                                ", Preço Diária: " + apartamento.getPrecoDiaria());
                    }
                }
                break;
            case 2:
                System.out.print("Digite o ID do apartamento: ");
                int idApartamento = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                if (apartamentoDAO.verificarDisponibilidade(idApartamento)) {
                    System.out.println("Apartamento " + idApartamento + " está disponível.");
                } else {
                    System.out.println("Apartamento " + idApartamento + " não está disponível.");
                }
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
    // Consultar caso cabana
    private static void consultarDisponibilidadeCabana(Scanner scanner) {
        System.out.println("===== CONSULTAR DISPONIBILIDADE DE CABANA =====");
        System.out.println("1 - Listar todas as cabanas disponíveis");
        System.out.println("2 - Consultar disponibilidade pelo ID");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        CabanaDAO cabanaDAO = new CabanaDAO();

        switch (opcao) {
            case 1:
                List<Cabana> cabanas = cabanaDAO.listarCabanas();
                for (Cabana cabana : cabanas) {
                    if (!cabana.isReservado()) {
                        System.out.println("Cabana ID: " + cabana.getIdhospedagem() +
                                ", Capacidade Máxima: " + cabana.getCapacidadeMaxima() +
                                ", Preço Diária: " + cabana.getPrecoDiaria());
                    }
                }
                break;
            case 2:
                System.out.print("Digite o ID da cabana: ");
                int idCabana = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                if (cabanaDAO.verificarDisponibilidade(idCabana)) {
                    System.out.println("Cabana " + idCabana + " está disponível.");
                } else {
                    System.out.println("Cabana " + idCabana + " não está disponível.");
                }
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
    // Consultar caso quarto
    private static void consultarDisponibilidadeQuarto(Scanner scanner) {
        System.out.println("===== CONSULTAR DISPONIBILIDADE DE QUARTO =====");
        System.out.println("1 - Listar todos os quartos disponíveis");
        System.out.println("2 - Consultar disponibilidade pelo ID");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        QuartoDAO quartoDAO = new QuartoDAO();

        switch (opcao) {
            case 1:
                List<Quarto> quartos = quartoDAO.listarQuartos();
                for (Quarto quarto : quartos) {
                    if (!quarto.isReservado()) {
                        System.out.println("Quarto ID: " + quarto.getIdhospedagem() +
                                ", Capacidade Máxima: " + quarto.getCapacidadeMaxima() +
                                ", Preço Diária: " + quarto.getPrecoDiaria());
                    }
                }
                break;
            case 2:
                System.out.print("Digite o ID do quarto: ");
                int idQuarto = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                if (quartoDAO.verificarDisponibilidade(idQuarto)) {
                    System.out.println("Quarto " + idQuarto + " está disponível.");
                } else {
                    System.out.println("Quarto " + idQuarto + " não está disponível.");
                }
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
    // Consultar caso apartamento
    private static void consultarDisponibilidadeTransfer(Scanner scanner) {
        System.out.println("===== CONSULTAR DISPONIBILIDADE DE TRANSFER =====");
        System.out.println("1 - Listar todos os transfers disponíveis");
        System.out.println("2 - Consultar disponibilidade pelo ID");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        TransferDAO transferDAO = new TransferDAO();

        switch (opcao) {
            case 1:
                List<Transfer> transfers = transferDAO.listarTransfers();
                for (Transfer transfer : transfers) {
                    if (!transfer.isReservado()) {
                        System.out.println("Transfer ID: " + transfer.getIdservicoAdicional() +
                                ", Destino: " + transfer.getDestino() +
                                ", Preço por Pessoa: " + transfer.getPrecoPorPessoa());
                    }
                }
                break;
            case 2:
                System.out.print("Digite o ID do transfer: ");
                int idTransfer = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                if (transferDAO.verificarDisponibilidade(idTransfer)) {
                    System.out.println("Transfer " + idTransfer + " está disponível.");
                } else {
                    System.out.println("Transfer " + idTransfer + " não está disponível.");
                }
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
    // Consultar caso passeios
    private static void consultarDisponibilidadePasseioTuristico(Scanner scanner) {
        System.out.println("===== CONSULTAR DISPONIBILIDADE DE PASSEIOS TURÍSTICOS =====");
        System.out.println("1 - Listar todos os passeios disponíveis");
        System.out.println("2 - Consultar disponibilidade pelo ID");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        PasseiosTuristicosDAO passeioDAO = new PasseiosTuristicosDAO();

        switch (opcao) {
            case 1:
                List<PasseiosTuristicos> passeios = passeioDAO.listarPasseios();
                for (PasseiosTuristicos passeio : passeios) {
                    if (passeio.isDisponivel()) {
                        System.out.println("Passeio ID: " + passeio.getIdservicoAdicional() +
                                ", Destino: " + passeio.getDestino() +
                                ", Preço por Pessoa: " + passeio.getPrecoPorPessoa());
                    }
                }
                break;
            case 2:
                System.out.print("Digite o ID do passeio: ");
                int idPasseio = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                if (passeioDAO.verificarDisponibilidade(idPasseio)) {
                    System.out.println("Passeio " + idPasseio + " está disponível.");
                } else {
                    System.out.println("Passeio " + idPasseio + " não está disponível.");
                }
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public static void gerenciarRelatorio() {
        Scanner scanner = new Scanner(System.in);
        RelatorioDAO relatorioDAO = new RelatorioDAO();

        while (true) {
            System.out.println("\n--- Menu de Relatórios ---");
            System.out.println("1. Relatório de Reservas Confirmadas");
            System.out.println("2. Relatório de Cancelamentos");
            System.out.println("3. Relatório de Ocupação");
            System.out.println("4. Relatório Financeiro");
            System.out.println("5. Relatório de Gastos do Cliente");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    relatorioReservasConfirmadas(relatorioDAO);
                    break;
                case 2:
                    relatorioCancelamento(relatorioDAO, scanner);
                    break;
                case 3:
                    relatorioOcupacao(relatorioDAO);
                    break;
                case 4:
                    relatorioFinanceiro(relatorioDAO);
                    break;
                case 5:
                    relatorioCliente(relatorioDAO, scanner);
                    break;
                case 0:
                    return; // Volta ao menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void relatorioReservasConfirmadas(RelatorioDAO relatorioDAO) {
        List<Map<String, String>> reservasConfirmadas = relatorioDAO.relatorioReservasConfirmadas();

        if (reservasConfirmadas.isEmpty()) {
            System.out.println("Nenhuma reserva confirmada encontrada.");
        } else {
            System.out.println("\n--- Reservas Confirmadas ---");
            for (Map<String, String> reserva : reservasConfirmadas) {
                System.out.println(
                        "ID: " + reserva.get("idReserva") +
                                ", CPF do Cliente: " + reserva.get("cliente_cpf") +
                                ", ID da Hospedagem: " + reserva.get("idHospedagem") +
                                ", Tipo de Hospedagem: " + reserva.get("escolhaHospedagem") +
                                ", Check-in: " + reserva.get("dataCheckIn") +
                                ", Check-out: " + reserva.get("dataCheckOut") +
                                ", Status: " + reserva.get("status")
                );
            }
        }
    }

    private static void relatorioCancelamento(RelatorioDAO relatorioDAO, Scanner scanner) {
        System.out.print("Digite a data de início (yyyy-MM-dd): ");
        String dataInicio = scanner.nextLine();
        System.out.print("Digite a data de fim (yyyy-MM-dd): ");
        String dataFim = scanner.nextLine();

        List<Map<String, String>> reservasCanceladas = relatorioDAO.relatorioCancelamento(dataInicio, dataFim);

        if (reservasCanceladas.isEmpty()) {
            System.out.println("Nenhuma reserva cancelada encontrada no intervalo especificado.");
        } else {
            System.out.println("\n--- Reservas Canceladas ---");
            for (Map<String, String> reserva : reservasCanceladas) {
                System.out.println(
                        "ID: " + reserva.get("idReserva") +
                                ", CPF do Cliente: " + reserva.get("cliente_cpf") +
                                ", ID da Hospedagem: " + reserva.get("idHospedagem") +
                                ", Tipo de Hospedagem: " + reserva.get("escolhaHospedagem") +
                                ", Check-in: " + reserva.get("dataCheckIn") +
                                ", Check-out: " + reserva.get("dataCheckOut") +
                                ", Data de Cancelamento: " + reserva.get("dataCancelamento") +
                                ", Status: " + reserva.get("status")
                );
            }
        }
    }

    private static void relatorioOcupacao(RelatorioDAO relatorioDAO) {
        Map<String, Double> relatorio = relatorioDAO.relatorioOcupacao();

        System.out.println("\n--- Relatório de Ocupação ---");
        System.out.println("Total de Hospedagens: " + relatorio.get("totalHospedagens"));
        System.out.println("Total de Hospedagens Reservadas: " + relatorio.get("totalReservadas"));
        System.out.println("Porcentagem de Ocupação: " + String.format("%.2f", relatorio.get("porcentagemOcupacao")) + "%");
    }

    private static void relatorioFinanceiro(RelatorioDAO relatorioDAO) {
        Map<String, Double> relatorio = relatorioDAO.relatorioFinanceiro();

        System.out.println("\n--- Relatório Financeiro ---");
        System.out.println("Total de Cobranças: R$ " + String.format("%.2f", relatorio.get("totalCobrancas")));
    }

    private static void relatorioCliente(RelatorioDAO relatorioDAO, Scanner scanner) {
        System.out.print("Digite o CPF do cliente: ");
        String cpfCliente = scanner.nextLine();

        Map<String, Double> relatorio = relatorioDAO.relatorioCliente(cpfCliente);

        System.out.println("\n--- Relatório de Gastos do Cliente ---");
        System.out.println("CPF: " + cpfCliente);
        System.out.println("Total Gasto: R$ " + String.format("%.2f", relatorio.get(cpfCliente)));
    }

}