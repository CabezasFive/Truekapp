package com.cabezasfive.truekapp.adapters;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;
import com.cabezasfive.truekapp.repositories.UserAccountRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPubXInt extends BaseAdapter {

    private Context context;
    private ArrayList<Publicacion> publicaciones;
    private Application application;
    private Publicacion pubOwner;

    PublicacionRepository publicacionRepository;

    public AdapterPubXInt(Context context, ArrayList<Publicacion> publicaciones, Application application, Publicacion pubOwner) {
        this.context = context;
        this.publicaciones = publicaciones;
        this.application = application;
        this.pubOwner = pubOwner;
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
        publicacionRepository = new PublicacionRepository(application);

        TextView tvTitulo = view.findViewById(R.id.tituloPubIntCard);
        Button btnVerPub = view.findViewById(R.id.btnVerPubInt);
        ImageView ivPub = view.findViewById(R.id.ivPubIntCard);

        ImageButton btnAceptar = view.findViewById(R.id.btnAceptarInt);
        ImageButton btnCancelar = view.findViewById(R.id.btnCancelarInt);

        tvTitulo.setText(publicacion.getTitulo());
        if(publicacion.getImagen01() != null){
            String url = publicacion.getImagen01();
            Picasso.get()
                    .load(url)
                    .into(ivPub);
        }

        /** Ve la publicacion que se ofrece para intercambio */
        btnVerPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("publicacion", publicacion);
                Navigation.findNavController(view).navigate(R.id.verPublicacion, bundle);
            }
        });

        /** Acepta el intercambio de la publicacion ofrecida
         * se debe quitar de la bd todas las ofertas de intercambio para esta pub  */
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicacionRepository.intAceptado(publicacion.getUid(), publicacion.getIdUser());
            }
        });

        /** Cancela la oferta de intercambio - Se quita de la lista de pendientes para int */
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicacionRepository.cancelarSolicitudDeInt(publicacion.getUid(), publicacion.getIdUser(), pubOwner.getUid());
            }
        });


        return view;
    }
}
