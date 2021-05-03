package com.cabezasfive.truekapp.ui.registroUsuario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.models.Usuario;

import java.util.Date;

public class RegistroFragment02 extends Fragment {



    private EditText editTextdireccion;
    private EditText editTexttelefono;
    private EditText editTextciudad;
    private Button btnContinuar;


    private RegistroViewModel registroViewModel;

    // Variables de los datos que se van a registrar
    private String nombre = "";
    private String apellido = "";
    private String nick = "" ;
    private String direccion = "";
    private String telefono = "";
    private String ciudad = "";

    private Usuario mUsuario, usuario;

    public static RegistroFragment02 newInstance(String param1, String param2) {
        RegistroFragment02 fragment = new RegistroFragment02();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro02, container, false);

        Bundle getUsuario = getArguments();
        if (getUsuario != null){
            mUsuario = (Usuario) getUsuario.getSerializable("usuario");
        }

        editTextdireccion = view.findViewById(R.id.et_Registro_Direccion);
        editTexttelefono = view.findViewById(R.id.et_Registro_Telefono);
        editTextciudad = view.findViewById(R.id.et_Registro_Ciudad);
        btnContinuar = view.findViewById(R.id.btn_GoForm3Registro);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                direccion = editTextdireccion.getText().toString();
                telefono = editTexttelefono.getText().toString();
                ciudad = editTextciudad.getText().toString();

                nombre = mUsuario.getNombre();
                apellido = mUsuario.getApellido();
                nick = mUsuario.getNick();


                if (validarTextos()){
                    String fecha = new Date().toString();
                    Usuario usuario = new Usuario(nombre, apellido,direccion, telefono, ciudad, nick, fecha);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("usuario", usuario);
                    Navigation.findNavController(view).navigate(R.id.registroFragment03, bundle);
                }
            }
        });

        return view;
    }


    private boolean validarTextos() {
        if(!direccion.isEmpty() && !telefono.isEmpty() && !ciudad.isEmpty()){
            return true;
        }else {
            Toast.makeText(getContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}