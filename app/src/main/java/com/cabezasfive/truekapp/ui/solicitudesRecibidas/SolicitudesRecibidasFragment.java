package com.cabezasfive.truekapp.ui.solicitudesRecibidas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cabezasfive.truekapp.R;


public class SolicitudesRecibidasFragment extends Fragment {

    private Button btnVolver;

    public static SolicitudesRecibidasFragment newInstance(String param1, String param2) {
        SolicitudesRecibidasFragment fragment = new SolicitudesRecibidasFragment();

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
        View view = inflater.inflate(R.layout.fragment_solicitudes_recibidas, container, false);

        btnVolver = view.findViewById(R.id.btnVolverDeSolicitudesRec);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });



        return view;
    }
}