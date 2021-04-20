package com.cabezasfive.truekapp.ui.home;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cabezasfive.truekapp.repositories.UserAccountRepository;


public class HomeViewModel extends AndroidViewModel {

    UserAccountRepository userAccountRepository;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        userAccountRepository = new UserAccountRepository(application);
    }

    public boolean isLog(){
        return userAccountRepository.isUserLoged();
    }
}
