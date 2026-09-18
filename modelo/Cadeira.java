package modelo;

public class Cadeira {
    private int linha;
    private int coluna;
    private boolean ocupada;

    public Cadeira(int linha, int coluna, boolean ocupada){
        this.linha = linha;
        this.coluna = coluna;
        this.ocupada = ocupada;
    }

    public int getLinha(){
        return linha;
    }
    public void setLinha(int linha){
        this.linha = linha;
    }

    public int getColuna(){
        return coluna;
    }
    public void setColuna(int coluna){
        this.coluna = coluna;
    }

    public boolean isOcupada(){
        return ocupada;
    }
    public void setOcupada(boolean ocupada){
        this.ocupada = ocupada;
    }
}
