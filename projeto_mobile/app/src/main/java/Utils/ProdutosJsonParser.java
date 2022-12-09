package Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Models.Produto;

public class ProdutosJsonParser {

    public static ArrayList<Produto> parserJsonProdutos(JSONArray response){
        ArrayList<Produto> produtos = new ArrayList<>();
        try {
            for (int i=0; i<response.length(); i++){
                JSONObject produto = (JSONObject) response.get(i);
                int id= produto.getInt("id");
                String nome = produto.getString("nome");
                String imagem = produto.getString("imagem");
                String precoString = produto.getString("preco");
                double preco= Double.parseDouble(precoString);
                Produto produtoAux = new Produto(id,nome,null,imagem,preco);
                produtos.add(produtoAux);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return produtos;
    }


    public static Produto parserJsonProduto(String response){
        Produto produtoAux = null;
        try {
            JSONObject produto = new JSONObject(response);
            int id= produto.getInt("id");
            String nome = produto.getString("nome");
            String imagem = produto.getString("imagem");
            String precoString = produto.getString("preco");
            String descricao = produto.getString("descricao");
            double preco= Double.parseDouble(precoString);
            produtoAux = new Produto(id,nome,descricao,imagem,preco);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return produtoAux;
    }

    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
