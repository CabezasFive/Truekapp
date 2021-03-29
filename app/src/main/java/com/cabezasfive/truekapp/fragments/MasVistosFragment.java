package com.cabezasfive.truekapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.cabezasfive.truekapp.MainActivity;
import com.cabezasfive.truekapp.PublicacionFactory;
import com.cabezasfive.truekapp.PublicacionListAdapter;
import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.adapters.AdapterListarPublicaciones;
import com.cabezasfive.truekapp.entities.Publicacion;
import com.cabezasfive.truekapp.models.PublicacionViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MasVistosFragment extends Fragment {

    // Integracion de Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    // Referencia al adaptador
    private AdapterListarPublicaciones adapter;
    // Referencia al recyclerView
    private RecyclerView rvPublicaciones;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Publicacion> publicaciones = new ArrayList<>();




/**
    // Lista con referencia al modelo publicacion
    private List<Publicacion> publicacionList = new ArrayList<Publicacion>();
    ArrayAdapter<Publicacion> publicacionArrayAdapter;




    PublicacionViewModel publicacionViewModel;

    public MasVistosFragment() {
        // Required empty public constructor
    }

**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mas_vistos, container, false);

        // Inicializar Firebase
        inicializarFirebase();

        rvPublicaciones = view.findViewById(R.id.rv_MasVistos);
        layoutManager = new LinearLayoutManager(getActivity());
        rvPublicaciones.setLayoutManager(layoutManager);

        obtenerPublicacionesFirebase();



        return view;
    }

    private void obtenerPublicacionesFirebase() {
        databaseReference.child("Publicacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Valido que el nodo "Publicaciones" existe en la bd
                if(snapshot.exists()){

                    // recorrer cada uno de los objetos en el nodo
                    for(DataSnapshot ds: snapshot.getChildren()){

                        Toast.makeText(getActivity(), "una publi mas", Toast.LENGTH_SHORT).show();

                        Publicacion p = snapshot.getValue(Publicacion.class);
                        publicaciones.add(p);
                    }

                    View view;
                    adapter = new AdapterListarPublicaciones(publicaciones, R.layout.publicacion_item);
                    rvPublicaciones.setAdapter(adapter);
                }else{
                    Toast.makeText(getActivity(), "no encontro nada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    // Metodo para inicializar Firebase
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}