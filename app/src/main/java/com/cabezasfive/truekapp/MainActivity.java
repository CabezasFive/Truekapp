package com.cabezasfive.truekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabezasfive.truekapp.interfaces.IUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    // referencias a la navegacion
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;

    // Logo de usuario
    private ImageView btnUser;
    //TextView nombre de usuario logueado
    private TextView userName;

    private Button btnCerrarSesion;
    private Button btnInicioSesion;


    ImageView logoHome;

    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // UI
        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottonNavView);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.masVistosFragment)
                .setDrawerLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.fragment);


        logoHome=findViewById(R.id.logoToolbar);
        userName=findViewById(R.id.tv_NombreUsuarioToolbar);

        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnInicioSesion = findViewById(R.id.btnInicioSesionToolbar);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        logoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.homeFragment);
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
                        navController.navigate(R.id.loginFragment);
                        return true;
                }
                return false;
            }
        });

/*
        // Boton cerrar Sesion
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });

        // Boton iniciar sesion
        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido, new LoginFragment())
                    .addToBackStack(null)
                    .commit();
            }
        });

        getUserName();


        // Al iniciar se muestra el HomeFragment
        getSupportFragmentManager().beginTransaction().add(R.id.contenido, new HomeFragment()).commit();
        setTitle("Home");



    }

*/
/*




    @Override
    public void activarCerrar() {
        btnInicioSesion.setVisibility(View.INVISIBLE);
        btnCerrarSesion.setVisibility(View.VISIBLE);
        getUserName();
    }

    @Override
    public void activarLogin() {
        btnInicioSesion.setVisibility(View.VISIBLE);
        btnCerrarSesion.setVisibility(View.INVISIBLE);
        getUserName();
    }

    public void getUserName(){
        if(mAuth.getCurrentUser() !=null ){
            String id = mAuth.getCurrentUser().getUid();
            databaseReference.child("users").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if ( snapshot.exists()){
                        String nick = snapshot.child("nick").getValue().toString();
                        userName.setText(nick);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            userName.setText("");
        }
*/
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
            ||super.onSupportNavigateUp();
    }
}


