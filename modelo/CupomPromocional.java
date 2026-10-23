package modelo;

public class CupomPromocional {

    DESCONTO10("DESCONTO DE 10%", 0.10);
    DESCONTO20("DESCONTO DE 20%", 0.20);
    DESCONTO30("DESCONTO DE 30%" , 0.30);

    private String nome;
    private double desconto;

    CupomPromocional(String nome , double desconto){
        this.nome = nome;
        this.desconto = desconto;
    }

    public String getNome(){
        return nome;
    }
    public double getDesconto(){
        return desconto;
    }
    
}
