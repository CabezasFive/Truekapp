package com.cabezasfive.truekapp.ui.verPublicacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cabezasfive.truekapp.R;

public class VerPublicacion extends Fragment {


    Button btnVolver;

    public VerPublicacion() {
        // Required empty public constructor
    }

    public static VerPublicacion newInstance(String param1, String param2) {
        VerPublicacion fragment = new VerPublicacion();

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
        View view = inflater.inflate(R.layout.fragment_ver_publicacion, container, false);

        btnVolver = view.findViewById(R.id.btnVolverVerPublicacion);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return view;
    }
}