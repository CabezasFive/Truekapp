package com.cabezasfive.truekapp.ui.solicitudesRecibidas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;

import java.util.ArrayList;

public class SolicitudesRecibidasViewModel extends AndroidViewModel {
    private PublicacionRepository publicacionRepository;

    public SolicitudesRecibidasViewModel(@NonNull Application application) {
        super(application);
        publicacionRepository = new PublicacionRepository(application);
    }

    public ArrayList<Publicacion> getAllPublicacionesUser(String userId){
        return publicacionRepository.getAllPublicacionesUser(userId);
    }

    public Integer cantidadIntercambios(String id){
        return publicacionRepository.cantidadIntercambios(id);
    }
}

