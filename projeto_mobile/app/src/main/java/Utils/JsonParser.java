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
import Models.Dados;
import Models.DBHelper;
import Models.Fatura;
import Models.LinhaFatura;
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

    public static Dados parserJsonDados(JSONObject response){
        Dados dadosAux = null;
        try {
            JSONObject dados = response;
            String nome = dados.getString("nome");
            String telefone = dados.getString("telefone");
            String nif = dados.getString("nif");
            String morada = dados.getString("morada");
            String codPostal = dados.getString("codPostal");
            dadosAux = new Dados(nome,telefone,nif,morada, codPostal);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return dadosAux;
    }
    //endregion

    //region FaturaActivity

    public static ArrayList<Fatura> parserJsonFaturas(JSONArray response){
        ArrayList<Fatura> faturas = new ArrayList<>();
        try {
            for (int i=0; i<response.length(); i++){
                JSONObject jsonObject = (JSONObject) response.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String data = jsonObject.getString("data");
                double valorTotal = Double.parseDouble(jsonObject.getString("valorTotal"));
                double ivaTotal = Double.parseDouble(jsonObject.getString("valorIva"));
                String morada = jsonObject.getString("morada");
                String nif = jsonObject.getString("nif");
                Fatura auxFatura = new Fatura(id,nif,morada,data,valorTotal,ivaTotal);
                faturas.add(auxFatura);
            }

        }catch (JSONException e){
            System.out.println(e.getMessage());
        }
        return faturas;
    }

    public static ArrayList<LinhaFatura> parserJsonLinhas(JSONArray response){
        ArrayList<LinhaFatura> linhas = new ArrayList<>();
        try {
            for (int i=0; i<response.length(); i++){
                JSONObject jsonObject = (JSONObject) response.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String produto_nome = jsonObject.getString("produto_nome");
                String produto_referencia = jsonObject.getString("produto_referencia");
                int id_Fatura = jsonObject.getInt("id_Fatura");
                int quantidade = jsonObject.getInt("quantidade");
                double valor = Double.parseDouble(jsonObject.getString("valor"));
                double valorIva = Double.parseDouble(jsonObject.getString("valorIva"));
                LinhaFatura linha = new LinhaFatura(id,id_Fatura,produto_nome, produto_referencia, quantidade, valor, valorIva);
                linhas.add(linha);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return linhas;
    }
    //endregion

    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
