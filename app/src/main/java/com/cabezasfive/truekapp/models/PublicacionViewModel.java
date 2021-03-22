package com.cabezasfive.truekapp.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cabezasfive.truekapp.entities.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;

import java.util.List;

public class PublicacionViewModel extends AndroidViewModel {

    private PublicacionRepository publicacionRepository;
    private final LiveData<List<Publicacion>> publicaciones;

    public PublicacionViewModel(Application application){
        super(application);
        publicacionRepository = new PublicacionRepository(application);
        publicaciones = publicacionRepository.getPublicaciones();
    }

    // Listado de todas las publicaciones
    public LiveData<List<Publicacion>> getPublicaciones(){
        return publicaciones;
    }

    // Insertar una publicacion
    public void insert(Publicacion publicacion){
        publicacionRepository.insert((publicacion));
    }

    // Update de una publicacion
    public void update(Publicacion publicacion){
        publicacionRepository.updatePublicacion(publicacion);
    }

    // Delete de la publicacion
    public void delete(Publicacion publicacion){
        publicacionRepository.deletePublicacion(publicacion);
    }
}
