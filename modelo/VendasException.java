package modelo;

public class VendasException extends Exception {
    private static final long serialVersionUID = 1L;


    public VendasException(String mensagem) {
        super(mensagem);
    }

    public static VendasException horarioPassado(String filme) {
        return new VendasException("A sessão do filme " + filme + " já passou");
    }

    public static VendasException poltronaOcupada(int poltrona) {
        return new VendasException("A poltrona " + poltrona + " já se encontra ocupada");
    }

    public static VendasException foraDeCartaz(String filme) {
        return new VendasException("O filme " + filme + " não está mais em cartaz");
    }
}
