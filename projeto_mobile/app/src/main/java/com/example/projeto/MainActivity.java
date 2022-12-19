package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;


import Models.Singleton;
import Utils.JsonParser;
import Utils.Public;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar = null;
    MqttClient client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.logo);
        mosquito();
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

    public void mosquito() {
        SharedPreferences sharedPreferences = null;
        try {
            sharedPreferences = getSharedPreferences(Public.SHARED_FILE, MODE_PRIVATE);
            client = new MqttClient("tcp://" + Public.IP + ":1883", "Cliente", null);
            client.connect();
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    if (topic.equals("filters")) {
                        Singleton.getInstance(MainActivity.this).getAllFiltersAPI(MainActivity.this);
                    }
                    if (topic.equals("promo")) {
                        Intent i = new Intent(MainActivity.this, PromoCodeActivity.class);
                        startActivity(i);
                    }
            }

            @Override
            public void deliveryComplete (IMqttDeliveryToken token){
            }
        });
        client.subscribe("filters", 1);
        if (sharedPreferences.contains(Public.TOKEN)) {
            client.subscribe("promo", 1);
        }
        }catch(Exception e){
        }

    }

}