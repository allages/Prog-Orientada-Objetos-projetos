package controle;

import modelo.Administrador;
import modelo.Critico;
import modelo.Estudante;
import modelo.Usuario;
import modelo.VendasException;

public class UsuarioController {

    public Usuario cadastrar(String tipo, String user, String cpf, String senha, char sexo, String email,
                             int idade, String nomeCartao, String numeroCartao, String cvv,
                             String origem) throws VendasException {
        if (vazio(user) || vazio(cpf) || vazio(senha) || vazio(email)) {
            throw new VendasException("Usuário, CPF, senha e e-mail são obrigatórios");
        }
        if (idade <= 0) {
            throw new VendasException("Idade inválida");
        }
        if (buscarPorUser(user) != null) {
            throw new VendasException("Já existe um usuário com este login");
        }
        for (Usuario u : Administrador.getClientes()) {
            if (u.getCpf().equals(cpf)) {
                throw new VendasException("Já existe um usuário com este CPF");
            }
        }
        if (emailEmUso(email, null)) {
            throw new VendasException("Já existe um usuário com este e-mail");
        }

        Usuario novo;
        if (tipo == null || tipo.equalsIgnoreCase("comum")) {
            novo = new Usuario(user, cpf, senha, sexo, email, idade, nomeCartao, numeroCartao, cvv);
        } else if (tipo.equalsIgnoreCase("estudante")) {
            novo = new Estudante(user, cpf, senha, sexo, email, idade, nomeCartao, numeroCartao, cvv);
        } else if (tipo.equalsIgnoreCase("critico")) {
            if (vazio(origem)) {
                throw new VendasException("O crítico precisa informar o órgão de origem");
            }
            novo = new Critico(user, cpf, senha, sexo, email, idade, nomeCartao, numeroCartao, cvv, origem);
        } else {
            throw new VendasException("Tipo de usuário inválido: " + tipo);
        }

        Administrador.getClientes().add(novo);
        return novo;
    }

    public Usuario login(String user, String senha) throws VendasException {
        Usuario usuario = buscarPorUser(user);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            throw new VendasException("Usuário ou senha inválidos");
        }
        return usuario;
    }

    public Usuario buscarPorUser(String user) {
        for (Usuario u : Administrador.getClientes()) {
            if (u.getUser().equalsIgnoreCase(user)) {
                return u;
            }
        }
        return null;
    }

    public void alterarDados(Usuario usuario, String email, int idade, String nomeCartao,
                             String numeroCartao, String cvv) throws VendasException {
        if (vazio(email)) {
            throw new VendasException("O e-mail é obrigatório");
        }
        if (idade <= 0) {
            throw new VendasException("Idade inválida");
        }
        if (emailEmUso(email, usuario)) {
            throw new VendasException("Já existe um usuário com este e-mail");
        }
        usuario.setEmail(email);
        usuario.setIdade(idade);
        usuario.setNomeCartao(nomeCartao);
        usuario.setNumeroCartao(numeroCartao);
        usuario.setCvv(cvv);
    }

    public void alterarSenha(Usuario usuario, String senhaAtual, String novaSenha) throws VendasException {
        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new VendasException("Senha atual incorreta");
        }
        if (vazio(novaSenha)) {
            throw new VendasException("A nova senha não pode ser vazia");
        }
        usuario.setSenha(novaSenha);
    }

    private boolean emailEmUso(String email, Usuario ignorar) {
        for (Usuario u : Administrador.getClientes()) {
            if (u != ignorar && u.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private boolean vazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
