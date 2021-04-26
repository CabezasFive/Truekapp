package com.cabezasfive.truekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.cabezasfive.truekapp.adapters.AdapterListarPublicaciones;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaActivity;
import com.cabezasfive.truekapp.models.Publicacion;

import com.cabezasfive.truekapp.repositories.UserAccountRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // referencias a la navegacion
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;


    //TextView nombre de usuario logueado
    private TextView userName;
    private Button btnCerrarSesion, btnIniciarSesion;

    private ImageView logoHome;

    private EditText textSearch;
    private ImageButton btnBuscar;

    UserAccountRepository userRepository;
    private FirebaseAuth firebaseAuth;

    private String nick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Repository con los metodos de usuario a la bd
        userRepository = new UserAccountRepository(getApplication());
        firebaseAuth = FirebaseAuth.getInstance();

        // UI
        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottonNavView);

        NavController navController = Navigation.findNavController(this, R.id.fragment);

        logoHome=findViewById(R.id.logoToolbar);


        btnBuscar = findViewById(R.id.btnSearch);
        textSearch = findViewById(R.id.searchField);

        userName=findViewById(R.id.tv_NombreUsuarioToolbar);

        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);



        // ************************************************
        // Logo superior si se da click va al fragment HOME
        logoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.homeFragment);
            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search;
                search = textSearch.getText().toString();
                textSearch.setText("");
                Bundle bundle = new Bundle();
                bundle.putString("text", search);
                InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                navController.navigate(R.id.listadoPublicacionesFragment, bundle);
            }
        });



        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRepository.logOut();
                ocultarCerrarSesion();
                navController.navigate(R.id.homeFragment);
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.loginFragment);
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean logueado = userRepository.isUserLoged();

                switch (item.getItemId()){
                    case R.id.nav_inicio:
                        navController.navigate(R.id.homeFragment);
                        return true;
                    case R.id.nav_publicaciones:
                        navController.navigate(R.id.listadoPublicacionesFragment);
                        return true;
                    case R.id.nav_login:
                        if(isLoged()){
                            // si ya esta logueado va al perfil
                            navController.navigate(R.id.editarUsuarioFragment);
                        }else {
                            // si no esta logueado va a login
                            navController.navigate(R.id.loginFragment);
                        }
                        return true;
                    case R.id.nav_publicar:
                        if(isLoged()){
                            // va a publicar fragment
                            navController.navigate(R.id.publicarFragment);
                        }else{
                            navController.navigate(R.id.loginFragment);
                        }
                        return true;
                    case R.id.nav_ayuda:
                        navController.navigate(R.id.ayudaActivity);
                        return true;

                }
                return false;
            }
        });
    }


    public void activarCerrar() {
        btnIniciarSesion.setVisibility(View.INVISIBLE);
        btnCerrarSesion.setVisibility(View.VISIBLE);
        getNickAyncTask getNick= new getNickAyncTask();
        getNick.execute();
    }



    public void ocultarCerrarSesion() {
        btnIniciarSesion.setVisibility(View.VISIBLE);
        btnCerrarSesion.setVisibility(View.INVISIBLE);
        userName.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isLoged()){
            activarCerrar();
        }else{
            ocultarCerrarSesion();
        }
    }

    private class getNickAyncTask extends AsyncTask<Void, Integer, String>{
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(Void... voids) {
            nick = userRepository.getUserNickname();
            return nick;
        }

        @Override
        protected void onProgressUpdate(Integer... values){

        }

        @Override
        protected void onPostExecute(String resultado){
            userName.setText(resultado);
        }
    }

    private boolean isLoged(){
        if(firebaseAuth.getCurrentUser() != null){
            return true;
        } else {
            return false;
        }
    }

}


