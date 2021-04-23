package com.cabezasfive.truekapp.ui.publicar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cabezasfive.truekapp.MainActivity;
import com.cabezasfive.truekapp.R;

import com.cabezasfive.truekapp.models.Publicacion;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class PublicarFragment extends Fragment  {

    private TextInputEditText inputTitulo, inputDescripcion, inputPrecio;
    private Button btn_Agregar_Foto, btn_Agregar_producto;

    // Integracion de Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicar, container, false);

        inputTitulo = view.findViewById(R.id.tie_Nombre_Producto);
        inputDescripcion = view.findViewById(R.id.tie_Descripción);
        inputPrecio = view.findViewById(R.id.tie_Precio);
        btn_Agregar_Foto = view.findViewById(R.id.btn_Agregar_Foto);
        btn_Agregar_producto = view.findViewById(R.id.btn_Agregar_producto);

        // Inicializar Firebase
        inicializarFirebase();
        mAuth = FirebaseAuth.getInstance();

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
                            // crear un objeto (instancia de la clase Publicacion)

                            Publicacion p = new Publicacion();
                            p.setUid(UUID.randomUUID().toString());
                            p.setIdUser(mAuth.getCurrentUser().getUid());
                            p.setTitulo(titulo);
                            p.setTitulo_upper(titulo.toUpperCase());
                            p.setDescripcion(descripcion);
                            Date Fecha = new Date(); // Sistema actual La fecha y la hora se asignan a objDate
                            p.setF_Creacion(Fecha.toString());
                            p.setImagen01("https://fotos.perfil.com/2020/06/08/960/0/9-de-junio-dia-mundial-del-pato-donald-968611.jpg");
                            p.setActivo(true);
                            p.setVisitas(0);

                            databaseReference.child("Publicacion").child(p.getUid()).setValue(p);

                            Toast.makeText(getActivity(), "Publicacion creada correctamente", Toast.LENGTH_SHORT).show();
                            limpiarDatos();
                        }
                    }else{
                        Toast.makeText(getContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();

                        //Falla navegation para login
                        //Navigation.findNavController(getView()).navigate(R.id.action_publicarFragment_to_loginFragment);
                    }
                //Falla navegation para home
                //Navigation.findNavController(getView()).navigate(R.id.action_publicarFragment_to_homeFragment);
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
}