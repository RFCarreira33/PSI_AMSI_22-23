package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.projeto.R;

import java.util.ArrayList;

import Models.Fatura;

public class FaturaListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Fatura> Faturas;

    public FaturaListAdapter(Context context, ArrayList<Fatura> faturas) {
        this.context = context;
        Faturas = faturas;
    }

    @Override
    public int getCount() {
        return Faturas.size();
    }

    @Override
    public Object getItem(int i) {
        return Faturas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Faturas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return view;
    }

    private class ViewHolderLista{

    }
}
