package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.CartListAdapter;
import Listeners.CartListener;
import Models.Carrinho;
import Models.Singleton;

public class ShoppingCart extends AppCompatActivity implements CartListener {

    private TextView tvPrecoTotal,tvNumeroArtigos ;
    private ListView lvCart;
    private double Total;
    private int TotalArtigos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        setTitle("Carrinho de Compras");
        tvPrecoTotal = findViewById(R.id.tvPrecoTotal);
        tvNumeroArtigos = findViewById(R.id.tvArtigos);
        lvCart = findViewById(R.id.lvCart);

        lvCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Carrinho carrinho = (Carrinho) adapterView.getItemAtPosition(position);
                int quantidade = carrinho.getQuantidade();
                Intent intent = new Intent(ShoppingCart.this, EditCart.class);
                intent.putExtra("Quantidade", quantidade);
                intent.putExtra("Produto", carrinho.getProduto().getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        Total = 0;
        TotalArtigos = 0;
        updateCarrinho();
        super.onResume();
    }

    private void updateCarrinho(){
        Singleton.getInstance(this).setCartListener(this);
        Singleton.getInstance(this).viewCartAPI(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        menu.removeItem(R.id.app_bar_cart);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent i = null;

        switch (itemId){
            case R.id.app_bar_category:
                i = new Intent(this, Login.class);
                break;
            case R.id.app_bar_personalArea:
                i = new Intent(this, PersonalArea.class);
                break;
        }

        if(i != null){
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickClear(View view){
        Singleton.getInstance(this).clearCartAPI(this);
        finish();
    }

    public void onClickBuy(View view){
        Singleton.getInstance(this).buyCartAPI(this);
        finish();
    }




    @Override
    public void onRefreshCart(ArrayList<Carrinho> carrinhos) {
        if(carrinhos != null) {
            for (Carrinho c : carrinhos) {
                Total += c.getProduto().getPreco() * c.getQuantidade();
                TotalArtigos += c.getQuantidade();
            }
            tvPrecoTotal.setText(String.format("%s€", Total));
            tvNumeroArtigos.setText(String.format("Artigos: %s", TotalArtigos));
            lvCart.setAdapter(new CartListAdapter(this, carrinhos));
        }
    }


}