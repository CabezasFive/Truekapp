package com.cabezasfive.truekapp.ui.perfilUsuario;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cabezasfive.truekapp.R;


public class PerfilFragment extends Fragment {

    private CardView cardPerfil, cardPublicaciones, cardCreditos, cardSolicitudes;
    private Button btnVolver;


    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();

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
        View view =  inflater.inflate(R.layout.fragment_perfil, container, false);

        cardPerfil = view.findViewById(R.id.cardMisDatos);
        cardPublicaciones = view.findViewById(R.id.cardMisPublicaciones);
        cardSolicitudes = view.findViewById(R.id.cardMisOfertas);
        cardCreditos = view.findViewById(R.id.cardMisCreditos);

        btnVolver = view.findViewById(R.id.btnVolverDeMiPerfil);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        cardPublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.misPublicacionesFragment);
            }
        });

        cardPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aqui va a editar el perfil de usuario
            }
        });

        cardSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.solicitudesRecibidasFragment);
            }
        });

        cardCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aqui a la gestion de creditos de usuario
            }
        });


        return view;
    }
}