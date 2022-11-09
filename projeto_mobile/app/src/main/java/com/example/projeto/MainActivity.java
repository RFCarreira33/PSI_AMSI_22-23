package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private boolean user = false;
    private FragmentContainerView fragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragView = findViewById(R.id.fragmentContainerView);
    }


    public void onClickLogin(View view) {
        Intent i;

        if(user){
            i = new Intent(MainActivity.this, PersonalArea.class);
        }else{
            user = true;
            i = new Intent(MainActivity.this, Login.class);
        }
        startActivity(i);
    }

    public void onClickCart(View view){
        Intent i = new Intent(MainActivity.this, ShoppingCart.class);
        startActivity(i);
    }
}