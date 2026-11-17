# Projeto de POO - Sistema de Cinema

Este repositório contém o projeto de desenvolvimento de um **Sistema de Cinema**, desenvolvido para a cadeira de **Programação Orientada a Objetos (POO)**.

## 👥 Equipe
* Allan Lucas Lages
* Breno de Araújo Bezerra

## 📌 Escopo do Projeto
O objetivo principal é implementar um sistema completo de gerenciamento de cinema focado em conceitos estruturais de POO, dividido em etapas de entrega:

* **Etapa 1:** Modelagem e encapsulamento das classes base (`Usuário`, `Sala`, `Sessão`, `Filme`, `Bilhete` e `Compra`).
* **Etapa 2:** Aplicação de conceitos de Herança, Sobrescrita e Classes Enum (para gerenciamento de produtos do balcão, cupons promocionais, tipos de sala e especializações de usuários como `Estudante` e `Crítico`).
* **Etapa 3:** Classes abstratas, interface e tratamento de exceções (`Base`, `Funcionário`, `Administrador`, `GerenciaFilmes` e `VendasException`), com a persistência de dados a cargo do administrador.
* **Etapa 4:** Aplicação de um padrão de projeto (**Facade**) sobre o sistema já funcionando, com apresentação do projeto.

## 🧩 O que foi implementado

### Etapa 1 e 2: Model
* **Sala de cinema** com grade de 10 linhas x 15 colunas de cadeiras e 7 horários por sala (horário sem sessão fica `null` e não aparece ao usuário).
* **Compra** com um ou mais bilhetes e itens de balcão, e **sugestão de cadeiras juntas** quando há vários bilhetes.
* **Enums:** `Produto` (6 itens de balcão), `CupomPromocional` (3 cupons: 10%, 20% e 30%) e `TiposSala` (Comum 1.0, 3D 1.2, XD 1.3 e XD/3D 1.4, que multiplicam o valor do ingresso).
* **Herança e sobrescrita:** `Estudante` paga metade do valor e `Critico` não paga nada na compra de bilhetes.
* **Sobrecarga:** os métodos de compra de bilhetes e de itens têm uma versão extra que recebe um `CupomPromocional`.
* **Críticas:** `Critico` atribui nota (a média do filme é recalculada) e registra a crítica no vetor de comentários do filme.

### Etapa 3: classes abstratas, interface e exceções
* `Base` (abstrata) com nome, idade e e-mail, e os métodos abstratos de adicionar e alterar usuário.
* `Funcionario` e `Administrador` (concretas) herdam de `Base` e implementam a interface `GerenciaFilmes` (incluir, excluir e alterar filme). Só o administrador exclui usuários.
* `VendasException` trata os 3 erros pedidos na compra de bilhetes: **sessão que já passou**, **poltrona já ocupada** e **filme fora de cartaz**.
* **Persistência:** listas dinâmicas (`ArrayList`) mantidas pelo `Administrador`, para usuários, clientes e filmes. Os dados ficam em memória enquanto o programa está rodando.

### Etapa 4: arquitetura e padrão Facade
O projeto foi organizado em camadas, seguindo a ideia do **MVC**:

| Pacote | Papel |
|---|---|
| `modelo` | **Model:** dados e regras do negócio |
| `controle` | **Controller:** validam os dados, chamam o Model e lançam `VendasException` |
| `Main` | **View:** menu de console |
| `fachada` | **Facade:** porta de entrada única para os controllers |

## 📁 Estrutura do repositório

```
├── modelo/     Classes de domínio, enums, interface e exceção
├── controle/   Controllers (regras de uso do sistema)
├── fachada/    CinemaFacade (padrão Facade)
├── Main.java        Menu de console usando o Facade (versão final)
└── MainAntes.java   Menu de console sem o Facade 
```

## ▶️ Como executar

Pela raiz do projeto:

```bash
javac -d out modelo/*.java controle/*.java fachada/*.java Main.java MainAntes.java
java -cp out Main
```

No IntelliJ, basta abrir o projeto e executar o `Main`. O sistema já inicia com um administrador, o filme "Filme A" e uma sessão às 18:00, para facilitar os testes. O menu permite cadastrar cliente, ver filmes e comprar bilhete, e as demais operações (equipe, administração e críticos) estão disponíveis na `CinemaFacade`.
