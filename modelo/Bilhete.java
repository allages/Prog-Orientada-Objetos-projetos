package modelo;

public class Bilhete {
    private String user;
    private String cpf;
    private Sala sala;
    private Filme filme;
    private Sessao sessao;
    private double valor;
    private Cadeira cadeira;

    public Bilhete (String user , String cpf , Sala sala , Filme filme , Sessao sessao , double valor){
        this.user = user;
        this.cpf = cpf;
        this.sala = sala;
        this.filme = filme;
        this.sessao = sessao;
        this.valor = valor;
        this.cadeira = cadeira;
    }
    public String getUser(){
        return user;
    }
    public void setUser(String user){
        this.user = user;
    }
    public String getCpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public Sala getSala(){
        return sala;
    }
    public void setSala(Sala sala){
        this.sala = sala;
    }
    public Filme getFilme(){
        return filme;
    }
    public void setFilme(Filme filme){
        this.filme = filme;
    }
    public Sessao getSessao(){
        return sessao;
    }
    public void setSessao(Sessao sessao){
        this.sessao = sessao;
    }
    public double getValor(){
        return valor;
    }
    public void setValor(double valor){
        this.valor = valor;
    }
    public Cadeira getCadeira(){
        return cadeira;
    }
    public void setCadeira(Cadeira cadeira){
        this.cadeira = cadeira;
    }

}
