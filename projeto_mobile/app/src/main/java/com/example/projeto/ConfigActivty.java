package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Utils.Public;

public class ConfigActivty extends AppCompatActivity {

    private EditText ip, port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_activty);
        ip = findViewById(R.id.configIP);
        port = findViewById(R.id.configPort);
    }

    public void onClick(View view) {
        String ipText = ip.getText().toString();
        String portText = port.getText().toString();
        if (ipText.isEmpty() || portText.isEmpty()) {
            return;
        }
        Public.IP = ipText;
        Public.PORT = portText;
        finish();

    }

    public void onClickCancel(View view) {
        finish();
    }
}