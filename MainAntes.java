import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controle.CompraController;
import controle.FilmeController;
import controle.UsuarioController;
import modelo.Administrador;
import modelo.Bilhete;
import modelo.Compra;
import modelo.Filme;
import modelo.Sala;
import modelo.Sessao;
import modelo.TiposSala;
import modelo.Usuario;
import modelo.VendasException;

public class MainAntes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        UsuarioController usuarioCtrl = new UsuarioController();
        FilmeController filmeCtrl = new FilmeController();
        List<Sala> salas = new ArrayList<>();
        CompraController compraCtrl = new CompraController(salas);

        try {
            Administrador adm = new Administrador("Ana", 30, "ana@cine.com", 5000, 1);
            adm.adicionarUsuario();

            Filme filme = new Filme("Filme A", 120, "Sinopse A", 20.0);
            filmeCtrl.incluirFilme(adm, filme);

            Sala sala = new Sala(TiposSala.COMUM);
            filmeCtrl.criarSessao(sala, 0, filme, "18:00");
            salas.add(sala);
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
            opcao = Integer.parseInt(sc.nextLine());

            try {
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
                    usuarioCtrl.cadastrar("comum", user, cpf, senha, 'M', email, idade,
                            "Cartão", "0000", "000", null);
                    System.out.println("Cliente cadastrado!");

                } else if (opcao == 2) {
                    for (Filme f : filmeCtrl.listarFilmes()) {
                        System.out.println("- " + f.getNome() + " (" + f.getDuracao() + " min)");
                    }

                } else if (opcao == 3) {
                    System.out.print("Usuário: ");
                    String user = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();
                    Usuario usuario = usuarioCtrl.login(user, senha);

                    Sala sala = salas.get(0);
                    List<Sessao> sessoes = compraCtrl.listarSessoes(sala);
                    for (int i = 0; i < sessoes.size(); i++) {
                        Sessao s = sessoes.get(i);
                        System.out.println(i + " - " + s.getFilme().getNome() + " às " + s.getHorario());
                    }
                    System.out.print("Sessão: ");
                    Sessao sessao = sessoes.get(Integer.parseInt(sc.nextLine()));
                    System.out.print("Linha (0-9): ");
                    int linha = Integer.parseInt(sc.nextLine());
                    System.out.print("Coluna (0-14): ");
                    int coluna = Integer.parseInt(sc.nextLine());

                    Compra compra = compraCtrl.iniciarCompra();
                    Bilhete b = compraCtrl.comprarBilhete(compra, usuario, sala, sessao, linha, coluna, null);
                    double total = compraCtrl.finalizarCompra(usuario, compra);
                    System.out.println("Bilhete comprado! Total: R$ " + total);
                }
            } catch (VendasException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
        System.out.println("Até logo!");
    }
}


