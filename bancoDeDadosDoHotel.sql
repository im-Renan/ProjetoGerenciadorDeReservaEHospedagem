/*
-- Remove o banco de dados existente (cuidado em produção!)
DROP DATABASE IF EXISTS hotel;

-- Cria o banco de dados
CREATE DATABASE hotel;
USE hotel;

-- Tabela Cliente
CREATE TABLE IF NOT EXISTS Cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(15) NOT NULL
);

-- Tabela Funcionario
CREATE TABLE IF NOT EXISTS Funcionario (
    CodFuncionario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    senha VARCHAR(255) NOT NULL -- Alterado para VARCHAR para armazenar hash de senha
);

-- Tabela Cabana
CREATE TABLE IF NOT EXISTS Cabana (
    Idhospedagem INT AUTO_INCREMENT PRIMARY KEY,
    capacidadeMaxima INT NOT NULL,
    precoDiaria DECIMAL(10, 2) NOT NULL,
    reservado BOOLEAN DEFAULT FALSE
);

-- Tabela Apartamento
CREATE TABLE IF NOT EXISTS Apartamento (
    Idhospedagem INT AUTO_INCREMENT PRIMARY KEY,
    capacidadeMaxima INT NOT NULL,
    precoDiaria DECIMAL(10, 2) NOT NULL,
    reservado BOOLEAN DEFAULT FALSE
);

-- Tabela Quarto
CREATE TABLE IF NOT EXISTS Quarto (
    Idhospedagem INT AUTO_INCREMENT PRIMARY KEY,
    capacidadeMaxima INT NOT NULL,
    precoDiaria DECIMAL(10, 2) NOT NULL,
    reservado BOOLEAN DEFAULT FALSE
);

-- Inserção de dados na tabela Quarto
INSERT INTO Quarto (capacidadeMaxima, precoDiaria, reservado) VALUES
(4, 100.00, FALSE),
(4, 100.00, FALSE),
(4, 100.00, FALSE),
(4, 100.00, FALSE),
(4, 100.00, FALSE);

-- Inserção de dados na tabela Cabana
INSERT INTO Cabana (capacidadeMaxima, precoDiaria, reservado) VALUES
(4, 150.00, FALSE),
(4, 150.00, FALSE),
(2, 100.00, FALSE),
(2, 100.00, FALSE),
(2, 100.00, FALSE);

-- Inserção de dados na tabela Apartamento
INSERT INTO Apartamento (capacidadeMaxima, precoDiaria, reservado) VALUES
(5, 50.00, FALSE),
(5, 50.00, FALSE),
(5, 50.00, FALSE),
(5, 50.00, FALSE),
(5, 50.00, FALSE);

-- Tabela Reserva
CREATE TABLE IF NOT EXISTS Reserva (
    idReserva INT PRIMARY KEY AUTO_INCREMENT,
    cliente_cpf VARCHAR(14) NOT NULL,
    idHospedagem INT NOT NULL,
    escolhaHospedagem INT NOT NULL, -- 1 = Quarto, 2 = Cabana, 3 = Apartamento
    dataCheckIn DATE NOT NULL,
    dataCheckOut DATE,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (cliente_cpf) REFERENCES Cliente(cpf)
);


CREATE TABLE IF NOT EXISTS Transfer (
    idServicoAdicional INT AUTO_INCREMENT PRIMARY KEY, -- ID único e autoincrementado
    destino VARCHAR(100) NOT NULL,    -- Destino da transferência
    quantidadeDePessoas INT NOT NULL, -- Quantidade de pessoas
    precoPorPessoa DECIMAL(10, 2) NOT NULL -- Preço por pessoa
);

CREATE TABLE IF NOT EXISTS PasseiosTuristicos (
    idServicoAdicional INT AUTO_INCREMENT PRIMARY KEY, -- ID único e autoincrementado
    destino VARCHAR(100) NOT NULL,    -- Destino do passeio turístico
    quantidadeDePessoas INT NOT NULL, -- Quantidade de pessoas
    precoPorPessoa DECIMAL(10, 2) NOT NULL -- Preço por pessoa
);

-- Cria a nova tabela ReservaServicoAdicional
CREATE TABLE IF NOT EXISTS ReservaServicoAdicional (
    idReservaServicoAdicional INT AUTO_INCREMENT PRIMARY KEY, -- ID único e autoincrementado
    cpf_cliente VARCHAR(14) NOT NULL,                         -- CPF do cliente que solicitou o serviço
    tipoServico ENUM('transfer', 'passeio') NOT NULL,         -- Tipo de serviço (transfer ou passeio)
    quantidadeDePessoas INT NOT NULL,                         -- Quantidade de pessoas
    precoPorPessoa DECIMAL(10, 2) NOT NULL,                   -- Preço por pessoa
    valorTotal DECIMAL(10, 2) NOT NULL,                       -- Valor total do serviço
    FOREIGN KEY (cpf_cliente) REFERENCES Cliente(cpf)         -- Chave estrangeira para vincular ao cliente
);

use hotel;
CREATE TABLE IF NOT EXISTS Cobranca (
    idCobranca INT AUTO_INCREMENT PRIMARY KEY, -- ID único e autoincrementado
    cpf_cliente VARCHAR(14) NOT NULL,         -- CPF do cliente
    valorTotal DECIMAL(10, 2) NOT NULL,        -- Valor total devido
    dataGeracao DATE NOT NULL,                 -- Data em que a cobrança foi gerada
    FOREIGN KEY (cpf_cliente) REFERENCES Cliente(cpf) -- Chave estrangeira para vincular ao cliente
);
*/
