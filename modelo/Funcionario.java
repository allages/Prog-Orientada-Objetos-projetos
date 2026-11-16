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
        Administrador.getUsuarios().add(this);
    }

    @Override 
    public void alterarUsuario(String nome , int idade , String email){
        setNome(nome);
        setIdade(idade);
        setEmail(email);
    }
    @Override 
    public void incluirFilme(Filme filme){
        Administrador.getFilmes().add(filme);
    }
    @Override 
    public void excluirFilme(String nome){
        for (Filme f : Administrador.getFilmes()) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                f.setEmCartaz(false);
            }
        }
        Administrador.getFilmes().removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }

    @Override 
    public void alterarFilme(String nome , Filme filmeAtualizado){
        for (Filme f : Administrador.getFilmes()) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                f.setNome(filmeAtualizado.getNome());
                f.setDuracao(filmeAtualizado.getDuracao());
                f.setSinopse(filmeAtualizado.getSinopse());
                f.setValor(filmeAtualizado.getValor());
                return;
            }
        }
    }





    
}
