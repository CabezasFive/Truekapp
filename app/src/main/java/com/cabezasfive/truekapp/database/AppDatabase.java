package com.cabezasfive.truekapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cabezasfive.truekapp.daos.PublicacionDao;
import com.cabezasfive.truekapp.entities.Publicacion;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Publicacion.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PublicacionDao publicacionDao();

    private static volatile AppDatabase instance;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static AppDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "truekapp")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
