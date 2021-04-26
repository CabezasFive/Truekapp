package com.cabezasfive.truekapp.adapters;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.logging.Handler;

public class AdapterPublicacionesUser  extends BaseAdapter {
    private Context context;
    private ArrayList<Publicacion> publicaciones;
    private Application application;

    PublicacionRepository publicacionRepository;


    public AdapterPublicacionesUser(Context context, ArrayList<Publicacion> publicaciones, Application application) {
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
            view = View.inflate(context, R.layout.list_item_pub_user, null);
        }

        publicacionRepository = new PublicacionRepository(application);

        Publicacion publicacion = publicaciones.get(i);
        ImageView imagenV = view.findViewById(R.id.ivPubUserIntercambio);
        TextView tvTitulo = view.findViewById(R.id.tituloPubIntercambio);
        TextView tvcantSolicitud = view.findViewById(R.id.tvSolicitudes);

        if (Integer.parseInt(publicacion.getInt_pendiente()) <= 0){
            tvcantSolicitud.setText("Esta publicion no tiene solicitudes pendientes");
        }else {
            tvcantSolicitud.setText("Tienes " + publicacion.getInt_pendiente() + "\nsolicitudes pendientes");
            Toast.makeText(context, "Tienes : " + publicacion.getInt_pendiente() + " solicitudes", Toast.LENGTH_SHORT).show();
            tvTitulo.setText(publicacion.getTitulo());
            if(publicaciones.get(i).getImagen01() != null){
                String url = publicaciones.get(i).getImagen01();
                Picasso.get()
                        .load(url)
                        .into(imagenV);
            }
        }





        return view;
    }


}
