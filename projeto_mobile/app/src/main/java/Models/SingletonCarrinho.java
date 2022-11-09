package Models;

import com.example.projeto.R;

import java.util.ArrayList;

public class SingletonCarrinho {

    private static SingletonCarrinho single_instance = null;
    private ArrayList<Carrinho> carrinhos;

    public static synchronized SingletonCarrinho getInstance()
    {
        if (single_instance == null)
            single_instance = new SingletonCarrinho();

        return single_instance;
    }


    private SingletonCarrinho() {

        carrinhos = new ArrayList<>();
        carrinhos.add(new Carrinho(1,"Gtx 1060", R.drawable.cooler, 331.33));
        carrinhos.add(new Carrinho(2,"Gtx 1080", R.drawable.gpu, 431.33));
        carrinhos.add(new Carrinho(3,"Gt 710", R.drawable.gt730, 31.33));
    }

    public ArrayList<Carrinho> getCarrinhos() {
        return new ArrayList(carrinhos);
    }

    public Carrinho getCarrinho(int id){
        for (Carrinho c:carrinhos){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }

}

