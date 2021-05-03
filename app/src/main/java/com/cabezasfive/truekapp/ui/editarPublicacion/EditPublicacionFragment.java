package com.cabezasfive.truekapp.ui.editarPublicacion;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.models.Usuario;
import com.cabezasfive.truekapp.ui.publicar.PublicarFragment03;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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


public class EditPublicacionFragment extends Fragment {


    private EditText etTitulo, etDescripcion, etValor;
    private ImageView ivFoto;
    private Button btnCambioFoto, btnCancel, btnGuardar;

    private Publicacion mPublicacion;

    // Integracion de Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    private Bitmap thumb_bitmap = null;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");

    byte[] thumb_byte = null;

    public static EditPublicacionFragment newInstance(String param1, String param2) {
        EditPublicacionFragment fragment = new EditPublicacionFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            mPublicacion = (Publicacion) getArguments().getSerializable("publicacion");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_publicacion, container, false);

        etTitulo = view.findViewById(R.id.et_Edit_Titulo_Pub);
        etDescripcion = view.findViewById(R.id.et_Edit_Descripcion_Pub);
        etValor = view.findViewById(R.id.et_Edit_Valor_Pub);
        ivFoto = view.findViewById(R.id.iv_Edit_Imagen_Pub);

        btnCambioFoto = view.findViewById(R.id.btn_Modificar_Foto);
        btnCancel = view.findViewById(R.id.btnCancelEditPub);
        btnGuardar = view.findViewById(R.id.btnGuardarEditPub);


        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        if (mPublicacion != null){
            etTitulo.setText(mPublicacion.getTitulo());
            etDescripcion.setText(mPublicacion.getDescripcion());
            etValor.setText(mPublicacion.getPrecio());
            if(mPublicacion.getImagen01() != null){
                String url = mPublicacion.getImagen01();
                Picasso.get()
                        .load(url)
                        .into(ivFoto);
            }


        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });



        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPublicacion.setTitulo(etTitulo.getText().toString());
                mPublicacion.setDescripcion(etDescripcion.getText().toString());
                mPublicacion.setPrecio(etValor.getText().toString());


                //Subir imagen en storage

                StorageReference ref = storageReference.child(mPublicacion.getIdUser());
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
                        mPublicacion.setImagen01(URLFoto);

                        // Guarda en la bd la publicacion dentro de Publicacion
                        // y una copia dentro del usuario con el id de la publicacion
                        databaseReference.child("Publicacion").child(mPublicacion.getUid()).setValue(mPublicacion);
                        databaseReference.child("users").child(mPublicacion.getIdUser()).child("publicaciones")
                                .child(mPublicacion.getUid()).setValue(mPublicacion);
                    }
                });

                Navigation.findNavController(view).navigate(R.id.action_editPublicacionFragment_to_misPublicacionesFragment);

            }
        });

        btnCambioFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(getContext(), EditPublicacionFragment.this);
            }
        });

        return view;
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
                        .into(ivFoto);

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
                thumb_byte = byteArrayOutputStream.toByteArray();
                //Fin del compressor

            }
        }
    }

}