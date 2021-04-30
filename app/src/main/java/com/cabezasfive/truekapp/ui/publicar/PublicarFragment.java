package com.cabezasfive.truekapp.ui.publicar;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PublicarFragment extends Fragment  {

    private TextInputEditText inputTitulo;
    private Button btn_Siguiente;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicar, container, false);

        inputTitulo = view.findViewById(R.id.tie_Nombre_Producto);

        btn_Siguiente = view.findViewById(R.id.btn_GoForm2Publicar);

        btn_Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validacion()){
                    Publicacion publicacion = new Publicacion();
                    String titulo = inputTitulo.getText().toString();
                    publicacion.setTitulo(titulo);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pub", publicacion);
                    Navigation.findNavController(view).navigate(R.id.publicarFragment022, bundle);
                }
            }
        });

        return view;
    }


    //Valida que este ingresado los valores necesarios
    private boolean validacion() {
        String titulo = inputTitulo.getText().toString();

        if(titulo.equals("")){
            Toast.makeText(getActivity(), "Debe ingresar un Titulo", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
//            Uri imagenuri = CropImage.getPickImageResultUri(getContext(),data);
//
//            //Recorta imagen
//            CropImage.activity(imagenuri)
//                    .start(getContext(), this);
//        }
//
//        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//
//            if(resultCode == RESULT_OK){
//
//                Uri resulturi = result.getUri();
//                File url = new File(resulturi.getPath());
//
//                Picasso.get()
//                        .load(url)
//                        .into(Foto);
//
//                // Comprimiendo
//                try{
//                    thumb_bitmap = new Compressor(getContext())
//                            .setMaxWidth(640)
//                            .setMaxHeight(480)
//                            .setQuality(90)
//                            .compressToBitmap(url);
//                }catch(IOException e){
//                    e.printStackTrace();
//                }
//
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
//                final byte [] thumb_byte = byteArrayOutputStream.toByteArray();
//                //Fin del compressor
//
//                btn_Agregar_producto.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        // si hay un usuario logueado puede publicar
//                        if(mAuth.getCurrentUser() != null){
//                            String titulo = inputTitulo.getText().toString();
//                            String descripcion = inputDescripcion.getText().toString();
//                            String precio = inputPrecio.getText().toString();
//
//                            if (titulo.equals("") || descripcion.equals("") || precio.equals("")){
//                                validacion();
//                            } else {
//
//                                // crear un objeto (instancia de la clase Publicacion)
//                                Publicacion p = new Publicacion();
//
//                                String Uid = UUID.randomUUID().toString();
//
//                                p.setUid(Uid);
//                                p.setIdUser(mAuth.getCurrentUser().getUid());
//                                p.setTitulo(titulo);
//                                p.setTitulo_upper(titulo.toUpperCase());
//                                p.setDescripcion(descripcion);
//                                Date Fecha = new Date(); // Sistema actual La fecha y la hora se asignan a objDate
//                                p.setF_Creacion(Fecha.toString());
//                                p.setActivo("true");
//                                p.setPrecio(precio);
//                                p.setVisitas(0);
//                                p.setInt_pendiente("0");
//
//                                //Subir imagen en storage
//
//                                StorageReference ref = storageReference.child(Uid);
//                                UploadTask uploadTask = ref.putBytes(thumb_byte);
//
//                                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                                    @Override
//                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                        if(!task.isSuccessful()){
//                                            throw Objects.requireNonNull(task.getException());
//                                        }
//                                        return ref.getDownloadUrl();
//                                    }
//                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Uri> task) {
//                                        Uri downloaduri = task.getResult();
//                                        String URLFoto = downloaduri.toString();
//                                        p.setImagen01(URLFoto);
//
//                                        // Guarda en la bd la publicacion dentro de Publicacion
//                                        // y una copia dentro del usuario con el id de la publicacion
//                                        databaseReference.child("Publicacion").child(p.getUid()).setValue(p);
//                                        databaseReference.child("users").child(p.getIdUser()).child("publicaciones").child(p.getUid()).setValue(p);
//                                    }
//                                });
//
//                                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                builder.setTitle("Publicación Creada");
//                                builder.setMessage("Publicacion creada con exito!")
//                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                InputMethodManager imm =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                                                limpiarDatos();
//                                                Navigation.findNavController(view).navigate(R.id.homeFragment);
//                                            }
//                                        })
//                                        .setCancelable(false)
//                                        .show();
//                            }
//                        }else{
//                            Toast.makeText(getContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();
//
//                            //Falla navegation para login
//                            Navigation.findNavController(getView()).navigate(R.id.loginFragment);
//                        }
//                        //Falla navegation para home
////                        Navigation.findNavController(getView()).navigate(R.id.homeFragment);
//                    }
//                });
//
//            }
//        }
//
//    }
}





//
//public class PublicarFragment extends Fragment  {
//
//    private TextInputEditText inputTitulo, inputDescripcion, inputPrecio;
//    private Button btn_Agregar_Foto, btn_Agregar_producto;
//
//    // Integracion de Firebase
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;
//    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");
//    Bitmap thumb_bitmap = null;
//
//    ImageView Foto;
//
//    FirebaseAuth mAuth;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);}
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_publicar, container, false);
//
//        inputTitulo = view.findViewById(R.id.tie_Nombre_Producto);
////        inputDescripcion = view.findViewById(R.id.tie_Descripción);
////        inputPrecio = view.findViewById(R.id.tie_Precio);
////
////        Foto = view.findViewById(R.id.Imagen_Producto);
////
////        btn_Agregar_Foto = view.findViewById(R.id.btn_Agregar_Foto);
////        btn_Agregar_producto = view.findViewById(R.id.btn_Agregar_producto);
//
//        // Inicializar Firebase
//        inicializarFirebase();
//        mAuth = FirebaseAuth.getInstance();
//
////        btn_Agregar_Foto.setOnClickListener(new View.OnClickListener(){
////            @Override
////            public void onClick(View view) {
////                CropImage.startPickImageActivity(getContext(),PublicarFragment.this);
////            }
////        });
//
//        return view;
//    }
//
//    // Metodo para inicializar Firebase
//    private void inicializarFirebase() {
//        FirebaseApp.initializeApp(getContext());
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference();
//    }
//
//    // Limpia datos
//    private void limpiarDatos() {
//        inputTitulo.setText("");
//        inputDescripcion.setText("");
//        inputPrecio.setText("");
//    }
//
//    //Valida que este ingresado los valores necesarios
//    private void validacion() {
//        String titulo = inputTitulo.getText().toString();
//        String descripcion = inputDescripcion.getText().toString();
//        String precio = inputPrecio.getText().toString();
//
//        if(titulo.equals("")){
//            Toast.makeText(getActivity(), "Debe ingresar un Titulo", Toast.LENGTH_SHORT).show();
//        } else if(descripcion.equals("")){
//            Toast.makeText(getActivity(), "Debe ingresar una Descripcion", Toast.LENGTH_SHORT).show();
//        } else if(precio.equals("")){
//            Toast.makeText(getActivity(), "Debe ingresar una precio estimado", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
//            Uri imagenuri = CropImage.getPickImageResultUri(getContext(),data);
//
//            //Recorta imagen
//            CropImage.activity(imagenuri)
//                    .start(getContext(), this);
//        }
//
//        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//
//            if(resultCode == RESULT_OK){
//
//                Uri resulturi = result.getUri();
//                File url = new File(resulturi.getPath());
//
//                Picasso.get()
//                        .load(url)
//                        .into(Foto);
//
//                // Comprimiendo
//                try{
//                    thumb_bitmap = new Compressor(getContext())
//                            .setMaxWidth(640)
//                            .setMaxHeight(480)
//                            .setQuality(90)
//                            .compressToBitmap(url);
//                }catch(IOException e){
//                    e.printStackTrace();
//                }
//
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
//                final byte [] thumb_byte = byteArrayOutputStream.toByteArray();
//                //Fin del compressor
//
//                btn_Agregar_producto.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        // si hay un usuario logueado puede publicar
//                        if(mAuth.getCurrentUser() != null){
//                            String titulo = inputTitulo.getText().toString();
//                            String descripcion = inputDescripcion.getText().toString();
//                            String precio = inputPrecio.getText().toString();
//
//                            if (titulo.equals("") || descripcion.equals("") || precio.equals("")){
//                                validacion();
//                            } else {
//
//                                // crear un objeto (instancia de la clase Publicacion)
//                                Publicacion p = new Publicacion();
//
//                                String Uid = UUID.randomUUID().toString();
//
//                                p.setUid(Uid);
//                                p.setIdUser(mAuth.getCurrentUser().getUid());
//                                p.setTitulo(titulo);
//                                p.setTitulo_upper(titulo.toUpperCase());
//                                p.setDescripcion(descripcion);
//                                Date Fecha = new Date(); // Sistema actual La fecha y la hora se asignan a objDate
//                                p.setF_Creacion(Fecha.toString());
//                                p.setActivo("true");
//                                p.setPrecio(precio);
//                                p.setVisitas(0);
//                                p.setInt_pendiente("0");
//
//                                //Subir imagen en storage
//
//                                StorageReference ref = storageReference.child(Uid);
//                                UploadTask uploadTask = ref.putBytes(thumb_byte);
//
//                                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                                    @Override
//                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                        if(!task.isSuccessful()){
//                                            throw Objects.requireNonNull(task.getException());
//                                        }
//                                        return ref.getDownloadUrl();
//                                    }
//                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Uri> task) {
//                                        Uri downloaduri = task.getResult();
//                                        String URLFoto = downloaduri.toString();
//                                        p.setImagen01(URLFoto);
//
//                                        // Guarda en la bd la publicacion dentro de Publicacion
//                                        // y una copia dentro del usuario con el id de la publicacion
//                                        databaseReference.child("Publicacion").child(p.getUid()).setValue(p);
//                                        databaseReference.child("users").child(p.getIdUser()).child("publicaciones").child(p.getUid()).setValue(p);
//                                    }
//                                });
//
//                                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                builder.setTitle("Publicación Creada");
//                                builder.setMessage("Publicacion creada con exito!")
//                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                InputMethodManager imm =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                                                limpiarDatos();
//                                                Navigation.findNavController(view).navigate(R.id.homeFragment);
//                                            }
//                                        })
//                                        .setCancelable(false)
//                                        .show();
//                            }
//                        }else{
//                            Toast.makeText(getContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();
//
//                            //Falla navegation para login
//                            Navigation.findNavController(getView()).navigate(R.id.loginFragment);
//                        }
//                        //Falla navegation para home
////                        Navigation.findNavController(getView()).navigate(R.id.homeFragment);
//                    }
//                });
//
//            }
//        }
//
//    }
//}