package com.cabezasfive.truekapp.ui.solicitudIntercambio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;

import java.util.ArrayList;

public class SolicitudViewModel extends AndroidViewModel {

    private PublicacionRepository publicacionRepository;

    public SolicitudViewModel(@NonNull Application application) {
        super(application);
        publicacionRepository = new PublicacionRepository(application);
    }

    public ArrayList<Publicacion> getAllPublicacionesUser(String userId){
        return publicacionRepository.getAllPublicacionesUser(userId);
    }
}
