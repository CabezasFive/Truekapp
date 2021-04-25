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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserAccountRepository{
    private Application application;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private MutableLiveData<FirebaseUser> userMutableLiveData;


    public Usuario usuario = new Usuario();
    boolean resultado;


    public UserAccountRepository(Application application){
        this.application = application;

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        userMutableLiveData = new MutableLiveData<>();
    }


    /** Registro de un nuevo usuario    */
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
                                        databaseReference.child("nicks").child(usuario.getId()).child("nick").setValue(usuario.getNick());
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

    /** Login de usuario en firebaseAuth por su email y password   */
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


     /** Cierra sesion en firebaseAuth */
    public void logOut(){
        firebaseAuth.signOut();
    }

    /** Devuelve un usuario por su ID */
    public Usuario getProfileById(String id){
        databaseReference.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists()){
                    // recorrer cada uno de los objetos en el nodo
                    for(DataSnapshot ds: snapshot.getChildren()){
                       Usuario user = ds.getValue(Usuario.class);
                       if (user.getId() == id){
                           usuario = user;
                       }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return usuario;
    }

    /** Consulta si un usuario esta logueado (Devuelve un booleano) */
    public boolean isUserLoged() {
        if(firebaseAuth.getCurrentUser() != null){
            return true;
        } else {
            return false;
        }
    }

    private String userNick;
    /** Obtiene el nickName de usuario  */
    /** A REVISAR -- NO ESTA FUNCIONANDO EN MAINACTIVITY PARA MOSTRAR EL NICK CUANDO ESTA LOGUEADO */
    public String getUserNickname(){

        if(firebaseAuth.getCurrentUser() != null){
            String id = firebaseAuth.getCurrentUser().getUid();
            databaseReference.child("users").child(id).child("nick").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for (DataSnapshot ds: snapshot.getChildren()){
                            userNick   = ds.getValue().toString();
                            Toast.makeText(application, "Usuario: " + userNick, Toast.LENGTH_SHORT).show();
//                            userNick = nick;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        } else {
            userNick="*----*";
        }
        return userNick;
    }



    public boolean existNick(String nick){

        Query userNick = databaseReference.child("nicks").orderByChild("nick").equalTo(nick).limitToFirst(1);

        userNick.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    resultado = true;
                }
                else {
                    resultado =false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return resultado;
    }



    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

}
