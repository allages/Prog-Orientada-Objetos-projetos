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
import modelo.Cadeira;
import modelo.Produto;

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


    public Cadeira[] sugerirCadeiras(Sessao sessao, int quantidade) throws VendasException {
        Cadeira[] sugestao = sessao.sugerirCadeirasJuntas(quantidade);
        if (sugestao == null) {
            throw new VendasException("Não há " + quantidade + " cadeiras juntas disponíveis nesta sessão");
        }
        return sugestao;
    }

    public List<Bilhete> comprarBilhetes(Compra compra, Usuario usuario, Sala sala, Sessao sessao,
                                         Cadeira[] cadeiras, CupomPromocional cupom) throws VendasException {
        sessao.validarVenda();

        if (compra.getQtdBilhetes() + cadeiras.length > compra.getBilhetes().length) {
            throw new VendasException("Limite de bilhetes da compra atingido");
        }

        List<Cadeira> ocupadas = new ArrayList<>();
        try {
            for (Cadeira c : cadeiras) {
                sessao.ocuparCadeira(c.getLinha(), c.getColuna());
                ocupadas.add(c);
            }
        } catch (VendasException e) {
            for (Cadeira c : ocupadas) {
                sessao.getCadeira(c.getLinha(), c.getColuna()).setOcupada(false);
            }
            throw e;
        }

        List<Bilhete> bilhetes = new ArrayList<>();
        for (Cadeira c : cadeiras) {
            Bilhete bilhete = new Bilhete(usuario.getUser(), usuario.getCpf(), sala, sessao.getFilme(),
                    sessao, sala.calcularValor(sessao.getFilme()), sessao.getCadeira(c.getLinha(), c.getColuna()));
            if (cupom == null) {
                compra.adicionarBilhete(bilhete);
            } else {
                compra.adicionarBilhete(bilhete, cupom);
            }
            bilhetes.add(bilhete);
        }
        return bilhetes;
    }

    public void adicionarItem(Compra compra, Produto produto, CupomPromocional cupom) throws VendasException {
        boolean adicionou;
        if (cupom == null) {
            adicionou = compra.adicionarItem(produto);
        } else {
            adicionou = compra.adicionarItem(produto, cupom);
        }
        if (!adicionou) {
            throw new VendasException("Limite de itens da compra atingido");
        }
    }
}