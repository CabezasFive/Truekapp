package com.cabezasfive.truekapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cabezasfive.truekapp.MainActivity;
import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.entities.Publicacion;


public class PublicarFragment extends Fragment {

    private EditText inputTitulo, inputDescripcion;
    private Button btnAgregar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicar, container, false);

        inputTitulo = view.findViewById(R.id.et_TituloPub);
        inputDescripcion = view.findViewById(R.id.et_DescrPub);
        btnAgregar = view.findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = inputTitulo.getText().toString();
                String descripcion = inputDescripcion.getText().toString();

                Publicacion publicacion = new Publicacion();
                publicacion.setTitulo(titulo);
                publicacion.setDescripcion(descripcion);

                MainActivity.appDatabase.publicacionDao().insert(publicacion);
                Toast.makeText(getActivity(), "Datos Guardados Correctamente", Toast.LENGTH_SHORT).show();

                inputTitulo.setText("");
                inputDescripcion.setText("");
            }
        });

        return view;
    }
}