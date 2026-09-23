package modelo;

public class Sala {
    private Sessao[] sessoes;

    public Sala () {
        sessoes = new Sessao[7];



    }
    public Sessao[] getSessoes(){
        return sessoes;
    }

    public void setSessoes(Sessao[] sessoes){
        this.sessoes = sessoes;
    }

}
