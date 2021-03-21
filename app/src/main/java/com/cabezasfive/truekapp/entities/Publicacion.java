package com.cabezasfive.truekapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Publicacion {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
