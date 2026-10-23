package modelo;

public class Estudante  extends Usuario{
    public Estudante(String user , String cpf ,String cpf , String senha , char sexo , String email , int idade , String nomeCartao , String numeroCartao , String cvv){

        super(user , cpf , senha , sexo , email , idade , nomeCartao , numeroCartao , cvv);
    }

    @Override 
    public double comprarBilhete(Compra compra){
        return compra.getValorTotal() * 0.5;
    }

    
}
