package com.cabezasfive.truekapp.entities;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// En indices - Index me aseguro que solo habra una unica publicacion con ese titulo
@Entity (indices = {@Index( value = {"titulo"}, unique = true)})
public class Publicacion {

    // id clave primaria y autogenerada
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;
    private String descripcion;


    //  **********************
    //    Getters y Setters
    //  **********************

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

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

    public Publicacion() {
    }

    public Publicacion(int id, String titulo, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }
}
