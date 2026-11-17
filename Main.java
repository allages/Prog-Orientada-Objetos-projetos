import java.util.List;
import java.util.Scanner;

import fachada.CinemaFacade;
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
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                if (opcao == 1) {
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
                    cinema.cadastrarCliente(user, cpf, senha, email, idade);
                    System.out.println("Cliente cadastrado!");

                } else if (opcao == 2) {
                    for (Filme f : cinema.listarFilmes()) {
                        System.out.println("- " + f.getNome() + " (" + f.getDuracao() + " min)");
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
