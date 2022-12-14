package Models;

import java.util.concurrent.atomic.AtomicInteger;

public class Marca {
    private static final AtomicInteger count = new AtomicInteger(0);
    int id;
    String nome;

    public Marca(String nome) {
        this.id = count.incrementAndGet();
        this.nome = nome;
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
}
