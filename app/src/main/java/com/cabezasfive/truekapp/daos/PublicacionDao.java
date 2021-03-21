package com.cabezasfive.truekapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cabezasfive.truekapp.entities.Publicacion;

import java.util.List;

@Dao
public interface PublicacionDao {

    @Query("SELECT * FROM  publicacion")
    LiveData<List<Publicacion>> getAll();

    @Insert
    void insert( Publicacion publicacion);

    @Update
    void update(Publicacion publicacion);

    @Delete
    void delete(Publicacion publicacion);

    @Query("SELECT * FROM publicacion WHERE titulo like :titulo")
    Publicacion findByTitulo(String titulo);

    @Query("SELECT * FROM publicacion WHERE id = :id")
    Publicacion findById(int id);
}
