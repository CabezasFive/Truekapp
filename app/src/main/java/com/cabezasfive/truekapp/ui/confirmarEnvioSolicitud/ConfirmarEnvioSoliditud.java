package com.cabezasfive.truekapp.ui.confirmarEnvioSolicitud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.squareup.picasso.Picasso;


public class ConfirmarEnvioSoliditud extends Fragment {



    private Publicacion pubSeleccionada, pubIntercambio;

    public ConfirmarEnvioSoliditud() {
        // Required empty public constructor
    }


    public static ConfirmarEnvioSoliditud newInstance(String param1, String param2) {
        ConfirmarEnvioSoliditud fragment = new ConfirmarEnvioSoliditud();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmar_envio_soliditud, container, false);

        if (getArguments() != null) {
            pubSeleccionada = (Publicacion) getArguments().getSerializable("miPub");
            pubIntercambio = (Publicacion) getArguments().getSerializable("xPub");
        }

        ImageView ivMiPub, ivXPub;
        TextView tvMiPub, tvXPub;
        Button btnVolver, btnConfirmar;

        /** Declaracion de las Vistas */
        ivMiPub = view.findViewById(R.id.ivMiPubIntercambio);
        ivXPub = view.findViewById(R.id.ivPubXIntercambio);
        tvMiPub = view.findViewById(R.id.tvMiPubIntercambio);
        tvXPub = view.findViewById(R.id.tvPubXIntercambio);

        /** Se asigna los valores a las vistas */
        tvMiPub.setText(pubSeleccionada.getTitulo());
        tvXPub.setText(pubIntercambio.getTitulo());

        if (pubSeleccionada.getImagen01() != null){
            String url = pubSeleccionada.getImagen01();
            Picasso.get().load(url).into(ivMiPub);
        }else {
            ivMiPub.setImageResource(R.drawable.sin_imagen);
        }

        if (pubIntercambio.getImagen01() != null){
            String url = pubIntercambio.getImagen01();
            Picasso.get().load(url).into(ivXPub);
        }else {
            ivXPub.setImageResource(R.drawable.sin_imagen);
        }


        /** Boton Volver - Regresa a la seleccion de publicacion para intercambio */
        btnVolver = view.findViewById(R.id.btnVolverSolicitud);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });



        return view;
    }
}