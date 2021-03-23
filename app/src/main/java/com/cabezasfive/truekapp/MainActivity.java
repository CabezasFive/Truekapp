package com.cabezasfive.truekapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import javax.crypto.KeyAgreement;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolBar();

        }

        private void setUpToolBar(){
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            showHomeUpIcon();
        }

        private void showHomeUpIcon(){
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        // Metodo del boton  A Listado
        public void aListado(View view){
            Intent intent = new Intent(this, ListadoPublicaciones.class);
            startActivity(intent);
        }
}