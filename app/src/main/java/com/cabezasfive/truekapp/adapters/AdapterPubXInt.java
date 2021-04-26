package com.cabezasfive.truekapp.adapters;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPubXInt extends BaseAdapter {

    private Context context;
    private ArrayList<Publicacion> publicaciones;
    private Application application;

    public AdapterPubXInt(Context context, ArrayList<Publicacion> publicaciones, Application application) {
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
            view = View.inflate(context, R.layout.item_pub_int, null);
        }

        Publicacion publicacion = publicaciones.get(i);

        TextView tvTitulo = view.findViewById(R.id.tituloPubIntCard);
        Button btnVerPub = view.findViewById(R.id.btnVerPubInt);
        ImageView ivPub = view.findViewById(R.id.ivPubIntCard);

        tvTitulo.setText(publicacion.getTitulo());
        if(publicacion.getImagen01() != null){
            String url = publicacion.getImagen01();
            Picasso.get()
                    .load(url)
                    .into(ivPub);
        }

        return view;
    }
}
