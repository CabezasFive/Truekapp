package com.cabezasfive.truekapp;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cabezasfive.truekapp.database.AppDatabase;
import com.cabezasfive.truekapp.entities.Publicacion;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PublicacionTezt {
    private PublicacionDao publicacionDao;
    private AppDatabase appDatabase;

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        publicacionDao = appDatabase.publicacionDao();
    }

    @After
    public void closeDB() throws IOException{
        appDatabase.close();
    }

    @Test
    public void findByTituloTest() throws Exception{
        Publicacion publicacion = new Publicacion();

        publicacion.setId(1);
        publicacion.setTitulo("Pantalon barato");

        publicacionDao.insert(publicacion);

        Publicacion buscada = publicacionDao.findByTitulo("Pantalon barato");

        assertTrue("No encontro la publicacion", publicacion.getId() == buscada.getId());
    }
}
