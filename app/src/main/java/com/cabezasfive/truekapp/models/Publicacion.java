package com.cabezasfive.truekapp.models;

public class Publicacion {

    private String uid;
    private String titulo;
    private String descripcion;

    public Publicacion() {
    }

    public Publicacion (String uid, String titulo, String descripcion){
        this.uid = uid;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
