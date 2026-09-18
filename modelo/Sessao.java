package modelo;

public class Sessao {
    private Filme filme;
    private String horario;
    private Cadeira[][] cadeiras = new Cadeira[10][15];


    public Sessao(Filme filme , String horario ){
        this.filme = filme;
        this.horario = horario;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 15; j++){
                cadeiras[i][j] = new Cadeira(i,j, false);
            }
        }

    }

    public Filme getFilme(){
        return filme;
    }
    public void setFilme(Filme filme){
        this.filme = filme;
    }
    public String getHorario(){
        return horario;
    }
    public void setHorario(String horario){
        this.horario = horario;
    }


}
