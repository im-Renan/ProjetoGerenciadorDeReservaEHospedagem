# Sistema Gerenciador de Reservas e Hospedagem

# Descrição
Este é um projeto colaborativo desenvolvido por Rena Moreira da Silva e Angelo Pacheco Pereira, para a disciplina de Programação Orientada a Objetos (POO) do curso de Sistemas de Informação - IFMA.

O sistema permite cadastrar clientes e funcionários, gerenciar reservas, check-ins, check-outs e serviços adicionais, facilitando a administração de hospedagens em hotéis, cabanas e apartamentos.

# Funcionalidades 
• Cadastrar Usuários: Permite adicionar clientes e funcionários ao sistema.  
• Autenticar Usuário: Valida a identidade de clientes e funcionários.  
• Cadastrar Hospedagens: Registra quartos, cabanas e apartamentos disponíveis.  
• Reservar Hospedagens: Permite a reserva e cancelamento de hospedagens.  
• Check-in e Check-out: Gerencia entradas e saídas de hóspedes.  
• Gerenciar Serviços Adicionais: Inclui passeios turísticos e transfer.

# Tecnologias Utilizadas
* 🖥️ Linguagem de Programação: Java
* 📂 Banco de Dados: MySQL
* 🔗 Bibliotecas: JDBC (para conexão com MySQL), Swing (para interface gráfica)

# Como Executar

**1-Requisitos**

- Java JDK 8+ instalado
- MySQL instalado e rodando
- Eclipse (ou outra IDE compatível)

**2-Configuração do Banco de Dados**

* Antes de rodar o sistema, crie o banco de dados no **MySQL Workbench ou IDE compatível:**
  
```sql 
CREATE DATABASE gerenciador_reservas;
USE gerenciador_reservas;
```

**3-Compilação e Execução**

I. Clone o repositório:

```Bash
git clone https://github.com/Deckard-exe/GerenciadorDeReservasEHospedagem.git
```

II. Importe o projeto no Eclipse ou IDE compatível.

III. No arquivo ConexaoBD.java, altere as credenciais do banco:
 ```Java
private static final String URL = "jdbc:mysql://localhost:3306/gerenciador_reservas";
private static final String USUARIO = "root"; 
private static final String SENHA = "sua_senha";
```

IV. Compile e execute o programa:
```Java
javac -d bin src/*.java
java -cp bin Main
```

# Estrutura do Projeto
📂 src/ (Código-fonte)
📁 exceptions/ (Exceções personalizadas)
📁 hospedagens/ (Classes para tipos de hospedagem)
📁 servicosAdicionais/ (Serviços extras como transfer e passeios)
📁 reservaControle/ (Gerenciamento de reservas)
📁 usuarios/ (Clientes e funcionários)
📄 ConexaoBD.java (Gerencia a conexão com MySQL)

# Autor
- Desenvolvido por: @Deckard-exe e @anjelop
- Status do Projeto: Em Andamento 
