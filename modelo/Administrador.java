package modelo;

public class Administrador extends Base implements GerenciaFilmes{
    private double salario;
    private int id;


    public Administrador(String nome ,int idade , String email , double salario , int id){
        super(nome , idade , email);
        this.salario = salario;
        this.id = id;
    }
    public double getSalario(){
        return salario;
    }
    public void setSalario(double salario){
        this.salario = salario;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    @Override 
    public void adicionarUsuario(){

    }
    @Override 
    public void alterarUsuario(String nome , int idade , String email){
        setNome(nome);
        setIdade(idade);
        setEmail(email);
    }

    public void excluirUsuario(String email){

    }
    @Override 
    public void excluirFilme(String nome){

    }
    @Override 
    public void incluirFilme(Filme filme){

    }
    @Override 
    public void alterarFilme(String nome , Filme filmeAtualizado){
        
    }

}