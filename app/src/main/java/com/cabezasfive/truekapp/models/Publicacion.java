package com.cabezasfive.truekapp.models;


import java.util.Date;

public class Publicacion {

    private String uid;
    private String titulo;
    private String descripcion;
    private Date f_Creacion;
    private Date f_Modificado;
    private Boolean activo;
    private Integer visitas;
    private String imagen01;

    // Constructores
    public Publicacion() {
    }

    public Publicacion (String uid, String titulo, String descripcion, String imagen01 ){
        this.uid = uid;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.activo = true;
        this.imagen01 = imagen01;
    }


    // Getters and Setters

    public Date getF_Creacion() {
        return f_Creacion;
    }

    public void setF_Creacion(Date f_Creacion) {
        this.f_Creacion = f_Creacion;
    }

    public Date getF_Modificado() {
        return f_Modificado;
    }

    public void setF_Modificado(Date f_Modificado) {
        this.f_Modificado = f_Modificado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getImagen01() {
        return imagen01;
    }

    public void setImagen01(String imagen01) {
        this.imagen01 = imagen01;
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

    public Integer getVisitas() {
        return visitas;
    }

    public void setVisitas(Integer visitas) {
        this.visitas = visitas;
    }
}
