package com.cabezasfive.truekapp.ui.confirmarEnvioSolicitud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;


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




        return view;
    }
}