package com.cabezasfive.truekapp.ui.misPublicaciones;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;

import java.util.ArrayList;

public class MisPublicacionesViewModel extends AndroidViewModel {

    private PublicacionRepository publicacionRepository;

    public MisPublicacionesViewModel(Application application) {
        super(application);
        publicacionRepository = new PublicacionRepository(application);
    }

    public ArrayList<Publicacion> getAllPublicaciones(){
        return publicacionRepository.getAllPublicaciones();
    }

    public ArrayList<Publicacion> getAllPublicacionesUser(String userId){
        return publicacionRepository.getAllPublicacionesUser(userId);
    }

    public void cambiarEstado(String id, String userId, String estado){
        publicacionRepository.cambiarEstado(id,userId, estado);
    }
}
