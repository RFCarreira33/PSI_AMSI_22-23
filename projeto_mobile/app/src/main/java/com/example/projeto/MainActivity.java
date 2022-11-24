package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FragmentContainerView fvProductsGrid, fvCart, fvHome, fvPersonalArea;
    ActionBar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.logo);
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
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

}