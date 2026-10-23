package modelo;

public enum Produto{
    PIPOCA("Pipoca", 18.00),
    REFRIGERANTE("Refrigerante", 10.00),
    CHOCOLATE("Chocolate", 7.00),
    AGUA("Agua",5.00) , 
    HOT_DOG("Hot dog", 15.00) , 
    COMBO("Combo p + r", 25.00);


    private String nome;
    private double valor;


    Produto(String nome , double valor) {
        this.nome = nome;
        this.valor = valor;
    }
    public String getNome(){
        return nome;
    }
    
    
    public double getValor(){
        return valor;
    }


}