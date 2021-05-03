package com.cabezasfive.truekapp.ui.registroUsuario;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Usuario;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;


public class RegistroFragment03 extends Fragment {


    private EditText editTextemail;
    private EditText editTextpassword;
    private Button btnRegistrar;

    private RegistroViewModel registroViewModel;

    // Variables de los datos que se van a registrar
    private String email = "";
    private String nick = "" ;
    private String password = "";

    private Usuario mUsuario, usuario;


    public static RegistroFragment03 newInstance() {
        RegistroFragment03 fragment = new RegistroFragment03();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registroViewModel = new ViewModelProvider(this).get(RegistroViewModel.class);
        registroViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
//                    Toast.makeText(getContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();
//                    Navigation.findNavController(getView()).navigate(R.id.homeFragment);

                    androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Registro Completado");
                    builder.setMessage("Usuario creado exitosamente\nInicie sesión para continuar")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    InputMethodManager imm =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                                    Navigation.findNavController(getView()).navigate(R.id.loginFragment);
                                }
                            })
                            .setCancelable(false)
                            .show();

                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro03, container, false);

        Bundle getUsuario = getArguments();
        if (getUsuario != null){
            mUsuario = (Usuario) getUsuario.getSerializable("usuario");
        }

        editTextemail = view.findViewById(R.id.et_Registro_Email);
        editTextpassword = view.findViewById(R.id.et_Registro_Password);
        btnRegistrar = view.findViewById(R.id.btn_RegistroUsuario);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = editTextemail.getText().toString();
                password = editTextpassword.getText().toString();

                if (validarTextos()){

                    mUsuario.setEmail(email);

                    TareaAsyncTask tareaAsyncTask = new TareaAsyncTask();
                    tareaAsyncTask.execute();

                }

            }
        });


        return view;
    }



        private boolean validarTextos() {
        if(!email.isEmpty() && !password.isEmpty()){
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





    private class TareaAsyncTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean resultado = registroViewModel.existNick(nick);
            return resultado;
        }

        @Override
        protected void onProgressUpdate(Integer... values){

        }

        @Override
        protected void onPostExecute(Boolean exist){
            if(exist){
                Log.d("RESULTADO", "Existe el usuario");
            }else {
                Log.d("RESULTADO", "No existe se va a guardar");
                registroViewModel.registrar(email, password, mUsuario);
            }
        }
    }

}