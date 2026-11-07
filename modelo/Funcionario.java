package modelo;

public class Funcionario extends Base implements GerenciaFilmes {
    private double salario;

    public Funcionario(String email ,int idade , String nome , double salario){
        super(nome , idade , email);
        this.salario = salario;
    }
    public double getSalario(){
        return salario;
    }
    public void setSalario(double salario){
        this.salario = salario;
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
    @Override 
    public void incluirFilme(Filme filme){

    }
    @Override 
    public void excluirFilme(String nome){

    }
    @Override 
    public void alterarFilme(String nome , Filme filmeAtualizado){
        
    }





    
}
