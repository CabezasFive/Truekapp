package com.cabezasfive.truekapp.ui.verPublicacion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;

public class VerPublicacionViewModel extends AndroidViewModel {

    private PublicacionRepository publicacionRepository;

    public VerPublicacionViewModel(@NonNull Application application) {
        super(application);

        publicacionRepository = new PublicacionRepository(application);
    }

    public Publicacion getPublicacionById(String uid){
        return publicacionRepository.getPublicacionById(uid);
    }
}
