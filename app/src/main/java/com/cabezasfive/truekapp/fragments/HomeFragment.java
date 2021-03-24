package com.cabezasfive.truekapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.interfaces.IComunicacionFragments;


public class HomeFragment extends Fragment {

    //Referencia a los cardViews que funcionaran como botones
    CardView cardCategorias, cardPublicar, cardDestacados, cardMisOfertas, cardMasVistos, cardAyuda;

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
        cardAyuda=view.findViewById(R.id.cardAyuda);
        cardMasVistos=view.findViewById(R.id.cardMasVistos);
        cardDestacados=view.findViewById(R.id.cardDestacados);
        cardPublicar=view.findViewById(R.id.cardPublicar);
        cardMisOfertas=view.findViewById(R.id.cardMisOfertas);



        // scucha si se da click en alguno de los cardView del menu
        // se comunica con la interface IComunicacionFragment para ejecutar desde el mainActivity y no desde este fragemnt
        cardPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iComunica.A_Publicar();
            }
        });

        cardCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunica.A_Categorias();
            }
        });

        cardDestacados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iComunica.A_Destacados();
            }
        });

        cardMisOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunica.A_MisOfertas();
            }
        });

        cardMasVistos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunica.A_MasVistos();
            }
        });

        cardAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunica.A_Ayuda();
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

