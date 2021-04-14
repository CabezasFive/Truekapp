package com.cabezasfive.truekapp.ui.masVistos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.models.repositories.PublicacionRepository;

import java.util.ArrayList;

public class MAsVistosViewModel extends AndroidViewModel {
    private PublicacionRepository publicacionRepository;

    public MAsVistosViewModel(@NonNull Application application) {
        super(application);

        publicacionRepository = new PublicacionRepository(application);

    }

    public ArrayList<Publicacion> getAllPublicaciones(){
        return publicacionRepository.getAllPublicaciones();
    }

}
