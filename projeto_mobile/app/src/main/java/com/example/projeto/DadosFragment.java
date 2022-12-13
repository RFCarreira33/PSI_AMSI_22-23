package com.example.projeto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Listeners.DadosListener;
import Models.Dados;
import Models.Singleton;
import Utils.Public;

public class DadosFragment extends Fragment implements View.OnClickListener,DadosListener{

    EditText tvNome,tvTelefone,tvNif,tvMorada,tvCodpostal;
    Button btnSaveDados;

    public DadosFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_dados, container, false);

        btnSaveDados = (Button) view.findViewById(R.id.btnSave);
        btnSaveDados.setOnClickListener(this);

        tvNome = view.findViewById(R.id.tvNome);
        tvTelefone = view.findViewById(R.id.tvTelefone);
        tvNif = view.findViewById(R.id.tvNif);
        tvMorada = view.findViewById(R.id.tvMorada);
        tvCodpostal = view.findViewById(R.id.tvCodPostal);

        Singleton.getInstance(getContext()).setDadosListener(this);
        Singleton.getInstance(getContext()).getDadosAPI(getContext());

        return view;
    }

    @Override
    public void onRefreshDados(Models.Dados dado) {
        String nome = dado.getNome();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Public.SHARED_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Public.USER_NOME, nome);
        editor.apply();
        tvNome.setText(nome);
        tvTelefone.setText(dado.getTelefone());
        tvNif.setText(dado.getNif());
        tvMorada.setText(dado.getMorada());
        tvCodpostal.setText(dado.getCodPostal());
    }


    @Override
    public void onClick(View view)
    {
        Dados dadosAux = null;
        String nome = tvNome.getText().toString();
        String telefone = tvTelefone.getText().toString();
        String nif = tvNif.getText().toString();
        String morada = tvMorada.getText().toString();
        String codPostal = tvCodpostal.getText().toString();

        dadosAux = new Dados(nome,telefone,nif,morada, codPostal);

        Singleton.getInstance(getContext()).updateDadosAPI(getContext(),dadosAux);
    }
}
