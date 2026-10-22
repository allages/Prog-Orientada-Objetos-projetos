package modelo;

public class Compra {
    private Bilhete[] bilhetes;
    private String[] itens;
    private int qtdBilhetes;
    private int qtdItens;
    private double valorTotal;

    public Compra(int limiteBilhetes, int limiteItens) {
        this.bilhetes = new Bilhete[limiteBilhetes];
        this.itens = new String[limiteItens];
        this.qtdBilhetes = 0;
        this.qtdItens = 0;
        this.valorTotal = 0.0;
    }

    public boolean adicionarBilhete(Bilhete bilhete) {
        if (qtdBilhetes < bilhetes.length) {
            bilhetes[qtdBilhetes] = bilhete;
            qtdBilhetes++;
            valorTotal += bilhete.getValor();
            return true;
        }
        return false;
    }

    public boolean adicionarItem(String item, double valorItem) {
        if (qtdItens < itens.length) {
            itens[qtdItens] = item;
            qtdItens++;
            valorTotal += valorItem;
            return true;
        }
        return false;
    }

    public void cancelarCompra(Sessao sessao) {
        for (int i = 0; i < qtdBilhetes; i++) {
            if (bilhetes[i] != null && bilhetes[i].getCadeira() != null) {
                sessao.liberarCadeira(
                    bilhetes[i].getCadeira().getLinha(),
                    bilhetes[i].getCadeira().getColuna()
                );
            }
        }
        this.qtdBilhetes = 0;
        this.qtdItens = 0;
        this.valorTotal = 0.0;
    }

    public Bilhete[] getBilhetes() {
        return bilhetes;
    }

    public int getQtdBilhetes() {
        return qtdBilhetes;
    }

    public String[] getItens() {
        return itens;
    }

    public int getQtdItens() {
        return qtdItens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
