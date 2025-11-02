Backend de Sistema de Chamada de Alunos
Descrição

Este projeto é um backend em Java com Spring Boot para gerenciar presença de alunos, histórico de frequência e geração de relatórios em PDF.

Funcionalidades principais:

Registrar presença diária de alunos.

Listar alunos e suas informações.

Gerar histórico de frequência mensal.

Exportar histórico em PDF.

Banco de dados utilizado: MySQL (Railway ou local).

Tecnologias

Java 17

Spring Boot 3

Spring Data JPA

MySQL

iText (para geração de PDFs)

Instalação

Clone o repositório

git clone https://github.com/marcosr1/Backend-de-Sistema-de-Chamada-de-alunos.git
cd Backend-de-Sistema-de-Chamada-de-alunos


Configurar variáveis de ambiente

Crie um arquivo .env ou configure diretamente no seu sistema:

SPRING_DATASOURCE_URL=jdbc:mysql://<host>:<porta>/<database>
SPRING_DATASOURCE_USERNAME=<usuario>
SPRING_DATASOURCE_PASSWORD=<senha>


Substitua <host>, <porta>, <database>, <usuario> e <senha> pelos dados do seu banco.

Build do projeto

Usando Maven:

mvn clean install


Executar o projeto

mvn spring-boot:run


O servidor vai subir normalmente em http://localhost:8080.

Endpoints Disponíveis
Alunos

Listar todos os alunos
GET /alunos
Retorna todos os alunos (sem informações de presença detalhadas).

Cadastrar novo aluno
POST /alunos

{
  "nome": "nomeAluno",
  "idade": 21,
  "categoria": "A",
  "telefone": "99999-9999",
  "dataEntrada": "2025-01-10"
}


Atualizar aluno parcialmente
PUT /alunos/{id}
Pode atualizar qualquer campo do aluno:

{
  "nome": "nomeNovo",
  "telefone": "98888-8888"
}

Presença

Registrar presença
POST /presencas/{alunoId}?presente=true
Registra presença (true) ou falta (false) para o dia atual.

Listar presença por aluno
GET /presencas/{alunoId}
Retorna histórico de presença do aluno.

Histórico de Frequência

Gerar histórico mensal
POST /historicos/gerar?ano=2025&mes=11
Gera o histórico de frequência mensal para todos os alunos.
Substitua ano e mes conforme necessário.

Listar histórico formatado por mês
GET /historicos?ano=2025&mes=11
Retorna lista de alunos com total de aulas, presenças, faltas e percentual.

Gerar PDF do histórico
GET /historicos/pdf?ano=2025&mes=11
Retorna um arquivo PDF do histórico mensal.

Estrutura do Projeto
src/
├─ main/
│  ├─ java/
│  │  └─ com.example.apipresenca/
│  │     ├─ controller/       # Endpoints REST
│  │     ├─ model/            # Classes de modelo (Aluno, Presenca, Historico)
│  │     ├─ repository/       # Interfaces JPA
│  │     └─ service/          # Lógica de negócio
│  └─ resources/
│     ├─ application.properties  # Configurações do Spring Boot
│     └─ ...

Observações

O backend está configurado para CORS liberado (@CrossOrigin("*")), permitindo acesso de qualquer frontend.

A geração de PDF usa iText 5, já inclusa nas dependências.

Total de aulas é calculado considerando 3 aulas por semana, podendo incluir sábados e domingos.
