package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import Listeners.FaturasListener;
import Models.Fatura;
import Models.Singleton;
import Utils.JsonParser;
import Utils.Public;

public class MainActivity extends AppCompatActivity {

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
        SharedPreferences sharedPreferences = getSharedPreferences(Public.SHARED_FILE, MODE_PRIVATE);
        if(!JsonParser.isConnected(this)){
            menu.removeItem(R.id.app_bar_cart);
            menu.removeItem(R.id.app_bar_search);
            if (!sharedPreferences.contains(Public.TOKEN)) {
                Singleton.getInstance(this).getFaturasAPI(this);
                menu.removeItem(R.id.app_bar_personalArea);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences(Public.SHARED_FILE, MODE_PRIVATE);
        int itemId = item.getItemId();
        Intent i = null;
        boolean isLogged = sharedPreferences.contains(Public.TOKEN);
        if(itemId != R.id.app_bar_search && !isLogged) {
            i = new Intent(this, Login.class);
            startActivity(i);
            return super.onOptionsItemSelected(item);
        }

        switch (itemId){
            case R.id.app_bar_cart:
                i = new Intent(this, ShoppingCart.class);
                break;

            case R.id.app_bar_search:
                i = new Intent(this, FiltersActivity.class);
                break;

            case R.id.app_bar_personalArea:
                i = new Intent(this, PersonalArea.class);
                break;
        }
        if (i != null) {
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}