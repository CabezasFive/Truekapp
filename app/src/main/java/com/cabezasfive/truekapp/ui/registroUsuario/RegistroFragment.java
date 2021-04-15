package com.cabezasfive.truekapp.ui.registroUsuario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RegistroFragment extends Fragment {

    private EditText editTextnombre;
    private EditText editTextapellido;
    private EditText editTextdireccion;
    private EditText editTexttelefono;
    private EditText editTextnick;
    private EditText editTextemail;
    private EditText editTextpassword;
    private Button btnRegistrar;

    private RegistroViewModel registroViewModel;

    // Variables de los datos que se van a registrar
    private String nombre = "";
    private String apellido = "";
    private String direccion = "";
    private String telefono = "";
    private String email = "";
    private String nick = "" ;
    private String password = "";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registroViewModel = new ViewModelProvider(this).get(RegistroViewModel.class);
        registroViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Toast.makeText(getContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_registroFragment_to_homeFragment);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_registro, container, false);

        editTextnombre = view.findViewById(R.id.et_Registro_Nombre);
        editTextapellido = view.findViewById(R.id.et_Registro_Apellido);
        editTextdireccion = view.findViewById(R.id.et_Registro_Direccion);
        editTexttelefono = view.findViewById(R.id.et_Registro_Telefono);
        editTextnick = view.findViewById(R.id.et_Registro_NickName);
        editTextemail = view.findViewById(R.id.et_Registro_Email);
        editTextpassword = view.findViewById(R.id.et_Registro_Password);
        btnRegistrar = view.findViewById(R.id.btn_RegistroUsuario);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre = editTextnombre.getText().toString();
                apellido = editTextapellido.getText().toString();
                direccion = editTextdireccion.getText().toString();
                telefono = editTexttelefono.getText().toString();
                nick = editTextnick.getText().toString();
                email = editTextemail.getText().toString();
                password = editTextpassword.getText().toString();

                if (validarTextos()){

                    String fecha = new Date().toString();

                    Usuario user = new Usuario(nombre, apellido, direccion, telefono, email, nick, fecha);

                    registroViewModel.registrar(email, password, user);

                }

            }
        });

        return view;
    }

    private boolean validarTextos() {
        if(!nombre.isEmpty() && !apellido.isEmpty() && !direccion.isEmpty() && !nombre.isEmpty()
                && !email.isEmpty() && !password.isEmpty() && !nick.isEmpty()){
            if (password.length() >= 6){
                return true;
            }else{
                Toast.makeText(getContext(), "El password debe tener como minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(getContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}