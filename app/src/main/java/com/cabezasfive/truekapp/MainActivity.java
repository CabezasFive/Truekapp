package com.cabezasfive.truekapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cabezasfive.truekapp.entities.Publicacion;
import com.cabezasfive.truekapp.models.PublicacionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private PublicacionViewModel publicacionViewModel;
    public static final int NEW_PUBLICACION_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPublicaciones);
        final PublicacionListAdapter adapter = new PublicacionListAdapter(new PublicacionListAdapter.PublicacionDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        publicacionViewModel = new ViewModelProvider(this, new PublicacionFactory(getApplication())).get(PublicacionViewModel.class);

        publicacionViewModel.getPublicaciones().observe(this, publicaciones -> {
            adapter.submitList(publicaciones);
        } );

        FloatingActionButton fab = findViewById(R.id.btnAgregar);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NuevaPublicacionActivity.class);

            startActivityForResult(intent, NEW_PUBLICACION_REQ_CODE);
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_PUBLICACION_REQ_CODE && resultCode == RESULT_OK){
            Publicacion publicacion = new Publicacion();
            publicacion.setTitulo(data.getStringExtra(NuevaPublicacionActivity.EXTRA_MSG));
            publicacionViewModel.insert(publicacion);
        }else {
            Toast.makeText(getApplicationContext(), "No se pudo guardar", Toast.LENGTH_LONG).show();
        }
    }
}