package Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Models.Carrinho;
import Models.Fatura;
import Models.Produto;
import Models.Signup;

public class JsonParser {

    //region Produto
    public static ArrayList<Produto> parserJsonProdutos(JSONArray response){
        ArrayList<Produto> produtos = new ArrayList<>();
        try {

            for (int i=0; i<response.length(); i++){
                JSONObject jsonObject = (JSONObject) response.getJSONObject(i);
                JSONObject produto = jsonObject.getJSONObject("produto");
                boolean inStock = jsonObject.getBoolean("stock");
                int id= produto.getInt("id");
                String nome = produto.getString("nome");
                String imagem = produto.getString("imagem");
                String precoString = produto.getString("preco");
                double preco= Double.parseDouble(precoString);
                Produto produtoAux = new Produto(id,nome,null,imagem,preco, null ,null,inStock);
                produtos.add(produtoAux);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return produtos;
    }


    public static Produto parserJsonProduto(JSONObject response){
        Produto produtoAux = null;
        try {
            JSONObject produto = response.getJSONObject("produto");
            boolean inStock = response.getBoolean("stock");
            int id= produto.getInt("id");
            String nome = produto.getString("nome");
            String imagem = produto.getString("imagem");
            String precoString = produto.getString("preco");
            String descricao = produto.getString("descricao");
            String marca = produto.getString("id_Marca");
            String referencia = produto.getString("referencia");
            double preco= Double.parseDouble(precoString);
            produtoAux = new Produto(id,nome,descricao,imagem,preco, marca ,referencia,inStock);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return produtoAux;
    }

    //endregion

    //region Carrinho

    public static ArrayList<Carrinho> parserJsonCarrinho (JSONArray response){
        ArrayList<Carrinho> carrinhos = new ArrayList<>();
        try {
            for (int i=0; i<response.length(); i++){
                JSONObject jsonObject = (JSONObject) response.getJSONObject(i);
                JSONObject produto = jsonObject.getJSONObject("produto");
                int id= produto.getInt("id");
                String nome = produto.getString("nome");
                String imagem = produto.getString("imagem");
                String precoString = produto.getString("preco");
                double preco= Double.parseDouble(precoString);
                int quantidade = jsonObject.getInt("quantidade");
                boolean inStock = jsonObject.getBoolean("stock");
                Produto produtoAux = new Produto(id,nome,null,imagem,preco, null ,null,inStock);
                Carrinho carrinho = new Carrinho(produtoAux,quantidade);
                carrinhos.add(carrinho);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return carrinhos;
    }

    //endregion

    //region Fatura

    //endregion

    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
