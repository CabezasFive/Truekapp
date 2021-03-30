package com.cabezasfive.truekapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditPublicacionFragment extends Fragment {


    private String tituloP, descrP, uidP;
    private Button btnEditar, btnBorrar;

    // Integracion de Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public EditPublicacionFragment() {
        // Required empty public constructor
    }


    public static EditPublicacionFragment newInstance(String param1, String param2) {
        EditPublicacionFragment fragment = new EditPublicacionFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tituloP = getArguments().getString("titulo");
        descrP = getArguments().getString("descripcion");
        uidP = getArguments().getString("uid");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_publicacion, container, false);

        EditText titulo = view.findViewById(R.id.et_EditTituloPub);
        EditText descripcion = view.findViewById(R.id.et_EditDescrPub);
        btnEditar = view.findViewById(R.id.btn_EditPub);
        btnBorrar = view.findViewById(R.id.btn_DeletePub);

        inicializarFirebase();


        // Al precionar el boton de editar se guarda en la misma posicion con los datos actualizados

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tituloEd = titulo.getText().toString();
                String descripcionEd = descripcion.getText().toString();

                Publicacion publicacion = new Publicacion();
                publicacion.setTitulo(tituloEd);
                publicacion.setDescripcion(descripcionEd);
                publicacion.setUid(uidP);

                databaseReference.child("Publicacion").child(uidP).setValue(publicacion);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft;
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido,new HomeFragment());
                ft.commit();
            }
        });



        // Al precionar el boton borrar elimina el elemento por su uid

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                databaseReference.child("Publicacion").child(uidP).removeValue();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft;
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido,new HomeFragment());
                ft.commit();
            }
        });

        titulo.setText(tituloP);
        descripcion.setText(descrP);

        return view;
    }


    // Metodo para inicializar Firebase
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}