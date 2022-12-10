package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import Listeners.DetailsListener;
import Models.Produto;
import Models.Singleton;
import Utils.Public;

public class DetailsProduct extends AppCompatActivity implements DetailsListener {

    private ImageView imgCapa;
    private TextView tvDetails, tvStock, tvPreco;
    private ImageButton btnMore, btnMinus;
    private EditText numQuant;
    private Button btnCart;
    ActionBar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);


        imgCapa = findViewById(R.id.imageCapa);
        tvDetails = findViewById(R.id.tvDetalhes);
        tvStock = findViewById(R.id.tvStock);
        tvPreco = findViewById(R.id.tvPreco);
        btnMinus = findViewById(R.id.btnMinus);
        btnMore = findViewById(R.id.btnMore);
        btnCart = findViewById(R.id.btnAddCart);
        numQuant = findViewById(R.id.numQuantity);

        int id = getIntent().getIntExtra("Produto", 0);
        Singleton.getInstance(this).setDetailsListener(this);
        Singleton.getInstance(this).getProdutoAPI(this, id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent i = null;

        switch (itemId){
            case R.id.app_bar_cart:
                i = new Intent(this, ShoppingCart.class);
                break;
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

    private void carregarProduto(Produto produto) {
        tvDetails.setText(produto.getDetalhes());
        tvStock.setText("Em Stock");
        tvPreco.setText(produto.getPreco()+" €");
        setTitle(produto.getNome());
        Glide.with(this)
                .load(Public.imgURL+produto.getCapa())
                .into(imgCapa);

    }

    @Override
    public void onRefreshDetails(Produto produto) {
        if (produto != null){
            carregarProduto(produto);
        }
    }


    //Buttons
    public void onCLickChangeQuantity(View view) {
        int quantity = Integer.parseInt(numQuant.getText().toString());
        int id = view.getId();
        switch (id){
            case R.id.btnMinus:
                if(quantity > 1)
                    quantity--;
                break;
            case R.id.btnMore:
                if (quantity < 20)
                    quantity++;
                break;
        }
        numQuant.setText(String.valueOf(quantity));
    }

    public void onClickAddCart(View view) {
        int id = getIntent().getIntExtra("Produto", 0);
        int quantity = Integer.parseInt(numQuant.getText().toString());
        Singleton.getInstance(this).addCartAPI(this, id, quantity);
    }

}