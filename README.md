# Backend de Sistema de Chamada de Alunos

## Descrição

Este backend em **Java Spring Boot** gerencia a presença de alunos, histórico mensal de frequência e geração de relatórios em PDF.

Funcionalidades principais:  

- Registrar presença diária de alunos.  
- Listar alunos e suas informações (sem incluir presenças por padrão).  
- Gerar histórico mensal.  
- Exportar histórico em PDF.

Banco de dados: **MySQL**.

---

## Tecnologias

- Java 21  
- Spring Boot 3  
- Spring Data JPA  
- MySQL  
- iText (PDF)  

---

## Instalação

1. **Clone o repositório**

```bash
git clone https://github.com/marcosr1/Backend-de-Sistema-de-Chamada-de-alunos.git
cd Backend-de-Sistema-de-Chamada-de-alunos
```

---

2. **Configurar variáveis de ambiente (.env ou no sistema)**

SPRING_DATASOURCE_URL=jdbc:mysql://<host>:<porta>/<database>
SPRING_DATASOURCE_USERNAME=<usuario>
SPRING_DATASOURCE_PASSWORD=<senha>

---

3. **Endpoints Alunos**

- GET /alunos - lista todos os alunos do banco trazendo suas informações.
  
- POST /alunos - registra um aluno novo
Exemplo:
```
Content-Type: application/json
{
  "nome": "nomeAluno",
  "idade": 18,
  "categoria": "Branca 1 grau",
  "telefone": "99999-0000",
  "dataEntrada": "2025-11-01"
}
```
- PUT /alunos/1 - Muda qualquer informação do aluno, escolha o que vai mudar e mande no corpo Json.
```
Content-Type: application/json
{
  "nome": "Elior", - opcional
  "idade": 18, - opcional
  "categoria": "Branca 1 grau", - opcional
  "telefone": "99999-0000", - opcional
  "dataEntrada": "2025-11-01" - opcional
}
```
---

4. **Endpoints Presença**

- GET /presencas/1 - Lista as presenças do aluno
```
Exemplo:
  {
    "id": 1,
    "data": "2025-11-02",
    "presente": true
  },
  {
    "id": 2,
    "data": "2025-11-03",
    "presente": false
  }
```
- POST /presencas/1?presente=true - Registra a presença do aluno.

- POST /presencas/registrarlote - Registra presenças juntas com um corpo JSON.
```
Exemplo:
  { "alunoId": 1, "presente": true },
  { "alunoId": 2, "presente": false },
  { "alunoId": 3, "presente": true }
```
---

5. **Histórico de Frequência**

- POST /historicos/gerar?ano=2025&mes=11 - Gera o historico do mês indicado.

- GET /historicos?ano=2025&mes=11 - Exibe o historico que foi gerado com o post acima.
```
Exemplo:
 {
    "nomeAluno": "Aluno 1",
    "mesReferencia": "2025-11",
    "totalAulas": 12,
    "presentes": 10,
    "faltas": 2,
    "percentual": 83.33
  },
  {
    "nomeAluno": "Aluno 2",
    "mesReferencia": "2025-11",
    "totalAulas": 12,
    "presentes": 9,
    "faltas": 3,
    "percentual": 75.00
  }
```
- GET /historicos/pdf?ano=2025&mes=11 - Gera um pdf com o Historico do mês que foi criado com o POST acima.

---

6. **Estrutura do Projeto**
```
src/
├─ main/
│  ├─ java/com/example/apipresenca/
│  │  ├─ controller/       # Endpoints REST
│  │  ├─ model/            # Classes de modelo (Aluno, Presenca, Historico)
│  │  ├─ repository/       # Interfaces JPA
│  │  └─ service/          # Lógica de negócio
│  └─ resources/
│     └─ application.properties
```
---

7. **Observação**

- Backend configurado para CORS liberado (@CrossOrigin("*")).
- Total de aulas calculado como 3 aulas por semana, incluindo fins de semana se necessário.
- PDF gerado com iText.

