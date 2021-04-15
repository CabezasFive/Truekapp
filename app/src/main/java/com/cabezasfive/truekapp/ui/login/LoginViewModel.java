package com.cabezasfive.truekapp.ui.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.cabezasfive.truekapp.models.repositories.UserAccountRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {


    private UserAccountRepository userAccountRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

    public LoginViewModel(Application application) {
        super(application);

        userAccountRepository = new UserAccountRepository(application);

        userMutableLiveData = userAccountRepository.getUserMutableLiveData();
    }

    public void login(String email, String password){
        userAccountRepository.login(email, password);
    }


    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
