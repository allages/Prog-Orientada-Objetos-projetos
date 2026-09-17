package modelo;

public class Sessao {
    private Filme filme;
    private String horario;
    private boolean[][] cadeiras;

    public Sessao(Filme filme , String horario ){
        this.filme = filme;
        this.horario = horario;
        this.cadeiras = new boolean[10][15];
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

    public boolean[][] getCadeiras(){
        return cadeiras;
    }
    public void setCadeiras(boolean[][] cadeiras){
        this.cadeiras = cadeiras;
    }

    public boolean cadeiralivre(int linha , int coluna){
        return !cadeiras[linha][coluna];
    }

    public boolean cadeiraocupada(int linha , int coluna){
        return cadeiras[linha][coluna];
    }
}
