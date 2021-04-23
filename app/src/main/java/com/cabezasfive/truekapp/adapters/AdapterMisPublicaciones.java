package com.cabezasfive.truekapp.adapters;

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

public class AdapterMisPublicaciones extends BaseAdapter {

    private Context context;
    private ArrayList<Publicacion> publicaciones;

    public AdapterMisPublicaciones(Context context, ArrayList<Publicacion> publicaciones) {
        this.context = context;
        this.publicaciones = publicaciones;
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
            view = View.inflate(context, R.layout.mis_publicaciones_list_item, null);
        }

        Publicacion publicacion = publicaciones.get(i);

        ImageView imagenV = view.findViewById(R.id.ivMisPublicacionesCard);
        TextView tvTitulo = view.findViewById(R.id.tituloMisPublicacionesCard);
        TextView tvDescr = view.findViewById(R.id.descripcionMisPublicaciones);

        tvTitulo.setText(publicacion.getTitulo());
        tvDescr.setText(publicacion.getDescripcion());
        if(publicaciones.get(i).getImagen01() != null){
            String url = publicaciones.get(i).getImagen01();
            Picasso.get()
                    .load(url)
                    .into(imagenV);
        }


        return view;
    }
}
