package com.cabezasfive.truekapp.repositories;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.cabezasfive.truekapp.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccountRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Usuario> usuarioProfileLiveData;

    // Indica si se esta logueado o no
    private MutableLiveData<Boolean> loggedOutLiveData;

    public UserAccountRepository(Application application){
        this.application = application;

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        userMutableLiveData = new MutableLiveData<>();
        loggedOutLiveData = new MutableLiveData<>();
    }


    public void registro(String email, String password, Usuario usuario){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(application), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                            String id = firebaseAuth.getCurrentUser().getUid();
                            usuario.setId(id);

                            databaseReference.child("users").child(id).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        // Se guarda en Nicks el nick para facilitar la busqueda
                                        databaseReference.child("nicks").child(usuario.getNick()).setValue(usuario.getNick());
                                        Toast.makeText(application, "Perfil creado con exito ", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(application, "Error registrando Perfil: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }else{
                            Toast.makeText(application, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void login(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(application), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else{
                            Toast.makeText(application, "Error al iniciar sesion" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logOut(){
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }


    public void getProfileById(String id){

        databaseReference.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists()){
                    // recorrer cada uno de los objetos en el nodo
                    for(DataSnapshot ds: snapshot.getChildren()){
                       Usuario user = ds.getValue(Usuario.class);
                       if (user.getId() == id){
                           usuarioProfileLiveData.setValue(user);
                       }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<Usuario> getUsuarioProfileLiveData() {
        return usuarioProfileLiveData;
    }
}
