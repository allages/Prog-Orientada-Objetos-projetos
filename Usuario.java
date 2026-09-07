public class Usuario {
    private String user;
    private String cpf;
    private String senha;
    private char sexo;
    private String email;
    private int idade;
    private String nomeCartao;
    private String numeroCartao;
    private String cvv;
    private List <Compra> compras;
    
    
    public Usuario(String user, String cpf, String senha, char sexo, String email, int idade, String nomeCartao, String numeroCartao, String cvv) {
        this.user = user;
        this.cpf = cpf;
        this.senha = senha;
        this.sexo = sexo;
        this.email = email;
        this.idade = idade;
        this.nomeCartao = nomeCartao;
        this.numeroCartao = numeroCartao;
        this.cvv = cvv;
        this.compras = new ArrayList<>();
        
    
    }
    
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    
}