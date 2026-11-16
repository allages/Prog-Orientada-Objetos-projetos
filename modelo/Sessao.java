package modelo;

public class Sessao {
    private Filme filme;
    private String horario;
    private Cadeira[][] cadeiras = new Cadeira[10][15];
    private static String horaAtual = "12:00";


    public Sessao(Filme filme , String horario ){
        this.filme = filme;
        this.horario = horario;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 15; j++){
                cadeiras[i][j] = new Cadeira(i,j, false);
            }
        }

    }

    public Filme getFilme(){
        return filme;
    }
    public void setFilme(Filme filme){
        this.filme = filme;
    }
    public String getHorario(){
        return horario;
    }
    public void setHorario(String horario){
        this.horario = horario;
    }
    public Cadeira getCadeira(int linha, int coluna) {
        if (linha >= 0 && linha < 10 && coluna >= 0 && coluna < 15) {
            return cadeiras[linha][coluna];
        }
        return null;
    }
    public void ocuparCadeira(int linha, int coluna) throws VendasException {
        Cadeira cadeira = getCadeira(linha, coluna);

        if (cadeira == null) {
            throw new VendasException("Cadeira inexistente");
        }
        if (cadeira.isOcupada()) {
            throw VendasException.poltronaOcupada(linha * 15 + coluna + 1);
        }
        cadeira.setOcupada(true);
    }

    public Cadeira[] sugerirCadeirasJuntas(int quantidade) {
        if (quantidade < 1 || quantidade > 15) {
            return null;
        }

        for (int i = 0; i < 10; i+=1) {
            int seguidas = 0;

            for (int j = 0; j < 15; j+=1) {
                if (cadeiras[i][j].isOcupada()) {
                    seguidas = 0;
                } else {
                    seguidas+=1;
                }

                if (seguidas == quantidade) {
                    Cadeira[] sugestao = new Cadeira[quantidade];
                    for (int k = 0; k < quantidade; k+=1) {
                        sugestao[k] = cadeiras[i][j - quantidade + 1 + k];
                    }
                    return sugestao;
                }
            }
        }
        return null;
    }

    public boolean jaPassou(String agora) {
        return horario.substring(0, 5).compareTo(agora) < 0;
    }

    public void validarVenda(String agora) throws VendasException {
        if (!filme.isEmCartaz()) {
            throw VendasException.foraDeCartaz(filme.getNome());
        }
        if (jaPassou(agora)) {
            throw VendasException.horarioPassado(filme.getNome());
        }
    }

    public static String getHoraAtual() {
        return horaAtual;
    }

    public void validarVenda() throws VendasException {
        validarVenda(horaAtual);
    }


}
