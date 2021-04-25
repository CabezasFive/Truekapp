package com.cabezasfive.truekapp.ui.login;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cabezasfive.truekapp.MainActivity;
import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.repositories.UserAccountRepository;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment  {


    private EditText etEmail;
    private EditText etPass;
    private TextView tvRegistrarse;
    private Button btnLogin;

    private String email = "";
    private String pass  = "";

    private LoginViewModel loginViewModel;
    UserAccountRepository userRepository;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Navigation.findNavController(getView()).navigate(R.id.homeFragment);
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

       userRepository = new UserAccountRepository(getActivity().getApplication());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                pass = etPass.getText().toString();

                if(!email.isEmpty() && !pass.isEmpty()) {
                    loginViewModel.login(email, pass);
                    InputMethodManager imm =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    ((MainActivity)getActivity()).userName.setText(userRepository.getUserNickname());
                    ((MainActivity)getActivity()).activarCerrar();
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