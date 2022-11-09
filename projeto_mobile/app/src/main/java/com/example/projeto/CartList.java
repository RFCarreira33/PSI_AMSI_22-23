package com.example.projeto;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import Adapters.CartListAdapter;
import Adapters.ProductGridAdapter;
import Models.Carrinho;
import Models.SingletonCarrinho;
import Models.SingletonProdutos;


public class CartList extends Fragment {
    private ListView lvCart;
    private ArrayList<Carrinho> carrinhos;

    public CartList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);
        lvCart= view.findViewById(R.id.lvCart);
        carrinhos = SingletonCarrinho.getInstance().getCarrinhos();
        lvCart.setAdapter(new CartListAdapter(getContext(), carrinhos));


        return view;
    }
}