package com.cabezasfive.truekapp.ui.verPublicacion;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;

public class VerPublicacion extends Fragment {


    private Button btnVolver;

    private TextView tvTitulo;


    private Publicacion publicacion;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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


        Bundle getPublicaicon = getArguments();
        if (getPublicaicon != null){
            publicacion = (Publicacion) getPublicaicon.getSerializable("publicacion");
        }

        btnVolver = view.findViewById(R.id.btnVolverVerPublicacion);

        tvTitulo = view.findViewById(R.id.tituloVerPublicacion);


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        tvTitulo.setText(publicacion.getTitulo());


        return view;
    }

}