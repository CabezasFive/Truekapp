package com.cabezasfive.truekapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cabezasfive.truekapp.ListadoPublicaciones;
import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.interfaces.IComunicacionFragments;


public class HomeFragment extends Fragment {

    //Referencia a los cardViews que funcionaran como botones
    CardView cardCategorias;
    IComunicacionFragments iComunica;


    View view;
    Activity activity;
    Button btnListado, btnCategorias;


    public HomeFragment() {
        // Required empty public constructors
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        cardCategorias=view.findViewById(R.id.cardCategorias);
        cardCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunica.Categorias();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity= (Activity) context;
            iComunica = (IComunicacionFragments) activity;
        }
    }
}

