package com.cabezasfive.truekapp.repositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.cabezasfive.truekapp.models.Publicacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PublicacionRepository {

    Application application;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private MutableLiveData<Publicacion> publicacionMutableLiveData;

    private ArrayList<Publicacion> publicaciones = new ArrayList<>();
    private Integer cantidad = 0;

    public PublicacionRepository(Application application) {
        this.application = application;

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        publicacionMutableLiveData = new MutableLiveData<>();
    }


    /** Trae todas las publicaciones y devuelve un arrayList con ellas   */
    public ArrayList<Publicacion> getAllPublicaciones(){
        databaseReference.child("Publicacion").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    // limpia la lista por datos anteriores
                    publicaciones.clear();

                    // recorrer cada uno de los objetos en el nodo
                    for(DataSnapshot ds: snapshot.getChildren()){
                        Publicacion publicacion =  ds.getValue(Publicacion.class);
                        publicaciones.add(publicacion);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(application, "Error al buscar listado de publicaciones", Toast.LENGTH_SHORT).show();
            }
        });
        return publicaciones;
    }


    public void getPublicacionById(String uid){

        databaseReference.child("Publicacion").addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Publicacion publicacion = ds.getValue(Publicacion.class);
                        if (publicacion.getUid() == uid){
                            publicacionMutableLiveData.setValue(publicacion);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
    }


    /** Busca todas las publicaciones que comienzan con el string pasado   */
    /** Convierte a mayusculas el string para buscar dentro de las publicaciones el nodo titulo_upper donde se guarda el titulo completo en mayusculas   */
    public ArrayList<Publicacion> searchPublicacion(String search){


        Query firebaseSearchQuery = databaseReference.child("Publicacion").orderByChild("titulo_upper").startAt(search.toUpperCase()).endAt(search.toUpperCase() + "\uf8ff");


        firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    // limpia la lista por datos anteriores
                    publicaciones.clear();

                    // recorrer cada uno de los objetos en el nodo
                    for(DataSnapshot ds: snapshot.getChildren()){
                        Publicacion publicacion =  ds.getValue(Publicacion.class);
                        Toast.makeText(application, "Find: " + publicacion.getTitulo(), Toast.LENGTH_SHORT).show();
                        publicaciones.add(publicacion);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return publicaciones;
    }

    public MutableLiveData<Publicacion> getPublicacionMutableLiveData() {
        return publicacionMutableLiveData;
    }
}
