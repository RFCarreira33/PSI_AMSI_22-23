package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import Models.Singleton;
import Utils.Public;

public class Login extends AppCompatActivity {
    private EditText tbUsername, tbPassword;
    private static RequestQueue volleyQueue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tbUsername = findViewById(R.id.tbUsername);
        tbPassword = findViewById(R.id.tbPass);
        volleyQueue = Volley.newRequestQueue(this);

    }

    public boolean isUsernameValid(String username){
        if(username.length() <= 2){ return false;}
        return true;
    }

    public void onClickLogin(View view){
        String username = tbUsername.getText().toString();
        if(!isUsernameValid(username)){
            Toast.makeText(this, "Username invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = tbPassword.getText().toString();
        String credentials = username + ":" + password;
        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        Singleton.getInstance(this).loginUserAPI(this,base64EncodedCredentials);
        Toast.makeText(this, "Bem Vindo", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onClickRegister(View view){
        Intent i = new Intent(Login.this, UserRegister.class);
        startActivity(i);
        finish();
    }

}