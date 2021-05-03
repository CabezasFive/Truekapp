package com.cabezasfive.truekapp.ui.publicar;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class PublicarFragment03 extends Fragment {

    private Button btn_Agregar_Foto, btn_Agregar_producto;
    private Publicacion mPublicacion = new Publicacion();
    private Publicacion p = new Publicacion();


    // Integracion de Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");

    private Bitmap thumb_bitmap = null;

    private ImageView Foto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicar03, container, false);

        Bundle getUsuario = getArguments();
        if (getUsuario != null) {
            mPublicacion = (Publicacion) getUsuario.getSerializable("pub");
        }


        Foto = view.findViewById(R.id.Imagen_Producto);
        btn_Agregar_Foto = view.findViewById(R.id.btn_Agregar_Foto);
        btn_Agregar_producto = view.findViewById(R.id.btn_AgregarPublicacion);

        inicializarFirebase();
        mAuth = FirebaseAuth.getInstance();

        btn_Agregar_Foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(getContext(), PublicarFragment03.this);
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

    //Valida que este ingresado los valores necesarios
    private void validacion() {
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imagenuri = CropImage.getPickImageResultUri(getContext(), data);

            //Recorta imagen
            CropImage.activity(imagenuri)
                    .start(getContext(), this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resulturi = result.getUri();
                File url = new File(resulturi.getPath());

                Picasso.get()
                        .load(url)
                        .into(Foto);

                // Comprimiendo
                try {
                    thumb_bitmap = new Compressor(getContext())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(90)
                            .compressToBitmap(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                final byte[] thumb_byte = byteArrayOutputStream.toByteArray();
                //Fin del compressor

                btn_Agregar_producto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // si hay un usuario logueado puede publicar
                        if (mAuth.getCurrentUser() != null) {

                            // crear un objeto (instancia de la clase Publicacion)
                            p = mPublicacion;

                            String Uid = UUID.randomUUID().toString();

                            p.setUid(Uid);
                            p.setIdUser(mAuth.getCurrentUser().getUid());
                            p.setTitulo_upper(p.getTitulo().toUpperCase());
                            Date Fecha = new Date(); // Sistema actual La fecha y la hora se asignan a objDate
                            p.setF_Creacion(Fecha.toString());
                            p.setActivo("true");
                            p.setVisitas(0);
                            p.setInt_pendiente("0");

                            //Subir imagen en storage

                            StorageReference ref = storageReference.child(Uid);
                            UploadTask uploadTask = ref.putBytes(thumb_byte);

                            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
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

                                    // Guarda en la bd la publicacion dentro de Publicacion
                                    // y una copia dentro del usuario con el id de la publicacion
                                    databaseReference.child("Publicacion").child(p.getUid()).setValue(p);
                                    databaseReference.child("users").child(p.getIdUser()).child("publicaciones").child(p.getUid()).setValue(p);
                                }
                            });

                            androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Publicación Creada");
                            builder.setMessage("Publicacion creada con exito!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            Navigation.findNavController(view).navigate(R.id.homeFragment);
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();

                        } else {
                            Toast.makeText(getContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(getView()).navigate(R.id.loginFragment);
                        }
                    }
                });

            }
        }
    }
}

