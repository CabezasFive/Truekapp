package com.cabezasfive.truekapp.ui.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.interfaces.IComunicacionMain;
import com.cabezasfive.truekapp.interfaces.IUser;
import com.cabezasfive.truekapp.ui.registroUsuario.RegistroViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class LoginFragment extends Fragment  {


    private EditText etEmail;
    private EditText etPass;
    private TextView tvRegistrarse;
    private Button btnLogin;

    private String email = "";
    private String pass  = "";

    private LoginViewModel loginViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Toast.makeText(getContext(), "Sesión iniciada", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = view.findViewById(R.id.et_UsuarioLogin);
        etPass = view.findViewById(R.id.et_PassLogin);
        btnLogin = view.findViewById(R.id.btnIniciarSesion);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                pass = etPass.getText().toString();

                if(!email.isEmpty() && !pass.isEmpty()) {
                    loginViewModel.login(email, pass);
                }else{
                    Toast.makeText(getContext(), "Debe ingresar los datos de email y password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // TextView de registrarse
        tvRegistrarse = view.findViewById(R.id.tvRegistroDesdeLogin);
        // Evento ClickListener del tv Registrarse
        tvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.registroFragment);
            }
        });

        return view;
    }


}