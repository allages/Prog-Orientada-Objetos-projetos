package fachada;

import java.util.ArrayList;
import java.util.List;

import controle.AdministradorController;
import controle.CompraController;
import controle.CriticoController;
import controle.EquipeController;
import controle.FilmeController;
import controle.UsuarioController;
import modelo.Administrador;
import modelo.Compra;
import modelo.Critica;
import modelo.Critico;
import modelo.Funcionario;
import modelo.Filme;
import modelo.Sala;
import modelo.Sessao;
import modelo.TiposSala;
import modelo.Usuario;
import modelo.VendasException;

// FACADE: porta de entrada única do sistema.
public class CinemaFacade {
    private UsuarioController usuarioCtrl;
    private FilmeController filmeCtrl;
    private CompraController compraCtrl;
    private EquipeController equipeCtrl;
    private AdministradorController adminCtrl;
    private CriticoController criticoCtrl;
    private List<Sala> salas;

    public CinemaFacade() {
        this.salas = new ArrayList<>();
        this.usuarioCtrl = new UsuarioController();
        this.filmeCtrl = new FilmeController();
        this.compraCtrl = new CompraController(salas);
        this.equipeCtrl = new EquipeController();
        this.adminCtrl = new AdministradorController(compraCtrl);
        this.criticoCtrl = new CriticoController();
    }

    public void carregarDadosIniciais() throws VendasException {
        Administrador adm = new Administrador("Ana", 30, "ana@cine.com", 5000, 1);
        adm.adicionarUsuario();

        Filme filme = new Filme("Filme A", 120, "Sinopse A", 20.0);
        filmeCtrl.incluirFilme(adm, filme);

        Filme filmeB = new Filme("Filme B", 100, "Sinopse B", 20.0);
        filmeCtrl.incluirFilme(adm, filmeB);

        Sala comum = new Sala(TiposSala.COMUM);
        filmeCtrl.criarSessao(comum, 0, filme, "18:00");
        salas.add(comum);

        Sala tresD = new Sala(TiposSala.TRES_D);
        filmeCtrl.criarSessao(tresD, 0, filmeB, "20:00");
        salas.add(tresD);

        Sala xd = new Sala(TiposSala.XD);
        filmeCtrl.criarSessao(xd, 0, filme, "19:00");
        salas.add(xd);

        Sala xd3d = new Sala(TiposSala.XD_3D);
        filmeCtrl.criarSessao(xd3d, 0, filmeB, "21:00");
        salas.add(xd3d);
    }

    public void cadastrarCliente(String tipo, String user, String cpf, String senha, String email,
                                 int idade, String origem) throws VendasException {
        usuarioCtrl.cadastrar(tipo, user, cpf, senha, 'M', email, idade, "Cartão", "0000", "000", origem);
    }

    public List<Filme> listarFilmes() {
        return filmeCtrl.listarFilmes();
    }

    public List<Sala> listarSalas() {
        return new ArrayList<>(salas);
    }

    public List<Sessao> listarSessoes(int indiceSala) throws VendasException {
        return compraCtrl.listarSessoes(buscarSala(indiceSala));
    }

    // Um único método esconde: login + iniciar compra + comprar bilhete + finalizar compra
    public double comprarBilhete(String user, String senha, int indiceSala, int indiceSessao,
                                 int linha, int coluna) throws VendasException {
        Usuario usuario = usuarioCtrl.login(user, senha);
        Sala sala = buscarSala(indiceSala);
        List<Sessao> sessoes = compraCtrl.listarSessoes(sala);
        if (indiceSessao < 0 || indiceSessao >= sessoes.size()) {
            throw new VendasException("Sessão inexistente");
        }

        Compra compra = compraCtrl.iniciarCompra();
        compraCtrl.comprarBilhete(compra, usuario, sala, sessoes.get(indiceSessao), linha, coluna, null);
        try {
            return compraCtrl.finalizarCompra(usuario, compra);
        } catch (VendasException e) {
            compraCtrl.descartarCompra(compra);
            throw e;
        }
    }

    private Sala buscarSala(int indiceSala) throws VendasException {
        if (indiceSala < 0 || indiceSala >= salas.size()) {
            throw new VendasException("Sala inexistente");
        }
        return salas.get(indiceSala);
    }

    public void incluirFilme(Administrador adm, String nome, int duracao, String sinopse, double valor)
            throws VendasException {
        filmeCtrl.incluirFilme(adm, new Filme(nome, duracao, sinopse, valor));
    }

    public void alterarFilme(Administrador adm, String nome, String novoNome, int duracao,
                             String sinopse, double valor) throws VendasException {
        Filme antigo = filmeCtrl.buscarFilme(nome);
        Filme novo = new Filme(novoNome, duracao, sinopse, valor);
        // mantém notas, críticas e situação de cartaz do filme original
        novo.setNota(antigo.getNota());
        novo.setQuantidade_criticos(antigo.getQuantidade_criticos());
        novo.setCritica(antigo.getCritica());
        novo.setEmCartaz(antigo.isEmCartaz());
        filmeCtrl.alterarFilme(adm, nome, novo);
        // as sessões passam a exibir o filme atualizado
        for (Sala sala : salas) {
            for (Sessao sessao : sala.getSessoes()) {
                if (sessao != null && sessao.getFilme() == antigo) {
                    sessao.setFilme(novo);
                }
            }
        }
    }

    public void excluirFilme(Administrador adm, String nome) throws VendasException {
        Filme filme = filmeCtrl.buscarFilme(nome);
        filmeCtrl.excluirFilme(adm, nome);
        // sessões que ainda usam o filme passam a recusar a venda (fora de cartaz)
        filme.setEmCartaz(false);
    }

    public Administrador loginAdministrador(String email, int id) throws VendasException {
        return equipeCtrl.loginAdministrador(email, id);
    }

    public Funcionario cadastrarFuncionario(Administrador adm, String nome, int idade,
                                            String email, double salario) throws VendasException {
        return equipeCtrl.cadastrarFuncionario(adm, nome, idade, email, salario);
    }

    public void excluirCliente(Administrador adm, String email) throws VendasException {
        adminCtrl.excluirCliente(adm, email);
    }

    public void excluirFuncionario(Administrador adm, String email) throws VendasException {
        adminCtrl.excluirFuncionario(adm, email);
    }


    public void avaliarFilme(String user, String senha, String nomeFilme, double nota, String comentario)
            throws VendasException {
        Usuario usuario = usuarioCtrl.login(user, senha);
        if (!(usuario instanceof Critico)) {
            throw new VendasException("Apenas críticos podem avaliar filmes");
        }
        Filme filme = filmeCtrl.buscarFilme(nomeFilme);
        criticoCtrl.avaliar((Critico) usuario, filme, nota, comentario);
    }

    public List<Critica> listarCriticas(String nomeFilme) throws VendasException {
        return criticoCtrl.listarCriticas(filmeCtrl.buscarFilme(nomeFilme));
    }
}