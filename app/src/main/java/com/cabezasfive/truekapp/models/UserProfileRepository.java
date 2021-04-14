package com.cabezasfive.truekapp.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class UserProfileRepository {

    private Application application;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private MutableLiveData<Usuario> usuarioMutableLiveData;

    public UserProfileRepository(Application application) {
        this.application = application;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        usuarioMutableLiveData = new MutableLiveData<>();
    }

    public void registrarProfile(Map<String, Object> usuario){

        // obtener el id que se le asigna al usuario en Auth
        String id = mAuth.getCurrentUser().getUid();
        usuario.put("id", id);

        // Creo un nuevo nodo hijo de Users con el nombre del id del usuario
        databaseReference.child("users").child(id).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                }
            }
        });

    }
}
