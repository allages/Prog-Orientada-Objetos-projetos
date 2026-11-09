package modelo;

public class Critico extends Usuario {
    private String origem;

    public Critico(String user, String cpf, String senha, char sexo, String email, int idade,
                   String nomeCartao, String numeroCartao, String cvv, String origem) {
        super(user, cpf, senha, sexo, email, idade, nomeCartao, numeroCartao, cvv);
        this.origem = origem;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    @Override
    public double comprarBilhete(Compra compra) {
        return 0.0;
    }

    public void atribuirNota(double nota, Filme filme) {
        if (nota < 0 || nota > 10) {
            return;
        }
        int quantidade = filme.getQuantidade_criticos();

        double novaMedia = (filme.getNota() * quantidade + nota) / (quantidade + 1);

        filme.setNota(novaMedia);
        filme.setQuantidade_criticos(quantidade + 1);
    }

    public void atribuirCritica(String critica, Filme filme) {
        Critica novaCritica = new Critica(getUser(), origem, critica);

        Critica[] criticas = filme.getCritica();

        for (int i = 0; i < criticas.length; i++) {
            if (criticas[i] == null) {
                criticas[i] = novaCritica;
                return;
            }
        }
    }
}
