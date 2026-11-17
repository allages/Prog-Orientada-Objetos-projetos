package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.Administrador;
import modelo.Base;
import modelo.Funcionario;
import modelo.VendasException;

public class EquipeController {

    public Funcionario cadastrarFuncionario(Administrador solicitante, String nome, int idade,
                                            String email, double salario) throws VendasException {
        if (solicitante == null) {
            throw new VendasException("Apenas um administrador pode cadastrar funcionários");
        }
        validar(nome, idade, email);
        if (salario < 0) {
            throw new VendasException("O salário não pode ser negativo");
        }
        if (buscarPorEmail(email) != null) {
            throw new VendasException("Já existe alguém da equipe com este e-mail");
        }
        Funcionario novo = new Funcionario(email, idade, nome, salario);
        novo.adicionarUsuario();
        return novo;
    }

    public Funcionario loginFuncionario(String email) throws VendasException {
        Base pessoa = buscarPorEmail(email);
        if (pessoa instanceof Funcionario) {
            return (Funcionario) pessoa;
        }
        throw new VendasException("Funcionário não encontrado");
    }

    public Administrador loginAdministrador(String email, int id) throws VendasException {
        Base pessoa = buscarPorEmail(email);
        if (pessoa instanceof Administrador && ((Administrador) pessoa).getId() == id) {
            return (Administrador) pessoa;
        }
        throw new VendasException("E-mail ou id de administrador inválido");
    }

    public List<Base> listarEquipe() {
        return new ArrayList<>(Administrador.getUsuarios());
    }

    public void alterarDados(Base pessoa, String nome, int idade, String email) throws VendasException {
        validar(nome, idade, email);
        Base outro = buscarPorEmail(email);
        if (outro != null && outro != pessoa) {
            throw new VendasException("Já existe alguém da equipe com este e-mail");
        }
        pessoa.alterarUsuario(nome, idade, email);
    }

    public Base buscarPorEmail(String email) {
        for (Base pessoa : Administrador.getUsuarios()) {
            if (pessoa.getEmail().equalsIgnoreCase(email)) {
                return pessoa;
            }
        }
        return null;
    }

    private void validar(String nome, int idade, String email) throws VendasException {
        if (nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new VendasException("Nome e e-mail são obrigatórios");
        }
        if (idade <= 0) {
            throw new VendasException("Idade inválida");
        }
    }
}
