package Models;

public class Produto {
    String nome, detalhes;
    int id;
    int capa;
    double preco;

    public Produto(int id, String nome, String detalhes, int capa, double preco) {
        this.nome = nome;
        this.detalhes = detalhes;
        this.id = id;
        this.capa = capa;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public int getCapa() {
        return capa;
    }

    public void setCapa(int capa) {
        this.capa = capa;
    }

}
