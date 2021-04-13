package com.cabezasfive.truekapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    // referencias a la navegacion
    DrawerLayout drawerLayout;
    NavigationView navigationView;

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
        navigationView = findViewById(R.id.navView);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.masVistosFragment)
                .setDrawerLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration );
//        NavigationUI.setupWithNavController(navigationView, navController);


        logoHome=findViewById(R.id.logoToolbar);
        userName=findViewById(R.id.tv_NombreUsuarioToolbar);

        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnInicioSesion = findViewById(R.id.btnInicioSesionToolbar);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();



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

        navigationView.setNavigationItemSelectedListener(this);

    }

*/
        /*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItemNav(item);
        return true;
    }


    private void selectItemNav(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido, new HomeFragment());
                ft.addToBackStack(null);
                break;
            case R.id.nav_login:
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido, new LoginFragment());
                ft.addToBackStack(null);
                break;
            case R.id.nav_registro:
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido, new RegistroFragment());
                ft.addToBackStack(null);
                break;
            case R.id.nav_categorias:
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido,new  CategoriasFragment());
                ft.addToBackStack(null);
                break;
            case R.id.nav_destacados:
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido,new DestacadosFragment());
                ft.addToBackStack(null);
                break;
            case R.id.nav_masVistos:
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido,new MasVistosFragment());
                ft.addToBackStack(null);
                break;
            case R.id.nav_publicar:
                if(mAuth.getCurrentUser() != null){
                    ft = fm.beginTransaction();
                    ft.replace(R.id.contenido,new PublicarFragment());
                    ft.addToBackStack(null);
                    break;
                }else{
                    ft = fm.beginTransaction();
                    ft.replace(R.id.contenido, new LoginFragment());
                    ft.addToBackStack(null);
                    break;
                }

            case R.id.nav_ayuda:
                Intent miIntent = new Intent(MainActivity.this, AyudaActivity.class);
                startActivity(miIntent);
                break;
            case R.id.nav_pregruntasFrecuentes:

                break;
            case R.id.nav_acercaDe:
                break;
        }



         */
        /*
        ft.commit();
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Lleva al fragment home al clickear el logo en la toolbar
    // esta asociado al imageView en el xml (onClick)
    public void clikHome(View view){
        ft = fm.beginTransaction();
        ft.replace(R.id.contenido, new HomeFragment());
        ft.commit();
    }

    // Cierra la sesion de usuario
    // al cerrar sesion oculto el boton y limpio el nick de usuario
    public void cerrarSesion(){
        mAuth.signOut();
        activarLogin();
    }



    // Metodos que son llamados desde el menu de homeFragment que inicia el fragment asociado con el cardView clickeado
    @Override
    public void A_Categorias() {
        ft = fm.beginTransaction();
        ft.replace(R.id.contenido, new CategoriasFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void A_Publicar() {
        if(mAuth.getCurrentUser() != null){
            ft = fm.beginTransaction();
            ft.replace(R.id.contenido, new PublicarFragment());
            ft.addToBackStack(null);
            ft.commit();
        }else{
            ft = fm.beginTransaction();
            ft.replace(R.id.contenido, new LoginFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void A_Destacados() {
        ft = fm.beginTransaction();
        ft.replace(R.id.contenido, new DestacadosFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void A_MisOfertas() {
        if (mAuth.getCurrentUser() != null){
            ft = fm.beginTransaction();
            ft.replace(R.id.contenido, new MisOfertasFragment());
            ft.addToBackStack(null);
            ft.commit();
        }else{
            ft = fm.beginTransaction();
            ft.replace(R.id.contenido, new LoginFragment());
            ft.addToBackStack(null);
            ft.commit();
        }

    }

    @Override
    public void A_MasVistos() {
        ft = fm.beginTransaction();
        ft.replace(R.id.contenido, new MasVistosFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void A_Ayuda() {
        Intent miIntent = new Intent(MainActivity.this, AyudaActivity.class);
        startActivity(miIntent);
    }

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


