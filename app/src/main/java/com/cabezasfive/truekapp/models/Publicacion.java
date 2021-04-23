package com.cabezasfive.truekapp.models;


import java.io.Serializable;
import java.util.Date;

public class Publicacion implements Serializable {

    private String uid;
    private String titulo;
    private String descripcion;
    private String imagen01;
    private String titulo_upper;
/*
    private String f_Creacion;
    private String f_Modificado;
    private Boolean activo;
    private Integer visitas;

    private String idUser;
*/

    // Constructores
    public Publicacion() {
    }

    public Publicacion(String descripcion,String uid, String titulo ) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.uid = uid;
    }

    public Publicacion(String uid, String titulo, String descripcion, String imagen01) {
        this.uid = uid;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen01 = imagen01;
    }

    /*
        public Publicacion (String uid, String titulo, String descripcion, String imagen01, String idUser ){
            this.uid = uid;
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.activo = true;
            this.imagen01 = imagen01;
            this.idUser = idUser;
        }


        // Getters and Setters

        public String getF_Creacion() {
            return f_Creacion;
        }

        public void setF_Creacion(String f_Creacion) {
            this.f_Creacion = f_Creacion;
        }

        public String getF_Modificado() {
            return f_Modificado;
        }

        public void setF_Modificado(String f_Modificado) {
            this.f_Modificado = f_Modificado;
        }

        public Boolean getActivo() {
            return activo;
        }

        public void setActivo(Boolean activo) {
            this.activo = activo;
        }
    */

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

    public String getTitulo_upper() {
        return titulo_upper;
    }

    public void setTitulo_upper(String titulo_upper) {
        this.titulo_upper = titulo_upper;
    }

    /*
    public Integer getVisitas() {
        return visitas;
    }

    public void setVisitas(Integer visitas) {
        this.visitas = visitas;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
*/
}
