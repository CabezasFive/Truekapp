package com.cabezasfive.truekapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cabezasfive.truekapp.ListadoPublicaciones;
import com.cabezasfive.truekapp.R;


public class HomeFragment extends Fragment {

    View view;
    Button btnListado;

    public HomeFragment() {
        // Required empty public constructors
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        btnListado = view.findViewById(R.id.btn_Listado);

        // escucha para el boton al listado
        btnListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent para comenzar una nueva activity
                // Debe reemplazarse por ir a un fragment donde se muestre el listado
                Intent intent = new Intent(getActivity(), ListadoPublicaciones.class);
                startActivity(intent);
            }
        });

        return view;
    }
}