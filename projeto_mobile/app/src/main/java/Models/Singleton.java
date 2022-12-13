package Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Listeners.CartListener;
import Listeners.DadosListener;
import Listeners.DetailsListener;
import Listeners.ProdutoListener;
import Utils.JsonParser;
import Utils.Public;

public class Singleton {
    private static Singleton single_instance = null;
    private ArrayList<Produto> produtos;
    private static RequestQueue volleyQueue = null;
    private ProdutoListener produtoListener;
    private CartListener cartListener;
    private DetailsListener detailsListener;
    private DadosListener dadosListener;

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

    //region Listeners

    public void setProdutoListener(ProdutoListener produtoListener) {
        this.produtoListener = produtoListener;
    }

    public void setDetailsListener(DetailsListener detailsListener) {
        this.detailsListener = detailsListener;
    }

    public void setDadosListener(DadosListener dadosListener) {
        this.dadosListener = dadosListener;
    }

    public void setCartListener(CartListener cartListener) {
        this.cartListener = cartListener;
    }

    //endregion Listeners

    //region Produto
    public void getAllProdutosAPI(final Context context) {
        if(!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Public.apiURL + "/produtos", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    produtos = JsonParser.parserJsonProdutos(response);
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

    public void getProdutoAPI(final Context context, int id) {
        if(!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Public.apiURL + "/produtos/" + id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Produto produto = JsonParser.parserJsonProduto(response);
                    if (detailsListener != null)
                        detailsListener.onRefreshDetails(produto);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(jsonObjectRequest);
        }
    }

    //endregion produtos

    //region User
    public void loginUserAPI(final Context context, final String credentials){
        if (!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Public.apiURL + "/user/login", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Public.SHARED_FILE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Public.TOKEN, response);
                    editor.apply();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + credentials);
                    return headers;
                }
                };
            volleyQueue.add(stringRequest);
        }

    }

    public void createUserAPI(final Context context, final Signup user){
        if(!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Public.apiURL + "/user/register", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected java.util.Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", user.getUsername());
                    params.put("email", user.getEmail());
                    params.put("password", user.getPassword());
                    params.put("nome", user.getNome());
                    params.put("codPostal", user.getCodPostal());
                    params.put("telefone", user.getTelefone());
                    params.put("nif", user.getNif());
                    params.put("morada", user.getMorada());
                    return params;
                }
            };
            volleyQueue.add(stringRequest);
        }

    }

    public void getDadosAPI(final Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Public.SHARED_FILE, Context.MODE_PRIVATE);
        if(!JsonParser.isConnected(context))
        {
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }
        else
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Public.apiURL + "/dados?access-token=" + sharedPreferences.getString(Public.TOKEN,null), null, new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    Dados dado = JsonParser.parserJsonDados(response);
                    if (dadosListener != null)
                    {
                        dadosListener.onRefreshDados(dado);
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(jsonObjectRequest);
        }
    }

    public void updateDadosAPI(final Context context, final Dados dados){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Public.SHARED_FILE, Context.MODE_PRIVATE);
        if(!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, Public.apiURL + "/dados/update?access-token="+ sharedPreferences.getString(Public.TOKEN,null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected java.util.Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nome", dados.getNome());
                    params.put("codPostal", dados.getCodPostal());
                    params.put("telefone", dados.getTelefone());
                    params.put("nif", dados.getNif());
                    params.put("morada", dados.getMorada());
                    return params;
                }
            };
            volleyQueue.add(stringRequest);
        }

    }
    //endregion User

    //region Cart

    public void addCartAPI(final Context context, final int idProduto, final int quantidade){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Public.SHARED_FILE, Context.MODE_PRIVATE);
        if(!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, Public.apiURL + "/carrinho/create?access-token="+ sharedPreferences.getString(Public.TOKEN, null),
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            }){
                @Override
                protected java.util.Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_Produto", String.valueOf(idProduto));
                    params.put("quantidade", String.valueOf(quantidade));
                    return params;
                }
            };
            volleyQueue.add(stringRequest);
        }
    }

   public void viewCartAPI(final Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Public.SHARED_FILE, Context.MODE_PRIVATE);
        if(!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Public.apiURL + "/carrinho?access-token="+ sharedPreferences.getString(Public.TOKEN, null), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<Carrinho> carrinhos = JsonParser.parserJsonCarrinho(response);
                    if (cartListener != null)
                        cartListener.onRefreshCart(carrinhos);
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

    public void clearCartAPI(final Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Public.SHARED_FILE, Context.MODE_PRIVATE);
        if(!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Public.apiURL + "/carrinho/delete?access-token="+ sharedPreferences.getString(Public.TOKEN, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(stringRequest);
        }
    }

    public void removeCartAPI(final Context context, final int idProduto){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Public.SHARED_FILE, Context.MODE_PRIVATE);
        if(!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Public.apiURL + "/carrinho/remove?access-token="+ sharedPreferences.getString(Public.TOKEN, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected java.util.Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_Produto", String.valueOf(idProduto));
                    return params;
                }
            };
            volleyQueue.add(stringRequest);
        }
    }

    public void buyCartAPI(final Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Public.SHARED_FILE, Context.MODE_PRIVATE);
        if(!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Public.apiURL + "/carrinho/buy?access-token="+ sharedPreferences.getString(Public.TOKEN, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(stringRequest);
        }
    }

    public void updateCartAPI(final Context context, final int idProduto, final int quantidade){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Public.SHARED_FILE, Context.MODE_PRIVATE);
        if(!JsonParser.isConnected(context)){
            Toast.makeText(context, context.getString(R.string.sem_internet), Toast.LENGTH_SHORT).show();
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, Public.apiURL + "/carrinho/update?access-token="+ sharedPreferences.getString(Public.TOKEN, null), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected java.util.Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_Produto", String.valueOf(idProduto));
                    params.put("quantidade", String.valueOf(quantidade));
                    return params;
                }
            };
            volleyQueue.add(stringRequest);
        }
    }
    //endregion Cart

}