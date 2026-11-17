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

        Sala sala = new Sala(TiposSala.COMUM);
        filmeCtrl.criarSessao(sala, 0, filme, "18:00");
        salas.add(sala);
    }

    public void cadastrarCliente(String user, String cpf, String senha, String email, int idade)
            throws VendasException {
        usuarioCtrl.cadastrar("comum", user, cpf, senha, 'M', email, idade, "Cartão", "0000", "000", null);
    }

    public List<Filme> listarFilmes() {
        return filmeCtrl.listarFilmes();
    }

    public List<Sessao> listarSessoes() {
        return compraCtrl.listarSessoes(salas.get(0));
    }

    // Um único método esconde: login + iniciar compra + comprar bilhete + finalizar compra
    public double comprarBilhete(String user, String senha, int indiceSessao, int linha, int coluna)
            throws VendasException {
        Usuario usuario = usuarioCtrl.login(user, senha);
        Sala sala = salas.get(0);
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