package fachada;

import java.util.ArrayList;
import java.util.List;

import controle.CompraController;
import controle.FilmeController;
import controle.UsuarioController;
import modelo.Administrador;
import modelo.Compra;
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
    private List<Sala> salas;

    public CinemaFacade() {
        this.salas = new ArrayList<>();
        this.usuarioCtrl = new UsuarioController();
        this.filmeCtrl = new FilmeController();
        this.compraCtrl = new CompraController(salas);
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
}
