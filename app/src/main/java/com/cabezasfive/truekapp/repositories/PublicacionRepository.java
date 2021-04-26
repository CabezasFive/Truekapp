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
    public MutableLiveData<Publicacion> publicacionMutableLiveData = new MutableLiveData<Publicacion>();

    private ArrayList<Publicacion> publicaciones = new ArrayList<>();
    private Integer cantidad = 0;
    String estado;
    private Integer cantInt;

    private Publicacion find = new Publicacion();

    public PublicacionRepository(Application application) {
        this.application = application;

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }


    /** Trae todas las publicaciones y devuelve un arrayList con ellas   */
    public ArrayList<Publicacion> getAllPublicaciones(){
        estado = "true";
        databaseReference.child("Publicacion").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    // limpia la lista por datos anteriores
                    publicaciones.clear();

                    // recorrer cada uno de los objetos en el nodo
                    for(DataSnapshot ds: snapshot.getChildren()){
                        Publicacion publicacion =  ds.getValue(Publicacion.class);
                        if (estado.equals(publicacion.getActivo())){
                            publicaciones.add(publicacion);
                        }

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



    /** Obtener todas las publicaciones de un usuario pasando su id  */
    public ArrayList<Publicacion> getAllPublicacionesUser(String userId){
        databaseReference.child("users").child(userId).child("publicaciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    publicaciones.clear();

                    for (DataSnapshot ds : snapshot.getChildren()){
                        Publicacion publicacion = ds.getValue(Publicacion.class);
//                        Toast.makeText(application, "Tiene: " + publicacion.getTitulo() + " y esta: " + publicacion.getActivo(), Toast.LENGTH_SHORT).show();
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



    /** Busca una publicacion por el id pasado como argumento   */
    public Publicacion getPublicacionById(String uid){

        Query query = databaseReference.child("Publicacion");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Publicacion publicacion = ds.getValue(Publicacion.class);
                        if (publicacion.getUid() == uid){
                            find = publicacion;
                            addPublicacionMutable(publicacion);
                        }
                    }
                }else {
                    Toast.makeText(application, "No encontro el id de publicacion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return find;
    }


    /** Se cambia el estado de la publicacion */
    /** Se modifican las dos tablas Publicaciones y Publicaciones dentro del usuario **/
    public void cambiarEstado(String id, String userId, String estado){
        databaseReference.child("Publicacion").child(id).child("activo").setValue(estado);
        databaseReference.child("users").child(userId).child("publicaciones").child(id).child("activo").setValue(estado);
    }


    /** Elimina una publicacion pasandole su id e id de usuario */
    public void deletePublicacion(String id, String userId){
        databaseReference.child("Publicacion").child(id).setValue(null);
        databaseReference.child("users").child(userId).child("publicaciones").child(id).setValue(null);

        // Borrar la imagen de la publicacion del Storage
    }


    /** Busca todas las publicaciones que comienzan con el string pasado   */
    /** Convierte a mayusculas el string para buscar dentro de las publicaciones el nodo titulo_upper donde se guarda el titulo completo en mayusculas   */
    public ArrayList<Publicacion> searchPublicacion(String search){
        estado = "true";
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
                        if (estado.equals(publicacion.getActivo())){
                            publicaciones.add(publicacion);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return publicaciones;
    }


    /** Obtener la cantidad de intercambios pendientes de una publicacion */
    public Integer cantidadIntercambios(String pubId){
        cantInt = 0;

        Query query = databaseReference.child("Publicacion").child(pubId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Publicacion publicacion = snapshot.getValue(Publicacion.class);
                    cantInt = Integer.parseInt(publicacion.getInt_pendiente());
                }else {
                    Toast.makeText(application, "No encontro el id de publicacion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return cantInt;
    }


    public void addPublicacionMutable(Publicacion pub){
        publicacionMutableLiveData.setValue(pub);
    }

    public MutableLiveData<Publicacion> getPublicacionMutableLiveData() {
        if (publicacionMutableLiveData == null){
            publicacionMutableLiveData = new MutableLiveData<Publicacion>();
        }
        return publicacionMutableLiveData;
    }
}
