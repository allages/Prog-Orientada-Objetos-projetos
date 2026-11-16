package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.Bilhete;
import modelo.Compra;
import modelo.CupomPromocional;
import modelo.Sala;
import modelo.Sessao;
import modelo.Usuario;
import modelo.VendasException;

public class CompraController {
    private List<Sala> salas;
    private int limiteBilhetes = 10;
    private int limiteItens = 10;

    public CompraController(List<Sala> salas) {
        this.salas = salas;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public int getLimiteBilhetes() {
        return limiteBilhetes;
    }

    public void setLimiteBilhetes(int limiteBilhetes) {
        this.limiteBilhetes = limiteBilhetes;
    }

    public int getLimiteItens() {
        return limiteItens;
    }

    public void setLimiteItens(int limiteItens) {
        this.limiteItens = limiteItens;
    }

    public Compra iniciarCompra() {
        return new Compra(limiteBilhetes, limiteItens);
    }

    public List<Sessao> listarSessoes(Sala sala) {
        List<Sessao> disponiveis = new ArrayList<>();
        for (Sessao sessao : sala.getSessoes()) {
            if (sessao != null) {
                disponiveis.add(sessao);
            }
        }
        return disponiveis;
    }

    public Bilhete comprarBilhete(Compra compra, Usuario usuario, Sala sala, Sessao sessao,
                                  int linha, int coluna, CupomPromocional cupom) throws VendasException {
        sessao.validarVenda();

        if (compra.getQtdBilhetes() >= compra.getBilhetes().length) {
            throw new VendasException("Limite de bilhetes da compra atingido");
        }

        sessao.ocuparCadeira(linha, coluna);

        Bilhete bilhete = new Bilhete(usuario.getUser(), usuario.getCpf(), sala, sessao.getFilme(),
                sessao, sala.calcularValor(sessao.getFilme()), sessao.getCadeira(linha, coluna));

        if (cupom == null) {
            compra.adicionarBilhete(bilhete);
        } else {
            compra.adicionarBilhete(bilhete, cupom);
        }
        return bilhete;
    }
}