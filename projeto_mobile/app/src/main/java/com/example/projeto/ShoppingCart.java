package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ShoppingCart extends AppCompatActivity {

    private TextView tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        setTitle("Shopping Cart");
        tvTotalPrice = findViewById(R.id.tvPrecoTotal);

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
}