package com.cabezasfive.truekapp.ui.publicar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;

import com.cabezasfive.truekapp.models.Publicacion;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.UUID;


public class PublicarFragment extends Fragment  {

    private EditText inputTitulo, inputDescripcion;
    private Button btnAgregar;

    // Integracion de Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseAuth mAuth;

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
        btnAgregar = view.findViewById(R.id.btn_AddPub);

        // Inicializar Firebase
        inicializarFirebase();
        mAuth = FirebaseAuth.getInstance();
/*
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // si hay un usuario logueado puede publicar
                if(mAuth.getCurrentUser() != null){
                    String titulo = inputTitulo.getText().toString();
                    String descripcion = inputDescripcion.getText().toString();

                    if (titulo.equals("") || descripcion.equals("")){
                        validacion();
                    }else {
                        // crear un objeto (instancia de la clase Publicacion
                        Publicacion p = new Publicacion();
                        p.setUid(UUID.randomUUID().toString());
                        p.setTitulo(titulo);
                        p.setDescripcion(descripcion);
                        p.setImagen01("https://fotos.perfil.com/2020/06/08/960/0/9-de-junio-dia-mundial-del-pato-donald-968611.jpg");
                        p.setActivo(true);
                        p.setVisitas(0);

                        Date fecha = new Date(); // Sistema actual La fecha y la hora se asignan a objDate
                        p.setF_Creacion(fecha.toString());

                        databaseReference.child("Publicacion").child(p.getUid()).setValue(p);

                        Toast.makeText(getActivity(), "Datos Guardados Correctamente", Toast.LENGTH_SHORT).show();
                        limpiarDatos();
                    }
                }else{
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft;
                    ft = fm.beginTransaction();
                    ft.replace(R.id.contenido,new LoginFragment())
                                .commit();
                }


                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft;
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido,new HomeFragment());
                ft.commit();
            }
        });
*/
        return view;
    }

    // Metodo para inicializar Firebase
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void limpiarDatos() {
        inputTitulo.setText("");
        inputDescripcion.setText("");
    }

    private void validacion() {
        String titulo = inputTitulo.getText().toString();
        String descripcion = inputDescripcion.getText().toString();

        if(titulo.equals("")){
            Toast.makeText(getActivity(), "Debe ingresar un Titulo", Toast.LENGTH_SHORT).show();
        } else if(descripcion.equals("")){
            Toast.makeText(getActivity(), "Debe ingresar una Descripcion", Toast.LENGTH_SHORT).show();
        }
    }
}