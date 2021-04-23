package com.cabezasfive.truekapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cabezasfive.truekapp.adapters.AdapterListarPublicaciones;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaActivity;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.UserAccountRepository;
import com.cabezasfive.truekapp.ui.masVistos.MasVistosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // referencias a la navegacion
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;


    //TextView nombre de usuario logueado
    private TextView userName;
    private Button btnCerrarSesion, btnIniciarSesion;

    ImageView logoHome;

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
                    case R.id.nav_publicar:
                        navController.navigate(R.id.publicarFragment);
                        return true;
                    case R.id.nav_login:
                        navController.navigate(R.id.loginFragment);
                        return true;
                    case R.id.nav_ayuda:
                        Intent miIntent = new Intent(MainActivity.this, AyudaActivity.class);
                        startActivity(miIntent);
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


