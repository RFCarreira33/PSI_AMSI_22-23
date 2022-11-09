package Models;

public class Carrinho {
    int id;
    String nome;
    int capa;
    double preco;

    public Carrinho(int id, String nome, int capa, double preco) {
        this.id = id;
        this.nome = nome;
        this.capa = capa;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapa() {
        return capa;
    }

    public void setCapa(int capa) {
        this.capa = capa;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
