package com.cabezasfive.truekapp.ui.registroUsuario;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.cabezasfive.truekapp.repositories.UserAccountRepository;
import com.cabezasfive.truekapp.models.Usuario;
import com.google.firebase.auth.FirebaseUser;

public class RegistroViewModel extends AndroidViewModel {

    private UserAccountRepository userAccountRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

    public RegistroViewModel(@NonNull Application application) {
        super(application);

        userAccountRepository = new UserAccountRepository(application);

        userMutableLiveData = userAccountRepository.getUserMutableLiveData();
    }

    public void registrar(String email, String password, Usuario usuario){
        userAccountRepository.registro(email, password, usuario);
    }


    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
