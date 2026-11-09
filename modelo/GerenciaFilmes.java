package modelo;

public interface GerenciaFilmes {
    void incluirFilme(Filme filme);

    void excluirFilme(String nome);

    void alterarFilme(String nome, Filme filmeAtualizado);
}
