package com.cabezasfive.truekapp.ui.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.interfaces.IComunicacionMain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class LoginFragment extends Fragment  {


    private EditText etEmail;
    private EditText etPass;
    private Button btnLogin;

    private String email = "";
    private String pass  = "";

    FragmentTransaction ft;

    private TextView tvRegistrarse;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private TextView userNick;

    IComunicacionMain comunicacionMain;
    Activity activity;


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        etEmail = view.findViewById(R.id.et_UsuarioLogin);
        etPass = view.findViewById(R.id.et_PassLogin);
        btnLogin = view.findViewById(R.id.btnIniciarSesion);


/*

        // Al precionar boton de Iniciar Sesion
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                pass = etPass.getText().toString();

                if(!email.isEmpty() && !pass.isEmpty()) {
                    loginUser();
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
                RegistroFragment registroFragment = new RegistroFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.contenido, registroFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
*/
        return view;
    }

    /*
    private void loginUser() {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // si la tarea fue exitosa (se logueo el usuario)
                if( task.isSuccessful()){

                    comunicacionMain.activarCerrar();

                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.contenido, homeFragment)
                            .commit();
                 }else{
                    Toast.makeText(getContext(), "No se pudo iniciar sesion, compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity= (Activity) context;
            comunicacionMain = (IComunicacionMain) activity;
        }
    }

}