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

public class ListadoPublicaciones extends AppCompatActivity {

    private PublicacionViewModel publicacionViewModel;
    public static final int NEW_PUBLICACION_REQ_CODE = 1;
    public static final int UPDATE_PUBLICACION_REQ_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_publicaciones);

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
            Intent intent = new Intent(this, NuevaPublicacionActivity.class);

            startActivityForResult(intent, NEW_PUBLICACION_REQ_CODE);
        });

        // Escucha por el clic en la cruz de borrar publicacion
        adapter.setOnItemClickListener(new PublicacionListAdapter.onItemClickListener() {
            @Override
            public void onItemDelete(Publicacion publicacion) {
                String titulo = publicacion.getTitulo();
                publicacionViewModel.delete(publicacion);
                Toast.makeText(getApplicationContext(), "Publicacion " + titulo + " eliminada", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemClick(Publicacion publicacion) {
                Intent intent = new Intent( ListadoPublicaciones.this, NuevaPublicacionActivity.class );
                intent.putExtra(NuevaPublicacionActivity.EXTRA_MSG_TITULO, publicacion.getTitulo());
                intent.putExtra(NuevaPublicacionActivity.EXTRA_MSG_DESCRIPCION, publicacion.getDescripcion());
                intent.putExtra(NuevaPublicacionActivity.EXTRA_MSG_ID, publicacion.getId());

                // inicia la actividad y envia el codigo diciendo que es para edicion
                startActivityForResult(intent, UPDATE_PUBLICACION_REQ_CODE);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // chequea que sea una nueva publicacion e inserta en la bd
        if(requestCode == NEW_PUBLICACION_REQ_CODE && resultCode == RESULT_OK){
            Publicacion publicacion = new Publicacion();
            publicacion.setTitulo(data.getStringExtra(NuevaPublicacionActivity.EXTRA_MSG_TITULO));
            publicacion.setDescripcion(data.getStringExtra(NuevaPublicacionActivity.EXTRA_MSG_DESCRIPCION));
            publicacionViewModel.insert(publicacion);
        }
        // aqui si es una actualizacion de la publicacion
        else if( requestCode == UPDATE_PUBLICACION_REQ_CODE && resultCode == RESULT_OK){
            int id = data.getIntExtra(NuevaPublicacionActivity.EXTRA_MSG_ID, -1);
            if (id == -1){
                Toast.makeText(getApplicationContext(), "No se pudo actualizar la publicacion", Toast.LENGTH_LONG).show();
            }

            String titulo = data.getStringExtra(NuevaPublicacionActivity.EXTRA_MSG_TITULO);
            String descripcion = data.getStringExtra(NuevaPublicacionActivity.EXTRA_MSG_DESCRIPCION);

            Publicacion publicacion = new Publicacion(id, titulo, descripcion);
            publicacionViewModel.update(publicacion);

        }
        // error si no se pudo guardar
        else {
            Toast.makeText(getApplicationContext(), "No se pudo guardar", Toast.LENGTH_LONG).show();
        }
    }
}