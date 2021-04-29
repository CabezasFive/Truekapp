package com.cabezasfive.truekapp.models;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String ciudad;
    private String email;
    private String nick;
    private String fechaCreado;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String apellido, String direccion, String telefono, String ciudad, String email, String nick, String fechaCreado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.email = email;
        this.nick = nick;
        this.fechaCreado = fechaCreado;
    }

    public Usuario(String nombre, String apellido, String nick, String fechaCreado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nick = nick;
        this.fechaCreado = fechaCreado;
    }


    public Usuario(String nombre, String apellido, String direccion, String telefono, String ciudad, String nick, String fechaCreado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nick = nick;
        this.fechaCreado = fechaCreado;
        this.ciudad = ciudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(String fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
