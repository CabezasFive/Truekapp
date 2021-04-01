package com.cabezasfive.truekapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

// Uso de persistencia de datos en Firebase
// se agrega (android:name=".FirebaseApp") en el manifest
public class FirebaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
