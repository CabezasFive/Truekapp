package com.cabezasfive.truekapp.ui.publicar;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.cabezasfive.truekapp.models.Usuario;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class PublicarFragment03 extends Fragment  {

    private Button btn_Agregar_Foto, btn_Siguiente;
    private Publicacion mPublicacion = new Publicacion();

    Bitmap thumb_bitmap = null;

    ImageView Foto;
    private byte [] thumb_byte;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicar03, container, false);

        Bundle getUsuario = getArguments();
        if (getUsuario != null){
            mPublicacion = (Publicacion) getUsuario.getSerializable("pub");
        }

        btn_Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putByteArray("img", thumb_byte);
                Log.e("L03", thumb_bitmap.toString());
                Navigation.findNavController(view).navigate(R.id.publicarFragment04, bundle);
            }
        });


        Foto = view.findViewById(R.id.Imagen_Producto);
        btn_Agregar_Foto = view.findViewById(R.id.btn_Agregar_Foto);

        btn_Agregar_Foto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(getContext(),PublicarFragment03.this);
            }
        });

        return view;
    }



    //Valida que este ingresado los valores necesarios
    private void validacion() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri imagenuri = CropImage.getPickImageResultUri(getContext(),data);

            //Recorta imagen
            CropImage.activity(imagenuri)
                    .start(getContext(), this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){

                Uri resulturi = result.getUri();
                File url = new File(resulturi.getPath());

                Picasso.get()
                        .load(url)
                        .into(Foto);

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
                thumb_byte = byteArrayOutputStream.toByteArray();
                //Fin del compressor

            }
        }

    }
}