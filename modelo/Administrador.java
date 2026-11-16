package modelo;

import java.util.ArrayList;

public class Administrador extends Base implements GerenciaFilmes{
    private double salario;
    private int id;
    private static ArrayList<Base> usuarios = new ArrayList<>();
    private static ArrayList<Filme> filmes = new ArrayList<>();



    public Administrador(String nome ,int idade , String email , double salario , int id){
        super(nome , idade , email);
        this.salario = salario;
        this.id = id;
    }

    public static ArrayList<Base> getUsuarios(){
        return usuarios;
    }
    public static ArrayList<Filme> getFilmes(){
        return filmes;
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
        usuarios.add(this);

    }
    @Override 
    public void alterarUsuario(String nome , int idade , String email){
        setNome(nome);
        setIdade(idade);
        setEmail(email);
    }

    public void excluirUsuario(String email){
        usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(email));

    }
    @Override 
    public void excluirFilme(String nome){
        for (Filme f : filmes) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                f.setEmCartaz(false);
            }
        }
        filmes.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }

    @Override 
    public void incluirFilme(Filme filme){
        filmes.add(filme);

    }
    @Override 
    public void alterarFilme(String nome , Filme filmeAtualizado){
        for (int i = 0 ; i< filmes.size() ; i++){
            if (filmes.get(i).getNome().equalsIgnoreCase(nome)){
                filmes.set(i , filmeAtualizado);
                return;
            }
        }

    }

}