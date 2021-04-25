package com.cabezasfive.truekapp.models;


import java.io.Serializable;
import java.util.Date;

public class Publicacion implements Serializable {


    private String uid;
    private String idUser;
    private String titulo;
    private String titulo_upper;
    private String descripcion;
    private String imagen01;
    private String f_Creacion;
    private String activo;
    private String precio;
    private Integer visitas;



    // Constructores
    public Publicacion() {
    }

    public Publicacion (String uid, String idUser, String titulo, String titulo_upper, String descripcion,
                        String f_Creacion, String imagen01, String activo, String precio, Integer visitas){

        this.uid = uid;
        this.idUser = idUser;
        this.titulo = titulo;
        this.titulo_upper = titulo_upper;
        this.descripcion = descripcion;
        this.f_Creacion = f_Creacion;
        this.imagen01 = imagen01;
        this.activo = activo;
        this.visitas = visitas;
        this.precio = precio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo_upper() {
        return titulo_upper;
    }

    public void setTitulo_upper(String titulo_upper) {
        this.titulo_upper = titulo_upper;
    }




        // Getters and Setters

        public String getF_Creacion() {
            return f_Creacion;
        }
  
        public void setF_Creacion(String f_Creacion) {
            this.f_Creacion = f_Creacion;
        }


        public String getActivo() {
            return activo;
        }

        public void setActivo(String activo) {
            this.activo = activo;
        }

    public String getImagen01() {
        return imagen01;
    }

    public void setImagen01(String imagen01) {
        this.imagen01 = imagen01;
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
