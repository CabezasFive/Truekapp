package com.cabezasfive.truekapp.ui.publicar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cabezasfive.truekapp.MainActivity;
import com.cabezasfive.truekapp.R;

import com.cabezasfive.truekapp.MainActivity;
import com.cabezasfive.truekapp.models.Publicacion;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class PublicarFragment extends Fragment  {

    private TextInputEditText inputTitulo, inputDescripcion, inputPrecio;
    private Button btn_Agregar_Foto, btn_Agregar_producto;

    // Integracion de Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");
    Bitmap thumb_bitmap = null;

    ImageView Foto;

    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicar, container, false);

        inputTitulo = view.findViewById(R.id.tie_Nombre_Producto);
        inputDescripcion = view.findViewById(R.id.tie_Descripción);
        inputPrecio = view.findViewById(R.id.tie_Precio);

        Foto = view.findViewById(R.id.Imagen_Producto);

        btn_Agregar_Foto = view.findViewById(R.id.btn_Agregar_Foto);
        btn_Agregar_producto = view.findViewById(R.id.btn_Agregar_producto);

        // Inicializar Firebase
        inicializarFirebase();
        mAuth = FirebaseAuth.getInstance();

        btn_Agregar_Foto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(getContext(),PublicarFragment.this);
            }
        });

        return view;
    }

    // Metodo para inicializar Firebase
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    // Limpia datos
    private void limpiarDatos() {
        inputTitulo.setText("");
        inputDescripcion.setText("");
        inputPrecio.setText("");
    }

    //Valida que este ingresado los valores necesarios
    private void validacion() {
        String titulo = inputTitulo.getText().toString();
        String descripcion = inputDescripcion.getText().toString();
        String precio = inputPrecio.getText().toString();

        if(titulo.equals("")){
            Toast.makeText(getActivity(), "Debe ingresar un Titulo", Toast.LENGTH_SHORT).show();
        } else if(descripcion.equals("")){
            Toast.makeText(getActivity(), "Debe ingresar una Descripcion", Toast.LENGTH_SHORT).show();
        } else if(precio.equals("")){
            Toast.makeText(getActivity(), "Debe ingresar una precio estimado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri imagenuri = CropImage.getPickImageResultUri(getContext(),data);

            //Recorta imagen
            CropImage.activity(imagenuri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setRequestedSize(640,480)
                    .setAspectRatio(2,2)
                    .start(getContext(), this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){

                Uri resulturi = result.getUri();
                File url = new File(resulturi.getPath());

                Picasso.with(getContext()).load(url).into(Foto);

                // Comprimiendo
                try{
                    thumb_bitmap = new Compressor(getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(90)
                            .compressToBitmap(url);
                }catch(IOException e){
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
                final byte [] thumb_byte = byteArrayOutputStream.toByteArray();
                //Fin del compressor

                btn_Agregar_producto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // si hay un usuario logueado puede publicar
                        if(mAuth.getCurrentUser() != null){
                            String titulo = inputTitulo.getText().toString();
                            String descripcion = inputDescripcion.getText().toString();
                            String precio = inputPrecio.getText().toString();

                            if (titulo.equals("") || descripcion.equals("") || precio.equals("")){
                                validacion();
                            } else {
                                Toast.makeText(getActivity(), "Creando Publicacion", Toast.LENGTH_SHORT).show();

                                // crear un objeto (instancia de la clase Publicacion)
                                Publicacion p = new Publicacion();

                                String Uid = UUID.randomUUID().toString();

                                p.setUid(Uid);
                                p.setIdUser(mAuth.getCurrentUser().getUid());
                                p.setTitulo(titulo);
                                p.setTitulo_upper(titulo.toUpperCase());
                                p.setDescripcion(descripcion);
                                Date Fecha = new Date(); // Sistema actual La fecha y la hora se asignan a objDate
                                p.setF_Creacion(Fecha.toString());
                                p.setActivo(true);
                                p.setVisitas(0);

                                //Subir imagen en storage

                                StorageReference ref = storageReference.child(Uid);
                                UploadTask uploadTask = ref.putBytes(thumb_byte);

                                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if(!task.isSuccessful()){
                                            throw Objects.requireNonNull(task.getException());
                                        }
                                        return ref.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        Uri downloaduri = task.getResult();
                                        String URLFoto = downloaduri.toString();
                                        p.setImagen01(URLFoto);
                                        databaseReference.child("Publicacion").child(p.getUid()).setValue(p);
                                    }
                                });

                                Toast.makeText(getActivity(), "Publicacion creada correctamente", Toast.LENGTH_SHORT).show();

                                limpiarDatos();
                            }
                        }else{
                            Toast.makeText(getContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();

                            //Falla navegation para login
                            Navigation.findNavController(getView()).navigate(R.id.action_publicarFragment_to_loginFragment);
                        }
                        //Falla navegation para home
                        Navigation.findNavController(getView()).navigate(R.id.action_publicarFragment_to_homeFragment);
                    }
                });

            }
        }

    }
}