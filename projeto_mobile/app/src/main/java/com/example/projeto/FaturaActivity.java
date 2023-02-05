package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import Adapters.LinhaListAdapter;
import Listeners.LinhasListener;
import Models.Fatura;
import Models.LinhaFatura;
import Models.Singleton;
import Utils.JsonParser;
import Utils.Public;

public class FaturaActivity extends AppCompatActivity implements LinhasListener {

    Button btnPDF;
    ListView lvLinhas;
    TextView tvDataFatura, tvPrecoTotal, tvIvaTotal, tvMorada, tvNif, tvDesconto, tvSubtotal;
    int idFatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatura);
        idFatura = getIntent().getIntExtra("Fatura", 0);
        setTitle("Fatura Nº" + idFatura);
        lvLinhas = findViewById(R.id.lvLinhas);
        tvDesconto = findViewById(R.id.tvTotalDesconto);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDataFatura = findViewById(R.id.tvDataFatura);
        tvPrecoTotal = findViewById(R.id.tvTotal);
        tvIvaTotal = findViewById(R.id.tvTotalIva);
        tvMorada = findViewById(R.id.tvMorada);
        btnPDF = findViewById(R.id.btnPDF);
        tvNif = findViewById(R.id.tvNif);
        if(JsonParser.isConnected(this)) {
            btnPDF.setVisibility(View.VISIBLE);
        }
        else {
            btnPDF.setVisibility(View.GONE);
        }
        Singleton.getInstance(this).setLinhasListener(this);
        Singleton.getInstance(this).getFaturaDB(this, idFatura);

    }


    @Override
    public void onRefreshLinhasFatura(ArrayList<LinhaFatura> linhas, Fatura fatura) {
        if (linhas != null) {
            tvDesconto.setText(String.format("Desconto: %.2f€", fatura.getValorDesconto()));
            tvSubtotal.setText(String.format("Subtotal: %.2f€", fatura.getSubtotal()));
            tvDataFatura.setText(String.format("Compra efetuada a %s", fatura.getData()));
            tvPrecoTotal.setText(String.format("Total: %.2f€", fatura.getValorTotal()));
            tvIvaTotal.setText(String.format("Iva: %.2f€", fatura.getValorIva()));
            tvMorada.setText(fatura.getMorada());
            tvNif.setText(String.format("NIF: %s", fatura.getNif()));
            lvLinhas.setAdapter(new LinhaListAdapter(this, linhas));
        }
    }

    //onclick download pdf
    public void downloadPDF(View view) {
        if (!JsonParser.isConnected(this)) {
            Toast.makeText(this, "Sem ligação à internet", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sharedPreferences = getSharedPreferences(Public.SHARED_FILE, MODE_PRIVATE);
        String base64 = sharedPreferences.getString(Public.PDF, "");
        byte[] pdfAsBytes = Base64.decode(base64, Base64.DEFAULT);
        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "fatura"+idFatura+".pdf");
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(pdfFile);
            outputStream.write(pdfAsBytes);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "PDF descarregado com sucesso", Toast.LENGTH_SHORT).show();
    }

}