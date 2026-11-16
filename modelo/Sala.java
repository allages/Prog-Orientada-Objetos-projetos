package modelo;

public class Sala {
    private Sessao[] sessoes;
    private TiposSala tipo;

    public Sala (TiposSala tipo) {
        this.tipo = tipo;
        sessoes = new Sessao[7];
    }

    public TiposSala getTipo() {
        return tipo;
    }

    public void setTipo(TiposSala tipo) {
        this.tipo = tipo;
    }

    public Sessao[] getSessoes(){
        return sessoes;
    }

    public void setSessoes(Sessao[] sessoes){
        this.sessoes = sessoes;
    }

    public double calcularValor(Filme filme) {
        return filme.getValor() * tipo.getMultiplicador();
    }


}
