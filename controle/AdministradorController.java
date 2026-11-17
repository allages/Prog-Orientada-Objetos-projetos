package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.Administrador;
import modelo.Base;
import modelo.Compra;
import modelo.Usuario;
import modelo.VendasException;

public class AdministradorController {
    private CompraController compraController;

    public AdministradorController(CompraController compraController) {
        this.compraController = compraController;
    }

    public CompraController getCompraController() {
        return compraController;
    }

    public void setCompraController(CompraController compraController) {
        this.compraController = compraController;
    }

    public List<Usuario> listarClientes() {
        return new ArrayList<>(Administrador.getClientes());
    }

    public void excluirCliente(Administrador admin, String email) throws VendasException {
        exigirAdministrador(admin);

        Usuario alvo = null;
        for (Usuario u : Administrador.getClientes()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                alvo = u;
            }
        }
        if (alvo == null) {
            throw new VendasException("Cliente não encontrado");
        }

        for (Compra compra : new ArrayList<>(alvo.getCompras())) {
            compraController.cancelarCompra(alvo, compra);
        }
        admin.excluirCliente(email);
    }


    public void excluirFuncionario(Administrador admin, String email) throws VendasException {
        exigirAdministrador(admin);

        Base alvo = null;
        for (Base pessoa : Administrador.getUsuarios()) {
            if (pessoa.getEmail().equalsIgnoreCase(email)) {
                alvo = pessoa;
            }
        }
        if (alvo == null) {
            throw new VendasException("Funcionário não encontrado");
        }
        if (alvo instanceof Administrador) {
            throw new VendasException("Não é possível excluir um administrador");
        }
        admin.excluirUsuario(email);
    }

    private void exigirAdministrador(Administrador admin) throws VendasException {
        if (admin == null) {
            throw new VendasException("Apenas um administrador pode fazer esta operação");
        }
    }
}
