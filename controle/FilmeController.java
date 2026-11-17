package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.Administrador;
import modelo.Filme;
import modelo.GerenciaFilmes;
import modelo.Sala;
import modelo.Sessao;
import modelo.VendasException;

public class FilmeController {

    public List<Filme> listarFilmes() {
        return new ArrayList<>(Administrador.getFilmes());
    }

    public Filme buscarFilme(String nome) throws VendasException {
        Filme filme = procurar(nome);
        if (filme == null) {
            throw new VendasException("Filme não encontrado: " + nome);
        }
        return filme;
    }

    public void incluirFilme(GerenciaFilmes gerente, Filme filme) throws VendasException {
        validar(filme);
        if (procurar(filme.getNome()) != null) {
            throw new VendasException("Já existe um filme com este nome");
        }
        gerente.incluirFilme(filme);
    }

    public void alterarFilme(GerenciaFilmes gerente, String nome, Filme atualizado) throws VendasException {
        buscarFilme(nome);
        validar(atualizado);
        if (!nome.equalsIgnoreCase(atualizado.getNome()) && procurar(atualizado.getNome()) != null) {
            throw new VendasException("Já existe um filme com este nome");
        }
        gerente.alterarFilme(nome, atualizado);
    }

    public void excluirFilme(GerenciaFilmes gerente, String nome) throws VendasException {
        buscarFilme(nome);
        gerente.excluirFilme(nome);
    }

    public Sessao criarSessao(Sala sala, int indice, Filme filme, String horario) throws VendasException {
        Sessao[] sessoes = sala.getSessoes();
        if (indice < 0 || indice >= sessoes.length) {
            throw new VendasException("Horário da grade inexistente: " + indice);
        }
        if (sessoes[indice] != null) {
            throw new VendasException("Já existe uma sessão neste horário da grade");
        }
        if (horario == null || !horario.matches(HORA + "( - " + HORA + ")?")) {
            throw new VendasException("Horário inválido: use HH:mm ou HH:mm - HH:mm");
        }
        if (!filme.isEmCartaz() || procurar(filme.getNome()) == null) {
            throw VendasException.foraDeCartaz(filme.getNome());
        }
        for (Sessao s : sessoes) {
            if (s != null && s.getHorario().equals(horario)) {
                throw new VendasException("A sala já tem uma sessão neste horário");
            }
        }
        Sessao nova = new Sessao(filme, horario);
        sessoes[indice] = nova;
        return nova;
    }

    public void removerSessao(Sala sala, int indice) throws VendasException {
        Sessao[] sessoes = sala.getSessoes();
        if (indice < 0 || indice >= sessoes.length || sessoes[indice] == null) {
            throw new VendasException("Não há sessão neste horário da grade");
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                if (sessoes[indice].getCadeira(i, j).isOcupada()) {
                    throw new VendasException("A sessão já possui bilhetes vendidos");
                }
            }
        }
        sessoes[indice] = null;
    }

    private static final String HORA = "([01]\\d|2[0-3]):[0-5]\\d";

    private Filme procurar(String nome) {
        for (Filme f : Administrador.getFilmes()) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                return f;
            }
        }
        return null;
    }

    private void validar(Filme filme) throws VendasException {
        if (filme.getNome() == null || filme.getNome().trim().isEmpty()) {
            throw new VendasException("O nome do filme é obrigatório");
        }
        if (filme.getDuracao() <= 0) {
            throw new VendasException("A duração deve ser maior que zero");
        }
        if (filme.getValor() < 0) {
            throw new VendasException("O valor não pode ser negativo");
        }
    }
}
