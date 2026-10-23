package modelo;

public class TiposSala {
    COMUM("Comun", 1.0),
    TRES_D("3d" , 1.2) ,
    XD("xd" , 1.3),
    XD_3D("XD/3D", 1.4);

    private String tipo;
    private double multiplicador;


    TiposSala(String tipo , double multiplicador){
        this.tipo = tipo;
        this.multiplicador = multiplicador;
    }

    public String getTipo(){
        return tipo;
    }
    public double getMultiplicador(){
        return multiplicador;
    }
    
}
