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
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;  
    }
    public char getSexo() {
        return sexo;
    }
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;  
    }
    public String getNomeCartao() {
        return nomeCartao;
    }
    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }
    public String getNumeroCartao() {
        return numeroCartao;
    }
    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public List<Compra> getCompras() {
        return compras;
    }
    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
    
    public void adicionarCompra(Compra compra) {
        compras.add(compra);
    }
    public void removerCompra(Compra compra) {
        compras.remove(compra);
    }
    
}
    
