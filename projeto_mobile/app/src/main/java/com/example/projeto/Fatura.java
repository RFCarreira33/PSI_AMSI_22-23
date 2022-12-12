package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapters.LinhaListAdapter;
import Listeners.LinhasListener;
import Models.LinhaFatura;
import Models.Singleton;

public class Fatura extends AppCompatActivity implements LinhasListener {

    ListView lvLinhas;
    TextView tvData, tvPrecoTotal, tvIvaTotal, tvMorada, tvNif, tvTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatura);
        int idFatura = getIntent().getIntExtra("idFatura", 0);
        setTitle("Fatura " + idFatura);
        lvLinhas = findViewById(R.id.lvLinhas);
        tvData = findViewById(R.id.tvData);
        tvPrecoTotal = findViewById(R.id.tvTotal);
        tvIvaTotal = findViewById(R.id.tvTotalIva);
        tvMorada = findViewById(R.id.tvMorada);
        tvNif = findViewById(R.id.tvNif);
        tvTelefone = findViewById(R.id.tvTelefone);
        Singleton.getInstance(this).setLinhasListener(this);
        Singleton.getInstance(this).getLinhasFaturaAPI(this, idFatura);
    }


    @Override
    public void onRefreshLinhasFatura(ArrayList<LinhaFatura> linhas) {
        if (linhas != null) {
            lvLinhas.setAdapter(new LinhaListAdapter(this, linhas));
        }
    }
}