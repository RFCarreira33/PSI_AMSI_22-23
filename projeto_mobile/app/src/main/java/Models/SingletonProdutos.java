package Models;

import com.example.projeto.R;

import java.util.ArrayList;

public class SingletonProdutos {
    private static SingletonProdutos single_instance = null;
    private ArrayList<Produto> produtos;

    public static synchronized SingletonProdutos getInstance()
    {
        if (single_instance == null)
            single_instance = new SingletonProdutos();

        return single_instance;
    }


    private SingletonProdutos() {

        produtos = new ArrayList<>();
        produtos.add(new Produto(1,"Gtx 1060", "6gb Vram", R.drawable.cooler, 331.33));
        produtos.add(new Produto(2,"Gtx 1070", "8gb Vram", R.drawable.gpu, 431.33));
        produtos.add(new Produto(3,"Gt 710", "2gb Vram", R.drawable.gt730, 31.33));
    }

    public ArrayList<Produto> getProdutos() {
        return new ArrayList(produtos);
    }

    public Produto getProduto(int id){
        for (Produto p:produtos){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

}

