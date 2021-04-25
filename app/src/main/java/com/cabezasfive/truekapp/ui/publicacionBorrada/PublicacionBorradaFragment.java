package com.cabezasfive.truekapp.ui.publicacionBorrada;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cabezasfive.truekapp.R;


public class PublicacionBorradaFragment extends Fragment {


    private Button btnVolver;

    public static PublicacionBorradaFragment newInstance(String param1, String param2) {
        PublicacionBorradaFragment fragment = new PublicacionBorradaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicacion_borrada, container, false);

        btnVolver = view.findViewById(R.id.btnVolverEliminada);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.misOfertasFragment);
            }
        });

        return view;
    }
}