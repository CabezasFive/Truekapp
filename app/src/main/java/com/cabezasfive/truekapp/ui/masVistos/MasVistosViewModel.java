package com.cabezasfive.truekapp.ui.masVistos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;

import java.util.ArrayList;

public class MasVistosViewModel extends AndroidViewModel {
    private PublicacionRepository publicacionRepository;

    public MasVistosViewModel(@NonNull Application application) {
        super(application);

        publicacionRepository = new PublicacionRepository(application);

    }

    public ArrayList<Publicacion> getAllPublicaciones(){
        return publicacionRepository.getAllPublicaciones();
    }

    public ArrayList<Publicacion> searchPublicacion(String search){
        return publicacionRepository.searchPublicacion(search);
    }

}
