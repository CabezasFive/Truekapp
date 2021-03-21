package com.cabezasfive.truekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class NuevaPublicacionActivity extends AppCompatActivity {
    public static final String EXTRA_MSG = "com.cabezasfive.truekapp.MSG_GUARDAR";

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_publicacion);

        editText = findViewById(R.id.editTextIngresarTitulo);

        final Button btnAgregar = findViewById(R.id.btnGuardarPublicacion);
        btnAgregar.setOnClickListener(view ->{
            Intent respuesta = new Intent();
            if(TextUtils.isEmpty(editText.getText())){
                setResult(RESULT_CANCELED, respuesta);
            }else{
                String publicacion = editText.getText().toString();
                respuesta.putExtra(EXTRA_MSG, publicacion);
                setResult(RESULT_OK, respuesta);
            }
            finish();
        });
    }
}