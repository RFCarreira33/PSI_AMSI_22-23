package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import Models.Produto;
import Models.SingletonProdutos;

public class DetailsProduct extends AppCompatActivity {

    private Produto produto;
    private ImageView imgCapa;
    private TextView tvDetails, tvStock, tvNome, tvPreco;
    private ImageButton btnMore, btnMinus;
    private EditText numQuant;
    private Button btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        imgCapa = findViewById(R.id.imageCapa);
        tvDetails = findViewById(R.id.tvDetalhes);
        tvStock = findViewById(R.id.tvStock);
        tvNome = findViewById(R.id.tvNome);
        tvPreco = findViewById(R.id.tvPreco);
        btnMinus = findViewById(R.id.btnMinus);
        btnMore = findViewById(R.id.btnMore);
        btnCart = findViewById(R.id.btnAddCart);
        numQuant = findViewById(R.id.numQuantity);

        int id = getIntent().getIntExtra("Produto", 0);
        if(id>0){
            produto = SingletonProdutos.getInstance().getProduto(id);
            if(produto != null){
                carregarProduto();
            }
        }
    }

    private void carregarProduto(){
        imgCapa.setImageResource(produto.getCapa());
        tvDetails.setText(produto.getDetalhes());
        tvStock.setText("Em Stock");
        tvNome.setText(produto.getNome());
        tvPreco.setText(produto.getPreco()+" €");

    }
}