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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Models.Dados;
import Models.Singleton;
import Models.DBHelper;
import Models.Fatura;
import Utils.Public;

public class PersonalArea extends AppCompatActivity {

    ActionBar actionBar = null;
    TextView tvNome;
    DBHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_personal_area);
        setTitle("Bem Vindo");

        SharedPreferences sharedPreferences = getSharedPreferences(Public.SHARED_FILE, MODE_PRIVATE);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

        tvNome = findViewById(R.id.tvNome);
        tvNome.setText(sharedPreferences.getString(Public.USER_NOME, ""));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar, menu);
        menu.removeItem(R.id.app_bar_personalArea);
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
        }
        if(i != null){
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCLickLogout(View view){
        dbHelper.removeAllFaturas();
        SharedPreferences sharedPreferences = getSharedPreferences(Public.SHARED_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Public.TOKEN);
        editor.apply();
        Toast.makeText(this, "Logout efetuado com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }

}