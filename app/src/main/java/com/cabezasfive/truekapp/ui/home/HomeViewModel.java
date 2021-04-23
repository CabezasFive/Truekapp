package com.cabezasfive.truekapp.ui.home;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;
import com.cabezasfive.truekapp.repositories.UserAccountRepository;

import java.util.ArrayList;


public class HomeViewModel extends AndroidViewModel {

    UserAccountRepository userAccountRepository;
    PublicacionRepository publicacionRepository;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        userAccountRepository = new UserAccountRepository(application);
        publicacionRepository = new PublicacionRepository(application);
    }

    public boolean isLog(){
        return userAccountRepository.isUserLoged();
    }


}
