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



        // Metodo del boton  A Listado
        public void aListado(View view){
            Intent intent = new Intent(this, ListadoPublicaciones.class);
            startActivity(intent);
        }
}