package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projeto.R;

import java.util.ArrayList;

import Models.Carrinho;
import Models.Produto;

public class CartListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Carrinho> Carrinhos;

    public CartListAdapter(Context context, ArrayList<Carrinho> carrinhos) {
        this.context = context;
        Carrinhos = carrinhos;
    }

    @Override
    public int getCount() {
        return Carrinhos.size();
    }

    @Override
    public Object getItem(int i) {
        return Carrinhos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Carrinhos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(inflater == null)
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = inflater.inflate(R.layout.item_cart, null);

        CartListAdapter.ViewHolderLista viewHolderLista = (CartListAdapter.ViewHolderLista) view.getTag();

        if(viewHolderLista == null){
            viewHolderLista = new CartListAdapter.ViewHolderLista(view);
            view.setTag(viewHolderLista);
        }

        viewHolderLista.update(Carrinhos.get(i));

        return view;
    }

    private class ViewHolderLista{

        private ImageView imgCapa;
        private TextView tvNome,  tvStock, tvPreco;

        public ViewHolderLista(View view) {
            imgCapa = view.findViewById(R.id.imageCapa);
            tvNome = view.findViewById(R.id.tvNome);
            tvStock = view.findViewById(R.id.tvStock);
            tvPreco = view.findViewById(R.id.tvPreco);


        }

        public void update(Carrinho carrinho){
            imgCapa.setImageResource(carrinho.getCapa());
            tvNome.setText(carrinho.getNome());
            tvStock.setText("Em Stock");
            tvPreco.setText(carrinho.getPreco()+" €");
        }
    }

}

