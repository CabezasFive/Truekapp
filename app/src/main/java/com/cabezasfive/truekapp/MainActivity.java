package com.cabezasfive.truekapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cabezasfive.truekapp.fragments.AyudaFragment;
import com.cabezasfive.truekapp.fragments.CategoriasFragment;
import com.cabezasfive.truekapp.fragments.DestacadosFragment;
import com.cabezasfive.truekapp.fragments.HomeFragment;
import com.cabezasfive.truekapp.fragments.LoginFragment;
import com.cabezasfive.truekapp.fragments.MasVistosFragment;
import com.cabezasfive.truekapp.fragments.MisOfertasFragment;
import com.cabezasfive.truekapp.fragments.PublicarFragment;
import com.cabezasfive.truekapp.fragments.RegistroFragment;
import com.cabezasfive.truekapp.interfaces.IComunicacionFragments;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements IComunicacionFragments, NavigationView.OnNavigationItemSelectedListener {

    // referencias a la navegacion
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    // Logo de usuario
    private ImageView btnUser;
    //TextView nombre de usuario logueado
    private TextView userName;

    private Button btnCerrarSesion;
    private Button btnInicioSesion;

    // Icono hamburguesa
    ActionBarDrawerToggle toggle;

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

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
        toolbar = findViewById(R.id.toolbar);

        logoHome=findViewById(R.id.logoToolbar);
        userName=findViewById(R.id.tv_NombreUsuarioToolbar);

        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnInicioSesion = findViewById(R.id.btnInicioSesionToolbar);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        // Chequea si hay un usuario logueado - si lo hay pone su nick en el textView UserName
        if(mAuth.getCurrentUser() !=null ){
            btnInicioSesion.setVisibility(View.INVISIBLE);
            btnCerrarSesion.setVisibility(View.VISIBLE);
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
            btnInicioSesion.setVisibility(View.VISIBLE);
            btnCerrarSesion.setVisibility(View.INVISIBLE);
            userName.setText("");
        }


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




        // Al iniciar se muestra el HomeFragment
        getSupportFragmentManager().beginTransaction().add(R.id.contenido, new HomeFragment()).commit();
        setTitle("Home");

        // Setup del toolbar
        setSupportActionBar(toolbar);

        //setup del icono hamb
        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawerLayout.addDrawerListener(toggle);


        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItemNav(item);
        return true;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
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
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido,new PublicarFragment());
                ft.addToBackStack(null);
                break;
            case R.id.nav_ayuda:
                ft = fm.beginTransaction();
                ft.replace(R.id.contenido,new AyudaFragment());
                ft.addToBackStack(null);
                break;
            case R.id.nav_pregruntasFrecuentes:
                break;
            case R.id.nav_acercaDe:
                break;
        }
        ft.commit();
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
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
        btnCerrarSesion.setVisibility(View.INVISIBLE);
        userName.setText("");
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
        ft = fm.beginTransaction();
        ft.replace(R.id.contenido, new PublicarFragment());
        ft.addToBackStack(null);
        ft.commit();
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
        ft = fm.beginTransaction();
        ft.replace(R.id.contenido, new MisOfertasFragment());
        ft.addToBackStack(null);
        ft.commit();
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
        ft = fm.beginTransaction();
        ft.replace(R.id.contenido, new AyudaFragment());
        ft.addToBackStack(null);
        ft.commit();
    }


}


