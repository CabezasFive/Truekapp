package com.cabezasfive.truekapp.ui.verSolicitudesDeIntercambios;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;

import java.util.ArrayList;

public class VerSolicitudesViewModel extends AndroidViewModel {
    private PublicacionRepository publicacionRepository;

    public VerSolicitudesViewModel(@NonNull Application application) {
        super(application);
        publicacionRepository = new PublicacionRepository(application);
    }

    public ArrayList<Publicacion> getPublicacionesInter(String userId, String pubId){
        return publicacionRepository.getPublicacionesInt(userId, pubId);
    }

}
