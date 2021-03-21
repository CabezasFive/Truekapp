package com.cabezasfive.truekapp.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cabezasfive.truekapp.daos.PublicacionDao;
import com.cabezasfive.truekapp.database.AppDatabase;
import com.cabezasfive.truekapp.entities.Publicacion;

import java.util.List;

public class PublicacionRepository {

    private PublicacionDao publicacionDao;

    private LiveData<List<Publicacion>> publicaciones;

    public PublicacionRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        publicacionDao = db.publicacionDao();
        publicaciones = publicacionDao.getAll();
    }

    public LiveData<List<Publicacion>> getPublicaciones(){
        return publicaciones;
    }

    public void insert( Publicacion publicacion){
        AppDatabase.databaseWriteExecutor.execute(()->{
            publicacionDao.insert(publicacion);
        });
    }
}
