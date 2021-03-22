package com.cabezasfive.truekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class NuevaPublicacionActivity extends AppCompatActivity {
    public static final String EXTRA_MSG_ID = "com.cabezasfive.truekapp.MSG_GUARDAR_ID";
    public static final String EXTRA_MSG_TITULO = "com.cabezasfive.truekapp.MSG_GUARDAR_TITULO";
    public static final String EXTRA_MSG_DESCRIPCION = "com.cabezasfive.truekapp.MSG_GUARDAR_DESCRIPCION";

    private EditText editTextTitulo;
    private EditText getEditTextDescripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_publicacion);

        editTextTitulo = findViewById(R.id.editTextIngresarTitulo);
        getEditTextDescripcion = findViewById(R.id.editTextIngresarDescripcion);

        final Button btnAgregar = findViewById(R.id.btnGuardarPublicacion);
        btnAgregar.setOnClickListener(view ->{
            Intent respuesta = new Intent();
            if(TextUtils.isEmpty(editTextTitulo.getText())){
                setResult(RESULT_CANCELED, respuesta);
            }else{
                String publicacion = editTextTitulo.getText().toString();
                String descripcion = getEditTextDescripcion.getText().toString();
                respuesta.putExtra(EXTRA_MSG_TITULO, publicacion);
                respuesta.putExtra(EXTRA_MSG_DESCRIPCION, descripcion);

                // si esta editando tiene un id y lo envia como EXTRA_MSG
                int id = getIntent().getIntExtra(EXTRA_MSG_ID, -1);
                if(id != -1){
                    respuesta.putExtra(EXTRA_MSG_ID, id);
                }
                setResult(RESULT_OK, respuesta);
            }
            finish();
        });

        // consulto en el intent si trae el ID
        // si trae id se esta editando y pone los datos en los editText
        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_MSG_ID)){
            editTextTitulo.setText((intent.getStringExtra(EXTRA_MSG_TITULO)));
            getEditTextDescripcion.setText((intent.getStringExtra(EXTRA_MSG_DESCRIPCION)));
        }

    }
}