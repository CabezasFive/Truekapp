package com.cabezasfive.truekapp.adapters;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterListaPublicacionesIntercambio extends BaseAdapter {

    private Context context;
    private ArrayList<Publicacion> publicaciones;
    private Application application;

    public AdapterListaPublicacionesIntercambio(Context context, ArrayList<Publicacion> publicaciones, Application application) {
        this.context = context;
        this.publicaciones = publicaciones;
        this.application = application;
    }

    @Override
    public int getCount() {
        return publicaciones.size();
    }

    @Override
    public Object getItem(int i) {
        return publicaciones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = View.inflate(context, R.layout.item_mis_publicaciones, null);
        }

        Publicacion publicacion = publicaciones.get(i);

        TextView tvTitulo = view.findViewById(R.id.tvTituloMiPublicacionItemSolicitud);
        TextView tvValor = view.findViewById(R.id.tvValorItemSolicitud);
        ImageView ivImagen = view.findViewById(R.id.ivMiPublicacionItemSolicitud);
        int valor;

        tvTitulo.setText(publicacion.getTitulo());
        if (publicacion.getPrecio() != null){
            valor = Integer.parseInt(publicacion.getPrecio());
        }else{
            valor = 0;
        }

        if (valor > 0 ){
            tvValor.setText("Valor estimado: " + valor);
        }else{
            tvValor.setText("No se agrego un valor estimado");
        }

        if(publicaciones.get(i).getImagen01() != null){
            String url = publicaciones.get(i).getImagen01();
            Picasso.get()
                    .load(url)
                    .into(ivImagen);
        }


        return view;
    }
}
