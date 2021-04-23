package com.cabezasfive.truekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.content.Context;
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

import com.cabezasfive.truekapp.repositories.UserAccountRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    String nick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Repository con los metodos de usuario a la bd
        userRepository = new UserAccountRepository(getApplication());

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
                navController.navigate(R.id.masVistosFragment, bundle);
            }
        });



        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRepository.logOut();
                ocultarCerrarSesion();
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
                switch (item.getItemId()){
                    case R.id.nav_inicio:
                        navController.navigate(R.id.homeFragment);
                        return true;
                    case R.id.nav_publicaciones:
                        navController.navigate(R.id.masVistosFragment);
                        return true;
                    case R.id.nav_login:
                        if(userRepository.isUserLoged()){
                            // si ya esta logueado va al perfil
                        }else {
                            // si no esta logueado va a login
                            navController.navigate(R.id.loginFragment);
                        }

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
        if(userRepository.isUserLoged()){
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
}


