package Models;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.constraintlayout.motion.utils.StopLogic;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeto.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Queue;

import Listeners.ProdutoListener;
import Utils.ProdutosJsonParser;

public class Singleton {
    private static Singleton single_instance = null;
    private ArrayList<Produto> produtos;
    private static RequestQueue volleyQueue = null;
    private static final String URL="http://10.0.2.2:8080/api";
    private ProdutoListener produtoListener;

    public static synchronized Singleton getInstance(Context context) {
        if (single_instance == null) {
            single_instance = new Singleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return single_instance;
    }

    private Singleton(Context context) {
        produtos = new ArrayList<>();
    }

    //section produtos

    public void setProdutoListener(ProdutoListener produtoListener) {
        this.produtoListener = produtoListener;
    }

    public void getAllProdutosAPI(final Context context) {
        if(!ProdutosJsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL + "/produtos", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    produtos = ProdutosJsonParser.parserJsonProdutos(response);
                    if (produtoListener != null)
                        produtoListener.onRefreshProdutos(produtos);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(jsonArrayRequest);
        }
    }



/*
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

 */
}