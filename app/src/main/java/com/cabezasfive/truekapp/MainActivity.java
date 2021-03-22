package com.cabezasfive.truekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // Metodo para el boton a listado
    public void listadoPublicaciones(View view){
        Intent intent = new Intent(this, ListadoPublicaciones.class);
        startActivity(intent);
    }


}