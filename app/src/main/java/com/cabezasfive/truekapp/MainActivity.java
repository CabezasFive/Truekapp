package com.cabezasfive.truekapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cabezasfive.truekapp.entities.Publicacion;
import com.cabezasfive.truekapp.models.PublicacionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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