import java.util.List;
import java.util.Scanner;

import fachada.CinemaFacade;
import modelo.Administrador;
import modelo.Filme;
import modelo.Sala;
import modelo.Sessao;
import modelo.VendasException;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CinemaFacade cinema = new CinemaFacade();

        try {
            cinema.carregarDadosIniciais();
        } catch (VendasException e) {
            System.out.println("Erro nos dados iniciais: " + e.getMessage());
            return;
        }

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== CINEMA ===");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Ver filmes");
            System.out.println("3 - Comprar bilhete");
            System.out.println("4 - Gerenciar filmes (administrador)");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                if (opcao == 1) {
                    System.out.println("Tipo: 1 - Comum | 2 - Estudante (50% de desconto) | 3 - Crítico (grátis)");
                    System.out.print("Tipo: ");
                    int escolhaTipo = Integer.parseInt(sc.nextLine());
                    String tipo;
                    String origem = null;
                    if (escolhaTipo == 1) {
                        tipo = "comum";
                    } else if (escolhaTipo == 2) {
                        tipo = "estudante";
                    } else if (escolhaTipo == 3) {
                        tipo = "critico";
                        System.out.print("Órgão de origem: ");
                        origem = sc.nextLine();
                    } else {
                        throw new VendasException("Tipo de usuário inválido");
                    }
                    System.out.print("Usuário: ");
                    String user = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();
                    System.out.print("E-mail: ");
                    String email = sc.nextLine();
                    System.out.print("Idade: ");
                    int idade = Integer.parseInt(sc.nextLine());
                    cinema.cadastrarCliente(tipo, user, cpf, senha, email, idade, origem);
                    System.out.println("Cliente cadastrado!");

                } else if (opcao == 2) {
                    for (Filme f : cinema.listarFilmes()) {
                        System.out.println("- " + f.getNome() + " (" + f.getDuracao() + " min) - R$ " + f.getValor());
                    }

                } else if (opcao == 3) {
                    System.out.print("Usuário: ");
                    String user = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();

                    List<Sala> salas = cinema.listarSalas();
                    for (int i = 0; i < salas.size(); i++) {
                        System.out.println(i + " - Sala " + salas.get(i).getTipo().getTipo());
                    }
                    System.out.print("Sala: ");
                    int indiceSala = Integer.parseInt(sc.nextLine());

                    List<Sessao> sessoes = cinema.listarSessoes(indiceSala);
                    Sala sala = salas.get(indiceSala);
                    for (int i = 0; i < sessoes.size(); i++) {
                        Sessao s = sessoes.get(i);
                        System.out.println(i + " - " + s.getFilme().getNome() + " às " + s.getHorario()
                                + " - R$ " + sala.calcularValor(s.getFilme()));
                    }
                    System.out.print("Sessão: ");
                    int sessao = Integer.parseInt(sc.nextLine());
                    System.out.print("Linha (0-9): ");
                    int linha = Integer.parseInt(sc.nextLine());
                    System.out.print("Coluna (0-14): ");
                    int coluna = Integer.parseInt(sc.nextLine());

                    double total = cinema.comprarBilhete(user, senha, indiceSala, sessao, linha, coluna);
                    System.out.println("Bilhete comprado! Total: R$ " + total);

                } else if (opcao == 4) {
                    System.out.print("E-mail do administrador: ");
                    String emailAdm = sc.nextLine();
                    System.out.print("ID: ");
                    int idAdm = Integer.parseInt(sc.nextLine());
                    Administrador adm = cinema.loginAdministrador(emailAdm, idAdm);

                    int sub = -1;
                    while (sub != 0) {
                        System.out.println("\n--- FILMES ---");
                        System.out.println("1 - Incluir filme");
                        System.out.println("2 - Alterar filme");
                        System.out.println("3 - Excluir filme");
                        System.out.println("0 - Voltar");
                        System.out.print("Opção: ");
                        try {
                            sub = Integer.parseInt(sc.nextLine());
                            if (sub == 1) {
                                System.out.print("Nome: ");
                                String nome = sc.nextLine();
                                System.out.print("Duração (min): ");
                                int duracao = Integer.parseInt(sc.nextLine());
                                System.out.print("Sinopse: ");
                                String sinopse = sc.nextLine();
                                System.out.print("Valor (use ponto, ex: 20.0): ");
                                double valor = Double.parseDouble(sc.nextLine());
                                cinema.incluirFilme(adm, nome, duracao, sinopse, valor);
                                System.out.println("Filme incluído!");
                            } else if (sub == 2) {
                                System.out.print("Nome do filme a alterar: ");
                                String nome = sc.nextLine();
                                System.out.print("Novo nome: ");
                                String novoNome = sc.nextLine();
                                System.out.print("Nova duração (min): ");
                                int duracao = Integer.parseInt(sc.nextLine());
                                System.out.print("Nova sinopse: ");
                                String sinopse = sc.nextLine();
                                System.out.print("Novo valor (use ponto, ex: 20.0): ");
                                double valor = Double.parseDouble(sc.nextLine());
                                cinema.alterarFilme(adm, nome, novoNome, duracao, sinopse, valor);
                                System.out.println("Filme alterado!");
                            } else if (sub == 3) {
                                System.out.print("Nome do filme a excluir: ");
                                String nome = sc.nextLine();
                                cinema.excluirFilme(adm, nome);
                                System.out.println("Filme excluído!");
                            }
                        } catch (VendasException e) {
                            System.out.println("Erro: " + e.getMessage());
                        } catch (NumberFormatException e) {
                            System.out.println("Digite um número válido.");
                            sub = -1;
                        }
                    }
                }
            } catch (VendasException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
                opcao = -1;
            }
        }
        System.out.println("Volte Sempre!");
    }
}