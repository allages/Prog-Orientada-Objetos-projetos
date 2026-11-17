package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.Critica;
import modelo.Critico;
import modelo.Filme;
import modelo.VendasException;

public class CriticoController {

    public void avaliar(Critico critico, Filme filme, double nota, String comentario) throws VendasException {
        if (nota < 0 || nota > 10) {
            throw new VendasException("A nota deve estar entre 0 e 10");
        }
        if (comentario == null || comentario.trim().isEmpty()) {
            throw new VendasException("A crítica não pode ser vazia");
        }
        Critica[] criticas = filme.getCritica();
        boolean temEspaco = false;
        for (Critica c : criticas) {
            if (c == null) {
                temEspaco = true;
            } else if (c.getNome().equalsIgnoreCase(critico.getUser())) {
                throw new VendasException("Você já avaliou este filme");
            }
        }
        if (!temEspaco) {
            throw new VendasException("O filme atingiu o limite de críticas");
        }
        critico.atribuirNota(nota, filme);
        critico.atribuirCritica(comentario, filme);
    }

    public List<Critica> listarCriticas(Filme filme) {
        List<Critica> lista = new ArrayList<>();
        for (Critica c : filme.getCritica()) {
            if (c != null) {
                lista.add(c);
            }
        }
        return lista;
    }
}
