package com.cabezasfive.truekapp.interfaces;

import com.cabezasfive.truekapp.models.Usuario;

public interface IUser {
    public void registro(String email, String password, Usuario usuario);
    public void login(String email, String password);
    public void logOut();
    public void getProfileById(String id);
}
