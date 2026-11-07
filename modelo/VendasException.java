package modelo;

public class VendasException extends Exception {

    public VendasException(String mensagem){
        super(mensagem);
    }


    public static VendasException horarioPassado(String filme){
        return new VendasException( "A  sessão do " + filme + "ja passou");
    }
    public static VendasException poltronaOcupada(int poltrona){
        return new VendasException("A poltrona" + poltrona + "já se encontra ocupada");
    }
    public static VendasException foraDeCartaz(String filme){
        return new VendasException("O filme " + filme + "não está mais em cartazes")
    }
    

}
